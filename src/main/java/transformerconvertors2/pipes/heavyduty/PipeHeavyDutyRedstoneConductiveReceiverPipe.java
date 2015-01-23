package transformerconvertors2.pipes.heavyduty;

import buildcraft.core.RFBattery;
import buildcraft.core.utils.Utils;
import buildcraft.transport.IPipeTransportPowerHook;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import cofh.api.energy.IEnergyHandler;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.helpers.TileEntityHelper;
import core.helpers.WorldHelper;
import core.utilities.Coordinates;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.pipes.PipeBase;

public class PipeHeavyDutyRedstoneConductiveReceiverPipe extends PipeBase implements IPipeTransportPowerHook, IEnergyHandler {

    protected final RFBattery battery;

    public PipeHeavyDutyRedstoneConductiveReceiverPipe(Item item) {
        super(new PipeTransportPower(), item);
        ((PipeTransportPower)getTransport()).initFromPipe(PipeHeavyDutyRedstoneConductiveReceiverPipe.class);
//        battery = new RFBattery(40 * (TileEntityElectricDynamoEngineConvertorBase.MAX_TICK_OUTPUT - 4), TileEntityElectricDynamoEngineConvertorBase.MAX_TICK_OUTPUT * 3, 0);
        battery = new RFBattery(40, 3, 0);//TODO
	}

	@Override
	protected boolean canOnlyReceivePower() {
		return true;
	}

    /**
     * Add energy to an IEnergyHandler, internal distribution is left entirely to the IEnergyHandler.
     *
     * @param from       Orientation the energy is received from.
     * @param maxReceive Maximum amount of energy to receive.
     * @param simulate   If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (TileEntityHelper.getTileEntityInDirection(getWorld(), new Coordinates(getContainer().xCoord, getContainer().yCoord, getContainer().zCoord), from) instanceof IEnergyHandler) {
            return battery.receiveEnergy(maxReceive, simulate);
        }
        return maxReceive;
    }

    /**
     * Remove energy from an IEnergyHandler, internal distribution is left entirely to the IEnergyHandler.
     *
     * @param from       Orientation the energy is extracted from.
     * @param maxExtract Maximum amount of energy to extract.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;//Leave this at zero.
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored(ForgeDirection from) {
        return battery.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return battery.getMaxEnergyStored();
    }

    /**
     * Returns TRUE if the TileEntity can connect on a given side.
     */
    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    /**
     * Override default behavior on receiving energy into the pipe.
     * @return The amount of power used, or -1 for default behavior.
     */
    @Override
    public int receiveEnergy(ForgeDirection from, int val) {
        return -1;//Leave this.
    }

    /**
     * Override default requested power.
     */
    @Override
    public int requestEnergy(ForgeDirection from, int amount) {
        return battery.getMaxEnergyStored() - battery.getEnergyStored();
    }

    /**
     * Should return the index in the array returned by GetTextureIcons() for a
     * specified direction
     *
     * @param direction - The direction for which the indexed should be
     *                  rendered. Unknown for pipe center
     * @return An index valid in the array returned by getTextureIcons()
     */
    @Override
    public int getIconIndex(ForgeDirection direction) {
        return 1;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        for(ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
            if (Utils.checkPipesConnections(getContainer(), getContainer().getTile(forgeDirection))) {
                TileEntity tileEntity = getContainer().getTile(forgeDirection);
                if (tileEntity instanceof TileGenericPipe) {
                    TileGenericPipe genericPipe = (TileGenericPipe)tileEntity;
                    if (genericPipe.pipe == null) {
                        LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, LoggerEnum.WARN, "The Heavy Duty Redstone Conductive Receiver Pipe is null! xCoord: '%d', yCoord: '%d', zCoord: '%d'", getContainer().xCoord, getContainer().yCoord, getContainer().zCoord);
                        return;
                    }
                    if (genericPipe.pipe.transport instanceof PipeTransportPower) {
                        PipeTransportPower pipeTransportPower = (PipeTransportPower)genericPipe.pipe.transport;
                        float energyToRemove = 0.0F;
                        if (getEnergyStored(forgeDirection) > 40.0F) {
                            energyToRemove = getEnergyStored(forgeDirection) / 40.0F + 4.0F;
                        } else if (getEnergyStored(forgeDirection) > 10.0F) {
                            energyToRemove = getEnergyStored(forgeDirection) / 10.0F;
                        } else {
                            energyToRemove = 1.0F;
                        }
                        int energyUsed = battery.useEnergy(1, Math.round(energyToRemove), false);
                        pipeTransportPower.receiveEnergy(forgeDirection.getOpposite(), energyUsed);
                        if (WorldHelper.isClient(getWorld())) {
                            return;
                        }
                        short[] var10000 = pipeTransportPower.displayPower;
                        int var10001 = forgeDirection.ordinal();
                        var10000[var10001] += new Integer(energyUsed).shortValue();
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        battery.readFromNBT((NBTTagCompound) nbt.getTag("batteryNBT"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound batteryNBT = new NBTTagCompound();
        battery.writeToNBT(batteryNBT);
        nbt.setTag("batteryNBT", batteryNBT);
    }

    public final RFBattery getBattery() {
        return battery;
    }

}

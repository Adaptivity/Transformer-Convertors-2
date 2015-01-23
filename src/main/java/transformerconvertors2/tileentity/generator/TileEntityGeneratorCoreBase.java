package transformerconvertors2.tileentity.generator;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import core.common.resources.CoreResources;
import core.helpers.PlayerHelper;
import core.helpers.TileEntityHelper;
import core.tileentity.TileEntityCoreBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Master801 on 11/21/2014.
 * @author Master801
 */
public abstract class TileEntityGeneratorCoreBase extends TileEntityCoreBase implements IEnergyHandler {

    protected abstract void updateGenerator();

    private final EnergyStorage storage;

    public TileEntityGeneratorCoreBase(int capacity) {
        this(new EnergyStorage(capacity));
    }

    public TileEntityGeneratorCoreBase(int capacity, int maxTransfer) {
        this(new EnergyStorage(capacity, maxTransfer));
    }

    public TileEntityGeneratorCoreBase(int capacity, int maxReceive, int maxExtract) {
        this(new EnergyStorage(capacity, maxReceive, maxExtract));
    }

    public TileEntityGeneratorCoreBase(EnergyStorage storage) {
        this.storage = storage;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
    }

    @Override
    public boolean onActivated(EntityPlayer player, ForgeDirection side) {
        if (PlayerHelper.isPlayerSneaking(player) && CoreResources.isDevWorkspace()) {
            PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Energy level: '%d/%d'", getStorage().getEnergyStored(), getStorage().getMaxEnergyStored());
            return true;
        }
        return super.onActivated(player, side);
    }

    @Override
    public final void updateEntity() {
        updateGenerator();
        for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity gotTile = TileEntityHelper.getTileEntityInDirection(this, direction);
            if (gotTile instanceof IEnergyHandler) {
                sendPower((IEnergyHandler)gotTile, direction, false);
            }
        }
    }

    public final EnergyStorage getStorage() {
        return storage;
    }

    protected void sendPower(IEnergyHandler handler, ForgeDirection direction, boolean simulation) {
        int extractedEnergy = handler.extractEnergy(direction.getOpposite(), Math.min(getStorage().getEnergyStored(), getStorage().getMaxEnergyStored()), simulation);
        if (extractedEnergy > 0) {
            getStorage().receiveEnergy(extractedEnergy, simulation);
        }
    }

}

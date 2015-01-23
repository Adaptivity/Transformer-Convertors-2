package transformerconvertors2.tileentity.dynamoengine;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import core.common.resources.CoreResources;
import core.helpers.*;
import core.tileentity.TileEntityCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.api.dynamoengine.IDynamoEngine;

/**
 * Created by Master801 on 11/9/2014.
 * @author Master801
 */
public abstract class TileEntityDynamoEngineConvertorBase extends TileEntityCoreBase implements IDynamoEngine, IEnergyHandler {

    public abstract boolean isAllowedToSendPower();

    /**
     * Only runs on the server side.
     * <p>
     *     If you need to run code on the client side, override the method "overrideClientSideUpdating", then return true;
     * </p>
     */
    protected abstract void updateDynamoEngine();

    private final EnergyStorage storage;
    protected float engineProgress = 0.0F, enginePistonSpeed = 0.07F, heat = 0.0F;
    private final float maxHeat;

    /**
     * 0 = IDLE
     * 1 = WORKING
     * 2 = MELTING POINT
     * 3 = OVERHEAT
     */
    private byte energyStage = RandomHelper.convertIntegerToByte(0);

    protected TileEntityDynamoEngineConvertorBase(int capacity, float maxHeat) {
        this(new EnergyStorage(capacity), maxHeat);
    }

    protected TileEntityDynamoEngineConvertorBase(EnergyStorage storage, float maxHeat) {
        this.storage = storage;
        this.maxHeat = maxHeat;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public final int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public final int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from != getDirection();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDynamoChamberTexture() {
        return "/custom_resources/textures/models/dynamo_engine/chamber/Chamber.png";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        engineProgress = nbt.getFloat("progress");
        enginePistonSpeed = nbt.getFloat("pistonSpeed");
        heat = nbt.getFloat("heat");
        energyStage = nbt.getByte("energyStage");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        nbt.setFloat("progress", engineProgress);
        nbt.setFloat("pistonSpeed", enginePistonSpeed);
        nbt.setFloat("heat", heat);
        nbt.setByte("energyStage", energyStage);
    }

    @Override
    public final void updateEntity() {
//        checkAndSetOrientation();//TODO This should be fixed by validate...
        /*
        if (engineProgress <= 0.0F) {
            engineProgress += getEnginePistonSpeed();
            return;
        }
        if (engineProgress > 0.0F) {
            updateDynamoEngine();
        }
        if (engineProgress > 0.0F) {//FIXME This should not set the progress to 0 if it's not completely at its climax.
            engineProgress = 0.0F;//Resets the progress.
        }
        */
        //TODO
        if (WorldHelper.isClient(getWorldObj())) {
            engineProgress += getEnginePistonSpeed();
            if (engineProgress > 1.0F) {
                engineProgress = 0.0F;
            }
            return;
        }
        recalculateEnergyStage();
        if (isAllowedToSendPower()) {
            if (getStorage().getEnergyStored() > 0 && getStorage().getEnergyStored() <= getStorage().getMaxEnergyStored()) {
                TileEntity receiverTile = TileEntityHelper.getTileEntityInDirection(this, getDirection());
                if (receiverTile instanceof IEnergyHandler) {
                    sendPower((IEnergyHandler) receiverTile, getDirection(), false);
                }
            }
        }
        if (getStorage().getEnergyStored() < 0) {//Checks the power levels anyway.
            getStorage().setEnergyStored(0);
        } else if (getStorage().getEnergyStored() > getStorage().getMaxEnergyStored()) {
            getStorage().setEnergyStored(getStorage().getMaxEnergyStored());
        }
    }

    public final EnergyStorage getStorage() {
        return storage;
    }

    @Override
    public float getEngineProgress() {
        return engineProgress;
    }

    public float getEnginePistonSpeed() {
        if (WorldHelper.isServer(getWorldObj())) {
            return enginePistonSpeed = Math.max(0.16F * getHeat() / getMaxHeat(), 0.01F);
        }
        return enginePistonSpeed;
    }

    @Override
    public float getHeat() {
        return heat;
    }

    @Override
    public final float getMaxHeat() {
        return maxHeat;
    }

    @Override
    public final void addHeat(float additionalHeat) {
        heat += additionalHeat;
        if (heat < 0.0F) {
            heat = 0.0F;
        } else if (heat > maxHeat) {
            heat = maxHeat;
        }
    }

    @Override
    public final void setHeat(float heat) {
        this.heat = heat;
        if (this.heat < 0.0F) {
            this.heat = 0.0F;
        } else if (heat > maxHeat) {
            this.heat = maxHeat;
        }
    }

    protected final boolean checkRedstoneSignalAndPower() {
        return isRedstonePowered() && getStorage().getEnergyStored() > 0 && getStorage().getEnergyStored() <= getStorage().getMaxEnergyStored();
    }

    @Override
    public String getTrunkTexture() {
        switch(energyStage) {
            case 0:
                return getTrunkPath("Idle");
            case 1:
                return getTrunkPath("Working");
            case 2:
                return getTrunkPath("Meltingpoint");
            case 3:
            default:
                return getTrunkPath("Overheat");
        }
    }

    protected void sendPower(IEnergyHandler handler, ForgeDirection direction, boolean simulation) {
        int receivedEnergy = handler.receiveEnergy(direction.getOpposite(), Math.min(getStorage().getEnergyStored(), getStorage().getMaxEnergyStored()), simulation);
        if (receivedEnergy > 0) {
            getStorage().extractEnergy(receivedEnergy, simulation);
        }
    }

    @Override
    public boolean onActivated(EntityPlayer player, ForgeDirection side) {
        if (PlayerHelper.isPlayerSneaking(player) && CoreResources.isDevWorkspace()) {
            PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Facing-Direction: '%s'", getDirection().name());
            PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Current Energy Level: '%d/%d'", getStorage().getEnergyStored(), getStorage().getMaxEnergyStored());
            PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Current Heat Level: '%f/%f'", getHeat(), getMaxHeat());
            PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Progress: '%f', Piston Speed: '%f'", getEngineProgress(), getEnginePistonSpeed());
            return true;
        }
        return false;
    }

    @Override
    public void validate() {
        super.validate();
        checkAndSetOrientation();
    }

    @SideOnly(Side.CLIENT)
    public static String getTrunkPath(String trunkName) {
        return StringHelper.advancedMessage("/custom_resources/textures/models/dynamo_engine/trunk/Trunk_%s.png", trunkName);
    }

    public void checkAndSetOrientation() {
        TileEntity tileDirection = TileEntityHelper.getTileEntityInDirection(this, getDirection());
        if (!(tileDirection instanceof IEnergyHandler)) {
            for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity bruteTile = TileEntityHelper.getTileEntityInDirection(this, direction);
                if (bruteTile instanceof IEnergyHandler) {
                    setRotation(RotationHelper.convertForgeDirectionToByte(direction));
                }
            }
        }
        TileEntityHelper.markBlockForUpdate(this);//Mark it anyway.
    }

    public void recalculateEnergyStage() {
        float heatPercentage = getHeat() / getMaxHeat();
        if (heatPercentage <= 0.0F) {
            energyStage = RandomHelper.convertIntegerToByte(0);
        } else if (heatPercentage <= 0.25F) {
            energyStage = RandomHelper.convertIntegerToByte(1);
        } else if (heatPercentage <= 0.5F) {
            energyStage = RandomHelper.convertIntegerToByte(2);
        } else {
            energyStage = RandomHelper.convertIntegerToByte(3);
        }
        TileEntityHelper.markBlockForUpdate(this);
    }

}

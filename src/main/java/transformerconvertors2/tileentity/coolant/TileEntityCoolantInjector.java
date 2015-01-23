package transformerconvertors2.tileentity.coolant;

import core.api.tileentity.coolant.ICoolantInjector;
import core.api.tileentity.coolant.ICoolantIntake;
import core.tileentity.TileEntityCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Created by Master801 on 11/2/2014.
 * @author Master801
 */
public class TileEntityCoolantInjector extends TileEntityCoreBase implements ICoolantInjector {

    private final int tankSize;
    private final ICoolantIntake[] coolantIntakes = new ICoolantIntake[ForgeDirection.VALID_DIRECTIONS.length];

    public TileEntityCoolantInjector(InjectorTypes type) {
        this.tankSize = type.getTankSize();
    }

    @Override
    public IFluidTank getCoolantTank() {
        return new FluidTank(tankSize);
    }

    @Override
    public ICoolantIntake getIntakeFromSide(ForgeDirection side) {
        return coolantIntakes[side.ordinal()];
    }

    @Override
    public void setIntakeFromSide(ICoolantIntake coolantIntake, ForgeDirection side) {
        coolantIntakes[side.ordinal()] = coolantIntake;
    }

    @Override
    public Container getServerGui(InventoryPlayer inventory) {
        return null;//TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer getClientGui(InventoryPlayer inventory) {
        return null;//TODO
    }

    @Override
    public boolean doesHaveGui() {
        return false;//TODO
    }

    /**
     * Make sure to open the gui if you have it implemented into your tile-entity!
     */
    @Override
    public boolean onActivated(EntityPlayer player, ForgeDirection side) {
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        //TODO
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (getCoolantTank() instanceof FluidTank) {
            ((FluidTank)getCoolantTank()).readFromNBT(nbt);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (getCoolantTank() instanceof FluidTank) {
            ((FluidTank)getCoolantTank()).writeToNBT(nbt);
        }
    }

}

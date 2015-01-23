package transformerconvertors2.tileentity.generator.special;

import core.helpers.RandomHelper;
import core.helpers.RotationHelper;
import core.helpers.TileEntityHelper;
import cpw.mods.fml.common.Optional;
import deatrathias.cogs.gears.Gear;
import deatrathias.cogs.gears.ITurnProvider;
import deatrathias.cogs.gears.ItemCogwheel;
import deatrathias.cogs.gears.TileEntityGear;
import deatrathias.cogs.tools.ICrank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.tileentity.generator.TileEntityGeneratorCoreBase;

/**
 * Created by Master801 on 11/22/2014.
 * @author Master801
 */
@Optional.Interface(iface = "deatrathias.cogs.gears.ITurnProvider", modid = "Cogs")
public class TileEntityGeneratorMotor extends TileEntityGeneratorCoreBase implements ITurnProvider {

    public static final float MAX_STRENGTH_TO_OUTPUT = 45.25F;
    public static final float MAX_SPEED_TO_OUTPUT = 275.50F;

    private Object gear = null;
    private boolean isRotatingCounterClockwise = false;

    public TileEntityGeneratorMotor() {
        super(5740, 0, 100);
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public boolean getTurnDirection() {
        return isRotatingCounterClockwise;
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public float getProvidingSpeed() {
        return TileEntityGeneratorMotor.MAX_SPEED_TO_OUTPUT;
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public float getStrength() {
        return TileEntityGeneratorMotor.MAX_STRENGTH_TO_OUTPUT;
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public Gear getConnectedGear() {
        return (Gear)gear;
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public int getProvidingSide() {
        return getRotation();
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public void setConnectedGear(Gear gear) {
        if (this.gear != gear) {
            this.gear = gear;
        }
        TileEntityHelper.markBlockForUpdate(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        isRotatingCounterClockwise = nbt.getBoolean("rotatingSign");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("rotatingSign", isRotatingCounterClockwise);
    }

    @Override
    @Optional.Method(modid = "Cogs")
    protected void updateGenerator() {
        for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = TileEntityHelper.getTileEntityInDirection(this, direction);
            if (tile instanceof TileEntityGear) {
                TileEntityGear tileGear = (TileEntityGear)tile;
                Gear gear = tileGear.gears[RotationHelper.convertForgeDirectionToInt(direction)];
                if (gear != null) {
                    if (gear.getLinkedProvider() != this) {
                        gear.setLinkedProvider(this);
                    }
                    this.gear = gear;
                }
            }
        }
        if (gear instanceof Gear) {
            if (((Gear)gear).getLinkedProvider() != this) {
                ((Gear)gear).setLinkedProvider(this);
            }
        }
        if (gear != null) {
            float cogSpeed = 0.0F;
            cogSpeed = Math.max(((Gear)gear).getActualSpeed(), cogSpeed);//FIXME This equation is just some random equation. Only used for testing purposes for now, until I find the correct ratio.
            getStorage().modifyEnergyStored(RandomHelper.convertFloatToInteger(cogSpeed));
        }
    }

    @Override
    @Optional.Method(modid = "Cogs")
    public boolean onActivated(EntityPlayer player, ForgeDirection side) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null) {
            Item stackItem = stack.getItem();
            if (stackItem != null) {
                if (side == getDirection() && stackItem instanceof ItemCogwheel) {
                    return false;
                }
                if (stackItem instanceof ICrank) {
                    setRotation(RotationHelper.convertForgeDirectionToByte(side));
                    return true;
                }
            }
        }
        return super.onActivated(player, side);
    }

}

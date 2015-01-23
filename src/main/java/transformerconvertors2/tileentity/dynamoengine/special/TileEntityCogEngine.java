package transformerconvertors2.tileentity.dynamoengine.special;

import core.helpers.RandomHelper;
import core.helpers.RotationHelper;
import core.helpers.TileEntityHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deatrathias.cogs.gears.Gear;
import deatrathias.cogs.gears.ITurnConsumer;
import deatrathias.cogs.gears.TileEntityGear;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;

/**
 * Created by Master801 on 11/10/2014.
 * @author Master801
 */
@Optional.Interface(iface = "deatrathias.cogs.gears.ITurnConsumer", modid = "Cogs")
public class TileEntityCogEngine extends TileEntityDynamoEngineConvertorBase implements ITurnConsumer {

    public static final float SPEED_MODIFIER = 32.0F;
    public static final float STRENGTH_MODIFIER = 8.0F;
    private boolean foundGear = false;

    public TileEntityCogEngine() {
        super(5000, 250.144F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDynamoTexture() {
        return "/custom_resources/textures/models/dynamo_engine/special/Cogs_Engine.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDynamoChamberTexture() {
    	return "/custom_resources/textures/models/dynamo_engine/chamber/special/Cogs_Chamber.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesUseResourceLocation() {
        return false;
    }

    /**
     * @return The speed to take away from the current gear network.
     */
    @Override
    public float getConsumedSpeed() {
        return SPEED_MODIFIER;
    }

    /**
     * @return The strength to take away from the current gear network.
     */
    @Override
    public float getConsumedStrength() {
        return STRENGTH_MODIFIER;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        foundGear = nbt.getBoolean("gear");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("gear", foundGear);
    }

    @Override
    public boolean isAllowedToSendPower() {
        return super.checkRedstoneSignalAndPower() && foundGear;
    }

    @Override
    @Optional.Method(modid = "Cogs")
    protected void updateDynamoEngine() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity gotTile = TileEntityHelper.getTileEntityInDirection(this, direction);
            if (gotTile instanceof TileEntityGear) {
                Gear gear = ((TileEntityGear) gotTile).gears[RotationHelper.convertForgeDirectionToInt(direction)];
                if (gear != null) {
                    foundGear = true;
                    if (gear.getLinkedConsumer() != this) {
                        gear.setLinkedConsumer(this);
                    }
                    float cogSpeed = 0.0F;
                    cogSpeed = Math.max(gear.getActualSpeed(), cogSpeed);//FIXME This equation is just some random equation. Only used for testing purposes for now, until I find the correct ratio.
                    getStorage().modifyEnergyStored(RandomHelper.convertFloatToInteger(cogSpeed));
                }
            }
        }
    }

}

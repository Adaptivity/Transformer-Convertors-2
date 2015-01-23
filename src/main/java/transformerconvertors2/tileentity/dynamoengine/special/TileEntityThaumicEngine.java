package transformerconvertors2.tileentity.dynamoengine.special;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import transformerconvertors2.api.thaumcraft.aspects.AspectHolder;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;

/**
 * Created by Master801 on 11/20/2014.
 * @author Master801
 */
@Optional.Interface(iface = "thaumcraft.api.aspects.IEssentiaTransport", modid = "Thaumcraft")
public class TileEntityThaumicEngine extends TileEntityDynamoEngineConvertorBase implements IEssentiaTransport {

    private AspectHolder aspectHolder = new AspectHolder(Aspect.ENERGY);

    public TileEntityThaumicEngine() {
        super(5200, 375.0F);
    }

    @Override
    public boolean isAllowedToSendPower() {
        return true;//TODO
    }

    /**
     * Only runs on the server side.
     */
    @Override
    protected void updateDynamoEngine() {
        //TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDynamoTexture() {
        return "/custom_resources/textures/models/special/Thaumic_Engine.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTrunkTexture() {
        return "/custom_resources/textures/models/chamber/special/Thaumic_Chamber.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesUseResourceLocation() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (aspectHolder == null) {
            aspectHolder = new AspectHolder(Aspect.ENERGY);
        }
        aspectHolder.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (aspectHolder == null) {
            aspectHolder = new AspectHolder(Aspect.ENERGY);
        }
        aspectHolder.writeToNBT(nbt);
    }

    /**
     * Is this tile able to connect to other vis users/sources on the specified side?
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean isConnectable(ForgeDirection face) {
        return face != getDirection();
    }

    /**
     * Is this side used to input essentia?
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean canInputFrom(ForgeDirection face) {
        return isConnectable(face);
    }

    /**
     * Is this side used to output essentia?
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean canOutputTo(ForgeDirection face) {
        return isConnectable(face);
    }

    /**
     * Sets the amount of suction this block will apply
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public void setSuction(Aspect aspect, int amount) {
    }

    /**
     * Returns the type of suction this block is applying.
     *
     * @param face@return a return type of null indicates the suction is untyped and the first thing available will be drawn
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public Aspect getSuctionType(ForgeDirection face) {
        if (face == getDirection()) {
            return null;
        }
        return null;
    }

    /**
     * Returns the strength of suction this block is applying.
     *
     * @param face@return
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public int getSuctionAmount(ForgeDirection face) {
        if (face == getDirection()) {
            return 0;
        }
        return 0;
    }

    /**
     * remove the specified amount of essentia from this transport tile
     * @return how much was actually taken
     */
    @Optional.Method(modid = "Thaumcraft")
    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        if (face == getDirection()) {
            return 0;
        }
        return 0;
    }

    /**
     * add the specified amount of essentia to this transport tile
     * @return how much was actually added
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        if (face == getDirection()) {
            return 0;
        }
        return 0;
    }

    /**
     * What type of essentia this contains
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public Aspect getEssentiaType(ForgeDirection face) {
        if (!isConnectable(face)) {
            return null;
        }
        return Aspect.ENERGY;
    }

    /**
     * How much essentia this block contains
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public int getEssentiaAmount(ForgeDirection face) {
        return 0;
    }

    /**
     * Essentia will not be drawn from this container unless the suction exceeds this amount.
     *
     * @return the amount
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public int getMinimumSuction() {
        return 0;
    }

    /**
     * Return true if you want the conduit to extend a little further into the block.
     * Used by jars and alembics that have smaller than normal hitboxes
     */
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean renderExtendedTube() {
        return true;
    }

}

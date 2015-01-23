package transformerconvertors2.blocks.generator.special;

import core.api.block.IRender;
import core.api.common.mod.IMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.blocks.generator.BlockGenerator;
import transformerconvertors2.tileentity.generator.special.TileEntityGeneratorMotor;

/**
 * Created by Master801 on 11/22/2014.
 * @author Master801
 */
public class BlockGeneratorMotor extends BlockGenerator implements IRender {

    public static final IIcon[] ICONS_ARRAY = new IIcon[6];

    public BlockGeneratorMotor() {
        super(Material.iron, false);
    }

    /**
     * Do not remove this method.
     * This for setting the unlocalized name.
     * Which you actually can't set in the constructor. :\
     * This may seem redundant, but it's worth it.
     */
    @Override
    public String getAdjustedUnlocalizedName() {
        return "generator.special.motor";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void registerIcon(TextureMap map) {
        ICONS_ARRAY[0] = map.registerIcon("cogs:turnSign");
        //TODO Need to make sprites.
    }

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityGeneratorMotor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getBlockTexture(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityGeneratorMotor) {
            TileEntityGeneratorMotor motor = (TileEntityGeneratorMotor)tile;
            switch(side) {
                case DOWN:
                    return null;//TODO
                case UP:
                    return null;//TODO
                case NORTH:
                    return null;//TODO
                case SOUTH:
                    return null;//TODO
                case WEST:
                    return null;//TODO
                case EAST:
                    return null;//TODO
            }
            if (side == motor.getDirection()) {
                return ICONS_ARRAY[0];//Turn sign.
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getIcon(ForgeDirection side, int metadata) {
        return super.getIcon(side, metadata);//TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return super.getRenderBlockPass();//TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInPass(int pass) {
        return super.canRenderInPass(pass);//TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.Generator_Motor";
    }

    @Override
    public IMod getMod() {
        return TransformerConvertors2.instance;
    }

}

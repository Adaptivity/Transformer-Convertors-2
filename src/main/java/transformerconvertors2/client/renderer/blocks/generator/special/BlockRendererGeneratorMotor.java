package transformerconvertors2.client.renderer.blocks.generator.special;

import core.api.common.mod.IMod;
import core.client.renderer.block.BlockRendererCoreBase;
import core.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.tileentity.generator.special.TileEntityGeneratorMotor;

/**
 * Created by Master801 on 11/22/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class BlockRendererGeneratorMotor extends BlockRendererCoreBase {

    @Override
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.Generator_Motor";
    }

    @Override
    public IMod getOwningMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    protected boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        TileEntity tile = world.getTileEntity(coords.getX(), coords.getY(), coords.getZ());
        if (tile instanceof TileEntityGeneratorMotor) {
            TileEntityGeneratorMotor motor = (TileEntityGeneratorMotor)tile;
            BlockRendererGeneratorMotor.renderTurnSign(tessellator, world.getBlock(coords.getX(), coords.getY(), coords.getZ()).getIcon(world, coords.getX(), coords.getY(), coords.getZ(), motor.getProvidingSide()), motor.getTurnDirection());
            return true;
        }
        return false;
    }

    @Override
    public String getRendererName() {
        return "Generator_Motor";
    }

    /**
     * Note, this method creates a new "shell" that the tessellator is apart of.
     */
    @SideOnly(Side.CLIENT)
    public static void renderTurnSign(Tessellator tessellator, IIcon turnIcon, boolean isCounterClockwise) {
        GL11.glPushMatrix();
        tessellator.startDrawingQuads();
        if (isCounterClockwise) {
            //TODO
        } else {
            //TODO
        }
        tessellator.draw();
        GL11.glPopMatrix();
    }

}

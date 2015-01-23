package transformerconvertors2.client.renderer.blocks.misc;

import core.api.common.mod.IMod;
import core.client.renderer.block.BlockRendererCoreBase;
import core.helpers.RandomHelper;
import core.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 11/13/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class BlockRendererReinforcedRefinery extends BlockRendererCoreBase {

    @Override
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.Reinforced_Refinery";
    }

    @Override
    public IMod getOwningMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    protected boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        return true;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        TileEntityRendererReinforcedRefinery reinforcedRefineryRenderer = new TileEntityRendererReinforcedRefinery();
        reinforcedRefineryRenderer.render(null, RandomHelper.convertFloatToInteger(-0.5F), RandomHelper.convertFloatToInteger(-0.5F), RandomHelper.convertFloatToInteger(-0.5F));
    }

    @Override
    public String getRendererName() {
        return "Reinforced_Refinery_Inventory_Renderer";
    }

}

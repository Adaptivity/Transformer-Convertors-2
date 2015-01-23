package transformerconvertors2.client.renderer.blocks.dynamo;

import core.api.common.mod.IMod;
import core.client.renderer.block.BlockRendererCoreBase;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.helpers.WrapperHelper.WrappingData;
import core.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.blocks.dynamo.BlockDynamoEngine;

@SideOnly(Side.CLIENT)
public final class BlockRendererDynamoEngine extends BlockRendererCoreBase {

    @Override
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.DynamoEngine";
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
		if (block instanceof BlockDynamoEngine) {
			WrappingData wrappingData = ((BlockDynamoEngine)block).getWrappingData(metadata);
			if (wrappingData != null) {
				Object[] data = wrappingData.getData();
                TileEntityRendererDynamoEngine dynamoEngineRenderer = new TileEntityRendererDynamoEngine(true);
                dynamoEngineRenderer.renderEngineInInventory(ForgeDirection.UP, (Boolean) data[0], 0.25F, (String) data[1], (String) data[2], (String) data[3]);
                return;
			}
            throw new CoreNullPointerException("The Dynamo Engine's Wrapping Data is null, or missing!");
		}
	}

	@Override
	public String getRendererName() {
		return "dynamoEngineItemRenderer";
	}

}

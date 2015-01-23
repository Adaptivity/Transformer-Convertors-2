package transformerconvertors2.blocks.dynamo.special;

import core.helpers.WrapperHelper.WrappingData;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import transformerconvertors2.blocks.dynamo.BlockDynamoEngine;
import transformerconvertors2.tileentity.dynamoengine.special.TileEntityCogEngine;

/**
 * Created by Master801 on 11/12/2014.
 * @author Master801
 */
public class BlockCogEngine extends BlockDynamoEngine {

    public BlockCogEngine() {
        super(Material.wood, false);
    }

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityCogEngine();
    }

    /**
     * DoesUseResourceLocation, Dynamo Engine Base Texture, Trunk Texture, and Chamber Texture.
     * @param metadata The metadata you want to render at.
     */
    @Override
    public WrappingData getWrappingData(int metadata) {
        return new WrappingData(new Boolean(false), "/custom_resources/textures/models/dynamo_engine/special/Cogs_Engine.png", "/custom_resources/textures/models/dynamo_engine/trunk/Trunk_Idle.png", "/custom_resources/textures/models/dynamo_engine/chamber/special/Cogs_Chamber.png");
    }

}

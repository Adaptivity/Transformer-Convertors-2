package transformerconvertors2.proxies;

import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import core.common.resources.CoreEnums;
import core.helpers.LoggerHelper;
import core.helpers.ProxyHelper;
import core.helpers.RegistryHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import deatrathias.cogs.gears.TileEntityGear;
import deatrathias.cogs.util.ItemLoader;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.client.renderer.blocks.dynamo.BlockRendererDynamoEngine;
import transformerconvertors2.client.renderer.blocks.dynamo.TileEntityRendererDynamoEngine;
import transformerconvertors2.client.renderer.blocks.generator.special.BlockRendererGeneratorMotor;
import transformerconvertors2.client.renderer.blocks.misc.BlockRendererReinforcedRefinery;
import transformerconvertors2.client.renderer.blocks.misc.TileEntityRendererGear;
import transformerconvertors2.client.renderer.blocks.misc.TileEntityRendererReinforcedRefinery;
import transformerconvertors2.client.renderer.blocks.misc.TileEntityRendererReinforcedTank;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.common.resources.ConvertorResources.ConvertorsItemList;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedRefinery;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedTank;
import transformerconvertors2.utilities.Utilities;

public class ConvertorsClientProxy implements IProxy {

    @Override
    public void registerBlockRenderers() {
        RegistryHelper.registerBlockHandler(new BlockRendererDynamoEngine());
        RegistryHelper.registerBlockHandler(new BlockRendererReinforcedRefinery());
        if (Loader.isModLoaded("Cogs") && Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableCogsConversion"))) {
            RegistryHelper.registerBlockHandler(new BlockRendererGeneratorMotor());
        }
    }

    @Override
    public void registerTileEntityRenderers() {
        RegistryHelper.bindSpecialRendererToTileEntity(TileEntityDynamoEngineConvertorBase.class, new TileEntityRendererDynamoEngine());
        if (Loader.isModLoaded("BuildCraft|Factory")) {
            RegistryHelper.bindSpecialRendererToTileEntity(TileEntityReinforcedRefinery.class, new TileEntityRendererReinforcedRefinery());
            RegistryHelper.bindSpecialRendererToTileEntity(TileEntityReinforcedTank.class, new TileEntityRendererReinforcedTank());
        }
        if (Loader.isModLoaded("Cogs") && Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableNewGears(WIP)"))) {
            LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, CoreEnums.LoggerEnum.INFO, "Replacing default Cogs' gear tile-entity renderer...");
            RegistryHelper.bindSpecialRendererToTileEntity(TileEntityGear.class, new TileEntityRendererGear(), true);
        }
    }

    @Override
    public void registerItemRenderers() {
        if (Loader.isModLoaded("BuildCraft|Transport")) {
            Utilities.registerPipeItemRenderer(ConvertorsItemList.itemPipeHeavyDutyRedstoneConductivePipe);
            Utilities.registerPipeItemRenderer(ConvertorsItemList.itemPipeHeavyDutyRedstoneConductiveReceiverPipe);
        }
        if (Loader.isModLoaded("Cogs") && Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableNewGears(WIP)"))) {
            LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, CoreEnums.LoggerEnum.INFO, "Replacing default Cogs' gear item renderer...");
            RegistryHelper.registerItemRenderer(ItemLoader.itemCogwheel, TileEntityRendererGear.createNewItemRendererInstance());
        }
    }

    @Override
    public void addRenderID(String id, int renderID) {
        ProxyHelper.addRenderIDToMapping(ProxyHelper.getSidedProxyFromMod(getOwningMod(), Side.CLIENT), id, renderID);
    }

    @Override
    public int getRenderIDFromSpecialID(String id) {
        return ProxyHelper.getRenderIDFromSpecialID(ProxyHelper.getSidedProxyFromMod(getOwningMod(), Side.CLIENT), id);
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public IMod getOwningMod() {
        return TransformerConvertors2.instance;
    }

}

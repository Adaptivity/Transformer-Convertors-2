package transformerconvertors2;

import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import core.helpers.ModHelper;
import core.helpers.PacketHelper;
import core.helpers.ProxyHelper;
import core.helpers.RegistryHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import transformerconvertors2.common.integration.IntegrationBoP;
import transformerconvertors2.common.integration.IntegrationBuildCraft;
import transformerconvertors2.common.integration.IntegrationCogs;
import transformerconvertors2.common.integration.IntegrationTinkersConstruct;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.common.resources.ConvertorResources.ConvertorsBlockList;
import transformerconvertors2.common.resources.TransformerConvertors2Metadata;
import transformerconvertors2.itemblocks.coolant.ItemBlockCoolantInjector;
import transformerconvertors2.tileentity.coolant.TileEntityCoolantInjector;
import transformerconvertors2.utilities.ConvertorsEventHandler;

/**
 * https://bitbucket.org/master801/transformer-converters/
 * https://bitbucket.org/master801/transformer-convertors-2/
 * @author Master801
 */
@Mod(modid = ConvertorResources.TC2_MODID, name = ConvertorResources.TC2_NAME, version = ConvertorResources.TC2_VERSION, dependencies = ConvertorResources.TC2_DEPENDENCIES)
public final class TransformerConvertors2 implements IMod {

    @Instance(ConvertorResources.TC2_MODID)
    public static IMod instance;

    @SidedProxy(serverSide = ConvertorResources.TC2_PROXY_SERVER, clientSide =  ConvertorResources.TC2_PROXY_CLIENT, modId = ConvertorResources.TC2_MODID)
    public static IProxy proxy;

    @Metadata(ConvertorResources.TC2_MODID)
    public static ModMetadata metadata;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        ModHelper.addChildMod(TransformerConvertors2.class);
        ConvertorResources.TC2_CONFIG_FILE.loadConfigFile();
        ConvertorResources.TC2_CONFIG_FILE.setValueFromKey("enableCogsConversion", true);
        ConvertorResources.TC2_CONFIG_FILE.setValueFromKey("enableNewGears(WIP)", true);
        ConvertorResources.TC2_CONFIG_FILE.saveConfigFile();
        ModHelper.handshakeMetadata(TransformerConvertors2.metadata, new TransformerConvertors2Metadata());
        RegistryHelper.registerTileEntity(TileEntityCoolantInjector.class, "coolantInjector");
        RegistryHelper.registerBlock(ConvertorsBlockList.COOLANT_INJECTOR, ItemBlockCoolantInjector.class, "coolantInjector");
        RegistryHelper.registerModIntegrationHandlers(new IntegrationCogs(), new IntegrationTinkersConstruct(), new IntegrationBoP(), new IntegrationBuildCraft());
        ProxyHelper.addProxyToMapping(TransformerConvertors2.proxy);
        RegistryHelper.postModEventToIntegrationHandlers(TransformerConvertors2.instance, event);
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        RegistryHelper.registerGuiHandler(TransformerConvertors2.instance);
        MinecraftForge.EVENT_BUS.register(ConvertorsEventHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(ConvertorsEventHandler.INSTANCE);
        PacketHelper.addPacketEventHandler(TransformerConvertors2.instance, ConvertorResources.getConvertorsEventChannel(), ConvertorResources.TC2_CHANNEL);
        TransformerConvertors2.proxy.registerBlockRenderers();
        TransformerConvertors2.proxy.registerTileEntityRenderers();
        TransformerConvertors2.proxy.registerItemRenderers();
        RegistryHelper.postModEventToIntegrationHandlers(TransformerConvertors2.instance, event);
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        RegistryHelper.postModEventToIntegrationHandlers(TransformerConvertors2.instance, event);
    }

    @Override
    public String getModID() {
        return ConvertorResources.TC2_MODID;
    }

    @Override
    public IMod getInstance() {
        return TransformerConvertors2.instance;
    }

}

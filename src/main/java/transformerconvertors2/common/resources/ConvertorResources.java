package transformerconvertors2.common.resources;

import core.api.common.IConfigFile;
import core.block.BlockCoreBase;
import core.common.resources.CoreResources;
import core.helpers.ConfigFileHelper;
import core.helpers.NetworkHelper;
import core.helpers.ReflectionHelper;
import core.item.ItemCoreBase;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deatrathias.cogs.gears.GearConstants;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.blocks.coolant.BlockCoolantInjector;
import transformerconvertors2.blocks.dynamo.BlockDynamoEngine;
import transformerconvertors2.blocks.dynamo.special.BlockCogEngine;
import transformerconvertors2.blocks.generator.special.BlockGeneratorMotor;
import transformerconvertors2.blocks.misc.BlockReinforcedRefinery;
import transformerconvertors2.blocks.misc.BlockReinforcedTank;
import transformerconvertors2.items.ItemFragment;
import transformerconvertors2.utilities.Utilities;

public final class ConvertorResources {

    public static final String TC2_MODID = "TC2";
    public static final String TC2_NAME = "Transformer Convertors 2";
    public static final String TC2_VERSION = "@VERSION@";
    public static final String TC2_DEPENDENCIES = "required-after:" + CoreResources.CORE_LIBRARY_MOD_ID + "; required-after:CoFHCore; after:BuildCraft|Energy; after:Cogs; after:TConstruct; after:BiomesOPlenty";
    public static final String TC2_PROXY_SERVER = "transformerconvertors2.proxies.ConvertorsCommonProxy";
    public static final String TC2_PROXY_CLIENT = "transformerconvertors2.proxies.ConvertorsClientProxy";
    public static final String TC2_CHANNEL = ConvertorResources.TC2_MODID;
    public static final IConfigFile TC2_CONFIG_FILE = ConfigFileHelper.createNewConfigFile(TransformerConvertors2.instance, "Transformer Convertors 2", "\n Convert energies to other energies!\n Plus random junk.\n");
    public static final CreativeTabs TC2_CREATIVE_TAB = new CreativeTabs("transformerConvertors2.name") {

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            if (Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableCogEngine")) && Loader.isModLoaded("Cogs") && ConvertorsBlockList.COG_ENGINE != null) {
                return Item.getItemFromBlock(ConvertorsBlockList.COG_ENGINE);
            }
            Block engineBlock = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.BuildCraftEnergy", "engineBlock"), null);
            return new ItemStack(engineBlock, 1, 2).getItem();
        }

    };

    public static FMLEventChannel getConvertorsEventChannel() {
        return NetworkHelper.getEventChannel(ConvertorResources.TC2_MODID);
    }

    public static final class ConvertorsItemList {

        public static Item itemPipeHeavyDutyRedstoneConductivePipe = null;
        public static Item itemPipeHeavyDutyRedstoneConductiveReceiverPipe = null;
        public static final ItemCoreBase ITEM_FRAGMENT = new ItemFragment();

    }

    public static final class ConvertorsBlockList {

        public static final BlockCoreBase REINFORCED_REFINERY = new BlockReinforcedRefinery();
        public static final BlockCoreBase REINFORCED_TANK = new BlockReinforcedTank();
        public static final BlockCoreBase COOLANT_INJECTOR = new BlockCoolantInjector();
        public static final BlockDynamoEngine COG_ENGINE = new BlockCogEngine();
        public static final BlockCoreBase COG_MOTOR = new BlockGeneratorMotor();

    }

    public static final class NewGearTypes {

        public static GearConstants.GearBaseType stoneGear = null;
        public static GearConstants.GearBaseType tinGear = null;

        public static void initGears() {
            if (stoneGear == null) {
                stoneGear = Utilities.createNewCogWheel("STONE", "stoneCogwheel", 21.0F, 3.75F, 0);
            }
            if (tinGear == null) {
                tinGear = Utilities.createNewCogWheel("TIN", "tinCogwheel", 53.50F, 1.50F, 2);
            }
        }

    }

}

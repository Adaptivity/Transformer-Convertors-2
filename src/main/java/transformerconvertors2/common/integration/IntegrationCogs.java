package transformerconvertors2.common.integration;

import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.helpers.ExternalModsHelper;
import core.helpers.RandomHelper;
import core.helpers.RegistryHelper;
import core.helpers.StringHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import deatrathias.cogs.util.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.common.resources.ConvertorResources.ConvertorsBlockList;
import transformerconvertors2.common.resources.ConvertorResources.NewGearTypes;
import transformerconvertors2.itemblocks.dynamo.special.ItemBlockCogEngine;
import transformerconvertors2.items.ItemFragment;
import transformerconvertors2.tileentity.dynamoengine.special.TileEntityCogEngine;
import transformerconvertors2.tileentity.generator.special.TileEntityGeneratorMotor;

/**
 * Created by Master801 on 11/27/2014 at 8:26 PM.
 * @author Master801
 */
public final class IntegrationCogs implements IModIntegrationHandler {

    @Override
    public void addModBlocksAndItems() {
        if (Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableCogsConversion"))) {
            RegistryHelper.registerTileEntity(TileEntityCogEngine.class, "dynamo_engine.special.cogEngine");
            RegistryHelper.registerBlock(ConvertorsBlockList.COG_ENGINE, ItemBlockCogEngine.class, "dynamo_engine.special.cogEngine");
            RegistryHelper.setBlockAsFlammable(ConvertorsBlockList.COG_ENGINE, 5, 20);
            RegistryHelper.registerTileEntity(TileEntityGeneratorMotor.class, "tiles.generator.special.motor");
            RegistryHelper.registerBlock(ConvertorsBlockList.COG_MOTOR, "blocks.generator.special.motor");
        }
        if (Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableNewGears(WIP)"))) {
            ConvertorResources.NewGearTypes.initGears();
        }
        GameRegistry.registerItem(ConvertorResources.ConvertorsItemList.ITEM_FRAGMENT, "fragment");
        for(int i = 0; i < ItemFragment.FRAGMENT_NAMES.length; i++) {
            OreDictionary.registerOre("fragment" + StringHelper.makeFirstCharacterAsUppercase(ItemFragment.FRAGMENT_NAMES[i]), new ItemStack(ConvertorResources.ConvertorsItemList.ITEM_FRAGMENT, 1, i));
        }
    }

    @Override
    public void addModRecipes() {
        if (Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableCogsConversion"))) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ConvertorsBlockList.COG_ENGINE, 1, 0), "WWW", " G ", "wPw", 'W', Blocks.planks, 'G', Blocks.glass, 'w', new ItemStack(ItemLoader.itemCogwheel, 1, 0), 'P', Blocks.piston));//Cog engine
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ConvertorsBlockList.COG_MOTOR, 1, 0), "IGI", "GXG", "IGI", 'I', Blocks.planks, 'G', new ItemStack(ExternalModsHelper.getCogsCogwheelItem(), 1, 3), 'X', Blocks.piston));//Cog motor
        }
        if (Boolean.valueOf(ConvertorResources.TC2_CONFIG_FILE.getValueFromKey("enableNewGears(WIP)"))) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ExternalModsHelper.getCogsCogwheelItem(), 8, NewGearTypes.stoneGear.ordinal()), "RCR", "C C", "RCR", 'R', ExternalModsHelper.getCogStack("itemMaterial", 1, 5), 'C', Blocks.cobblestone));//Stone cogwheel
        }
        ExternalModsHelper.addStonePounderRecipe("oreNickel", RandomHelper.duplicateObjectToArray(new ItemStack(ConvertorResources.ConvertorsItemList.ITEM_FRAGMENT, 1, 0), 3), new int[] { 100, 50, 20 } );//Nickel
        ExternalModsHelper.addStonePounderRecipe("orePlatinum", RandomHelper.duplicateObjectToArray(new ItemStack(ConvertorResources.ConvertorsItemList.ITEM_FRAGMENT, 1, 1), 3), new int[] { 100, 50, 20 } );//Platinum
        ExternalModsHelper.addStonePounderRecipe("oreMithril", RandomHelper.duplicateObjectToArray(new ItemStack(ConvertorResources.ConvertorsItemList.ITEM_FRAGMENT, 1, 2), 3), new int[] { 100, 50, 20 } );//Mithril
    }

    @Override
    public IMod getParentMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    public String getModIDToIntegrateWith() {
        return "Cogs";
    }

}

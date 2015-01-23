package transformerconvertors2.common.integration;

import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.helpers.ExternalModsHelper;
import core.helpers.RandomHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.common.Loader;
import deatrathias.cogs.material.ItemMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 11/27/2014 at 8:27 PM.
 * @author Master801
 */
public final class IntegrationTinkersConstruct implements IModIntegrationHandler {

    @Override
    public void addModBlocksAndItems() {
    }

    @Override
    public void addModRecipes() {
        if (Loader.isModLoaded("Cogs")) {
            addCompatibleRecipes();
        }
    }

    @Override
    public IMod getParentMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    public String getModIDToIntegrateWith() {
        return "TConstruct";
    }

    private void addCompatibleRecipes() {
        Item itemMaterial = ReflectionHelper.getFieldValue(ReflectionHelper.getField(ReflectionHelper.getClassFromString("deatrathias.cogs.util.ItemLoader", true), "itemMaterial", true), null, true);
        Block metalBlock = ReflectionHelper.getFieldValue(ReflectionHelper.getField(ReflectionHelper.getClassFromString("tconstruct.world.TinkerWorld", true), "metalBlock", true), null, true);
        Block gravelOreBlock = ReflectionHelper.getFieldValue(ReflectionHelper.getField(ReflectionHelper.getClassFromString("tconstruct.world.TinkerWorld", true), "oreGravel", true), null, true);
        if (itemMaterial == null || metalBlock == null || gravelOreBlock == null) {
            return;
        }
        ExternalModsHelper.addSmelteryRecipe(new ItemStack(itemMaterial, 1, ItemMaterial.MaterialType.FRAGMENT_COPPER.ordinal()), metalBlock, 3, 0, null);//Copper
        ExternalModsHelper.addSmelteryRecipe(new ItemStack(itemMaterial, 1, ItemMaterial.MaterialType.FRAGMENT_IRON.ordinal()), Blocks.iron_block, 0, 600, null);//Iron
        ExternalModsHelper.addSmelteryRecipe(new ItemStack(itemMaterial, 1, ItemMaterial.MaterialType.FRAGMENT_GOLD.ordinal()), Blocks.gold_block, 0, 0, null);//Gold
        ExternalModsHelper.addStonePounderRecipe(gravelOreBlock, 0, IntegrationTinkersConstruct.createMaterialArray(itemMaterial, ItemMaterial.MaterialType.FRAGMENT_IRON.ordinal()), new int[] { 100, 50, 20, 10, 2 });
        ExternalModsHelper.addStonePounderRecipe(gravelOreBlock, 1, IntegrationTinkersConstruct.createMaterialArray(itemMaterial, ItemMaterial.MaterialType.FRAGMENT_GOLD.ordinal()), new int[] { 100, 50, 20, 10, 2 });
        ExternalModsHelper.addStonePounderRecipe(gravelOreBlock, 2, IntegrationTinkersConstruct.createMaterialArray(itemMaterial, ItemMaterial.MaterialType.FRAGMENT_COPPER.ordinal()), new int[] { 100, 50, 20, 10, 2 });
        //TODO Add more gravel ore recipes.
    }

    private static ItemStack[] createMaterialArray(Item itemMaterial, int metadata) {
        ItemStack[] array = RandomHelper.duplicateObjectToArrayWithExtra(new ItemStack(itemMaterial, 1, metadata), 3, 2);
        array[3] = new ItemStack(Items.flint, 1, 0);
        array[4] = new ItemStack(Blocks.gravel, 1, 0);
        return array;
    }

}

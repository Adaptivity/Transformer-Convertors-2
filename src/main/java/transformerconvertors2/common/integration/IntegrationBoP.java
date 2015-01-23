package transformerconvertors2.common.integration;

import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.helpers.ExternalModsHelper;
import core.helpers.RandomHelper;
import core.helpers.ReflectionHelper;
import core.helpers.StringHelper;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 12/3/2014 at 8:51 PM.
 * @author Master801
 */
public final class IntegrationBoP implements IModIntegrationHandler {

    @Override
    public void addModBlocksAndItems() {
    }

    @Override
    public void addModRecipes() {
        if (Loader.isModLoaded("Cogs")) {
            Block[] logs = new Block[4];
            Block planks = ReflectionHelper.getFieldValue(ReflectionHelper.getField(ReflectionHelper.getClassFromString("biomesoplenty.api.content.BOPCBlocks", true), "planks"), null, true);
            for(int i = 0; i < logs.length; i++) {
                logs[i] = ReflectionHelper.getFieldValue(ReflectionHelper.getField(ReflectionHelper.getClassFromString("biomesoplenty.api.content.BOPCBlocks", true), StringHelper.advancedMessage("logs%d", i)), null, true);
                for(int ii = 0; ii < (logs.length * logs.length); ii++) {
                    ExternalModsHelper.addStonePounderRecipe(logs[i], i, RandomHelper.duplicateObjectToArray(new ItemStack(planks, 4, ii), 2), new int[] { 100, 50 });
                }
            }
            ExternalModsHelper.addStonePounderRecipe("planks", new ItemStack[] { new ItemStack(Items.stick, 2, 0), new ItemStack(Items.stick, 1, 0) }, new int[] { 100, 50 });
        }
    }

    @Override
    public IMod getParentMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    public String getModIDToIntegrateWith() {
        return "BiomesOPlenty";
    }

}

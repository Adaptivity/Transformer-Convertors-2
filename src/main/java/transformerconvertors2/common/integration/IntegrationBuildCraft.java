package transformerconvertors2.common.integration;

import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.helpers.ReflectionHelper;
import core.helpers.RegistryHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.pipes.PipeBase;
import transformerconvertors2.pipes.heavyduty.PipeHeavyDutyRedstoneConductivePipe;
import transformerconvertors2.pipes.heavyduty.PipeHeavyDutyRedstoneConductiveReceiverPipe;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedRefinery;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedTank;

/**
 * Created by Master801 on 1/17/2015 at 3:54 PM.
 * @author Master801
 */
public final class IntegrationBuildCraft implements IModIntegrationHandler {

    @Override
    public void addModBlocksAndItems() {
        PipeBase.registerPipeStorageValue(PipeHeavyDutyRedstoneConductiveReceiverPipe.class, 2048);
        PipeBase.registerPipeStorageValue(PipeHeavyDutyRedstoneConductivePipe.class, 4096);
        ConvertorResources.ConvertorsItemList.itemPipeHeavyDutyRedstoneConductiveReceiverPipe = PipeBase.initPipe(PipeHeavyDutyRedstoneConductiveReceiverPipe.class, "pipeHeavyDutyRedstoneConductiveReceiverPipe", ConvertorResources.TC2_CREATIVE_TAB);
        ConvertorResources.ConvertorsItemList.itemPipeHeavyDutyRedstoneConductivePipe = PipeBase.initPipe(PipeHeavyDutyRedstoneConductivePipe.class, "pipeHeavyDutyRedstoneConductivePipe", ConvertorResources.TC2_CREATIVE_TAB);
        RegistryHelper.registerTileEntity(TileEntityReinforcedRefinery.class, "buildcraft.reinforcedRefinery");
        RegistryHelper.registerTileEntity(TileEntityReinforcedTank.class, "buildcraft.reinforcedTank");
        RegistryHelper.registerBlock(ConvertorResources.ConvertorsBlockList.REINFORCED_REFINERY, "buildcraft.reinforcedRefinery");
        RegistryHelper.registerBlock(ConvertorResources.ConvertorsBlockList.REINFORCED_TANK, "buildcraft.reinforcedTank");
    }

    @Override
    public void addModRecipes() {
        Item diamondGearItem = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.BuildCraftCore", "diamondGearItem", false), null);
        GameRegistry.addRecipe(new ItemStack(ConvertorResources.ConvertorsBlockList.REINFORCED_REFINERY), "RTR", "TGT", 'T', ConvertorResources.ConvertorsBlockList.REINFORCED_TANK, 'G', diamondGearItem, 'R', Blocks.redstone_torch);
    }

    @Override
    public IMod getParentMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    public String getModIDToIntegrateWith() {
        return "BuildCraft";
    }

}

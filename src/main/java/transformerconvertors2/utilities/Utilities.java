package transformerconvertors2.utilities;

import core.common.resources.CoreEnums;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.helpers.LoggerHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deatrathias.cogs.gears.GearConstants.GearBaseType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import transformerconvertors2.TransformerConvertors2;

/**
 * @author Master801
 */
public final class Utilities {

    @SideOnly(Side.CLIENT)
    public static void registerPipeItemRenderer(Item pipe) {
        IItemRenderer renderer = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.transport.TransportProxyClient", "pipeItemRenderer"), null);
        MinecraftForgeClient.registerItemRenderer(pipe, renderer);
    }

    public static void addIronEngineFuel(ItemStack stack, int powerPerCycle, int totalBurningTime) {
        if (stack == null) {
            throw new CoreNullPointerException("The ItemStack is null!");
        }
        FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(stack);
        if (fluidStack == null) {
            throw new CoreNullPointerException("The FluidStack is null! Item Name: '%s'", stack.getUnlocalizedName());
        }
        Object fuels = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.api.fuels.BuildcraftFuelRegistry", "fuel"), null);
        ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("buildcraft.api.fuels.BuildcraftFuelRegistry", "addFuel"), fuels, fluidStack.getFluid(), powerPerCycle, totalBurningTime);
    }

    public static void addIronEngineCoolant(ItemStack stack, int cooling) {
        Object fuels = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.api.fuels.BuildcraftFuelRegistry", "fuel"), null);
        ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("buildcraft.api.fuels.BuildcraftFuelRegistry", "addCooland"), fuels, FluidContainerRegistry.getFluidForFilledItem(stack).getFluid(), cooling);
    }

    @Optional.Method(modid = "Cogs")
    public static GearBaseType createNewCogWheel(String enumName, String gearName, float maxGearSpeed, float speedLoss, int texture) {
        GearBaseType type = ReflectionHelper.addNewEnum(GearBaseType.class, enumName, new ReflectionHelper.ClassParameters(String.class, float.class, float.class, int.class), new ReflectionHelper.ObjectParameters(gearName, maxGearSpeed, speedLoss, texture));
        if (type == null) {
            throw new CoreNullPointerException("Failed to create a new gear! Enum name: '%s', Gear name: '%s'", enumName, gearName);
        }
        LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, CoreEnums.LoggerEnum.INFO, "Successfully created a new gear! Gear: '%s'", type.getName());
        return type;
    }

}

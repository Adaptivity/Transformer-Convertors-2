package transformerconvertors2.itemblocks.ic2;

import core.helpers.ItemHelper;
import core.helpers.LanguageHelper;
import core.helpers.RandomHelper;
import core.helpers.StringHelper;
import core.itemblock.ItemBlockCoreBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master801 on 12/6/2014 at 2:49 PM.
 * @author Master801
 */
public final class ItemBlockIC2Storage0 extends ItemBlockCoreBase {

    public ItemBlockIC2Storage0(Block block) {
        super(block, true);
    }

    @Override
    protected String getUnlocalizedName(int metadata) {
        switch(metadata) {
            case 0:
                return "ic2.storage.0";
        }
        return null;
    }

    @Override
    protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
        final List<String> list = new ArrayList<String>();
        list.add(StringHelper.advancedMessage("%s : %d", LanguageHelper.getLocalisedString("ic2.storage.output"), ItemBlockIC2Storage0.getOuput(stack)));
        list.add(StringHelper.advancedMessage("%d/%d", ItemBlockIC2Storage0.getStoredEnergy(stack), ItemBlockIC2Storage0.getMaxCapacity(stack)));
        return list;
    }

    public static double getOuput(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = ItemHelper.getItemStackTagCompound(stack);
            if (nbt.hasKey("output")) {
                return nbt.getDouble("output");
            }
        }
        return RandomHelper.convertIntegerToDouble(("ItemStack has a broken NBTTagCompound! Stack: " + stack).getBytes().length).doubleValue();
    }

    public static void setOutput(ItemStack stack, double output) {
        ItemHelper.getItemStackTagCompound(stack).setDouble("output", output);
    }

    public static double getStoredEnergy(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = ItemHelper.getItemStackTagCompound(stack);
            if (nbt.hasKey("storedEnergy")) {
                return nbt.getDouble("storedEnergy");
            }
        }
        return RandomHelper.convertIntegerToDouble(("ItemStack has a broken NBTTagCompound! Stack: " + stack).getBytes().length).doubleValue();
    }

    public static void setStoredEnergy(ItemStack stack, double storedEnergy) {
        ItemHelper.getItemStackTagCompound(stack).setDouble("storedEnergy", storedEnergy);
    }

    public static double getMaxCapacity(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = ItemHelper.getItemStackTagCompound(stack);
            if (nbt.hasKey("maxCapacity")) {
                return nbt.getDouble("maxCapacity");
            }
        }
        return RandomHelper.convertIntegerToDouble(("ItemStack has a broken NBTTagCompound! Stack: " + stack).getBytes().length).doubleValue();
    }

    public static void setMaxCapacity(ItemStack stack, double capacity) {
        ItemHelper.getItemStackTagCompound(stack).setDouble("maxCapacity", capacity);
    }

}

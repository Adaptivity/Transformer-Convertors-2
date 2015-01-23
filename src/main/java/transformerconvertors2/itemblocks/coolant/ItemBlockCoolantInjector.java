package transformerconvertors2.itemblocks.coolant;

import core.api.tileentity.coolant.ICoolantInjector;
import core.itemblock.ItemBlockCoreBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Master801 on 11/4/2014.
 * @author Master801
 */
public class ItemBlockCoolantInjector extends ItemBlockCoreBase {

    public ItemBlockCoolantInjector(Block block) {
        super(block, true);
    }

    @Override
    protected String getUnlocalizedName(int metadata) {
        return ICoolantInjector.InjectorTypes.values()[metadata].getUnlocalizedName();
    }

    @Override
    protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
        return null;
    }

}

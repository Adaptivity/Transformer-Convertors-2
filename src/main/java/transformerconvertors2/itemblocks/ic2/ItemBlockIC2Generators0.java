package transformerconvertors2.itemblocks.ic2;

import core.itemblock.ItemBlockCoreBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Master801 on 11/7/2014.
 * @author Master801
 */
public class ItemBlockIC2Generators0 extends ItemBlockCoreBase {

    public ItemBlockIC2Generators0(Block block) {
        super(block, true);
    }

    @Override
    protected String getUnlocalizedName(int metadata) {
        switch(metadata) {
            case 0:
                return "tiles.ic2.generator.turbineSolarGenerator";
            default:
                return null;
        }
    }

    @Override
    protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
        return null;
    }

}

package transformerconvertors2.itemblocks.dynamo.special;

import core.helpers.LanguageHelper;
import core.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import transformerconvertors2.itemblocks.dynamo.ItemBlockDynamoEngine;
import transformerconvertors2.tileentity.dynamoengine.special.TileEntityCogEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master801 on 11/12/2014.
 * @author Master801
 */
public class ItemBlockCogEngine extends ItemBlockDynamoEngine {

    public ItemBlockCogEngine(Block block) {
        super(block, false);
    }

    @Override
    protected String getUnlocalizedName(int metadata) {
        return "cogEngine";
    }

    @Override
    protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
        List<String> list = new ArrayList<String>();
        list.add(StringHelper.advancedMessage(LanguageHelper.getLocalisedString("cogEngine.modifier.speed") + ": %f", TileEntityCogEngine.SPEED_MODIFIER));
        list.add(StringHelper.advancedMessage(LanguageHelper.getLocalisedString("cogEngine.modifier.strength" + ": %f"), TileEntityCogEngine.STRENGTH_MODIFIER));
        return list;
    }

}

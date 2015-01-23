package transformerconvertors2.itemblocks.dynamo.ic2;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import transformerconvertors2.itemblocks.dynamo.ItemBlockDynamoEngine;

import java.util.ArrayList;
import java.util.List;

public class ItemBlockElectricDynamoEngine extends ItemBlockDynamoEngine {

	public ItemBlockElectricDynamoEngine(Block block) {
		super(block, true);
	}

	@Override
	protected String getUnlocalizedName(int metadata) {
		switch(metadata) {
		case 0:
			return "lowVoltageElectricDynamoEngine";
		case 1:
			return "mediumVoltageElectricDynamoEngine";
		case 2:
			return "highVoltageElectricDynamoEngine";
		default:
			return "adjustableVoltageElectricDynamoEngine";
		}
	}

	@Override
	protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3) {
		final List<String> list = new ArrayList<String>();
		return list;
	}

}

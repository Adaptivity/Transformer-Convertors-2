package transformerconvertors2.common.triggers.dynamo.ic2;

/*
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.common.triggers.TriggerBase;
import transformerconvertors2.tileentity.dynamoengine.ic2.TileEntityElectricDynamoEngineConvertorBase;
import buildcraft.api.gates.ITriggerParameter;
import core.helpers.LanguageHelper;
*/

//FIXME
/*
public abstract class TriggerElectricity extends TriggerBase {

	protected abstract String getTriggerIconTexture();

	protected abstract String getMoreDescription();

	protected abstract boolean isTriggerActive(ForgeDirection side, TileEntityElectricDynamoEngineConvertorBase engine, ITriggerParameter parameter);

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		triggerIcon = iconRegister.registerIcon(ConvertorResources.TC2_MODID + ":" + "dynamoEngine/Electric/" + getTriggerIconTexture());
	}

	@Override
	public String getDescription() {
		return LanguageHelper.getLocalizedName("transformerconvertors2.triggers.dynamo.electricity." + getMoreDescription());
	}

	public static TriggerElectricity getTriggerElectricityHalfEmpty() {
		return new TriggerElectricityHalfEmpty();
	}

	public static TriggerElectricity getTriggerElectricityEmpty() {
		return new TriggerElectricityEmpty();
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof TileEntityElectricDynamoEngineConvertorBase) {
			return isTriggerActive(side, (TileEntityElectricDynamoEngineConvertorBase)tile, parameter);
		}
		return false;
	}

	private static final class TriggerElectricityHalfEmpty extends TriggerElectricity {

		@Override
		protected String getTriggerIconTexture() {
			return "HalfEmpty";
		}

		@Override
		protected String getMoreDescription() {
			return "halfEmpty";
		}

		@Override
		protected boolean isTriggerActive(ForgeDirection side, TileEntityElectricDynamoEngineConvertorBase engine, ITriggerParameter parameter) {
			if (engine.getCurrentIC2Energy() <= (engine.getMaxIC2Storage(engine.getTier()))) {
				return true;
			}
			return false;
		}

	}

	private static class TriggerElectricityEmpty extends TriggerElectricity {

		@Override
		protected String getTriggerIconTexture() {
			return "Empty";
		}

		@Override
		protected String getMoreDescription() {
			return "empty";
		}

		@Override
		protected boolean isTriggerActive(ForgeDirection side, TileEntityElectricDynamoEngineConvertorBase engine, ITriggerParameter parameter) {
			if (engine.getCurrentIC2Energy() <= 0) {
				return true;
			}
			return false;
		}

	}

}
*/

package transformerconvertors2.common.triggers.dynamo;

import buildcraft.api.statements.IStatementParameter;
import core.api.tileentity.coolant.ICoolantInjector;
import core.helpers.LanguageHelper;
import core.helpers.SpriteHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.common.triggers.TriggerBase;

/**
 * @author Master801
 */
public abstract class TriggerCoolant extends TriggerBase {

    protected abstract String getTriggerName();

    protected abstract int getTriggerNumber();

	protected abstract boolean isTriggerActive(ICoolantInjector cooling, ForgeDirection side, IStatementParameter parameter);

	protected abstract String getMoreDescription();

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
        triggerIcon = SpriteHelper.createNewSpriteSheetAtlas("/custom_resources/textures/triggers/Triggers.png", getTriggerName(), iconRegister, getTriggerNumber(), 0, 32);
    }

	@Override
	public String getDescription() {
		return LanguageHelper.getLocalisedString("transformerconvertors2.triggers.coolant." + getMoreDescription());
	}

//	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, IStatementParameter parameter) {//FIXME Triggers don't use this anymore?
		if (tile instanceof ICoolantInjector) {
			return isTriggerActive((ICoolantInjector)tile, side, parameter);
		}
		return false;
	}

	public static TriggerCoolant getTriggerCoolantTankHalfEmpty() {
		return new TriggerCoolantTankHalfEmpty();
	}

	public static TriggerCoolant getTriggerCoolantTankEmpty() {
		return new TriggerCoolantTankEmpty();
	}

	private static final class TriggerCoolantTankHalfEmpty extends TriggerCoolant {

        @Override
        protected String getTriggerName() {
            return "CoolantTank|Half-Empty";
        }

        @Override
        protected int getTriggerNumber() {
            return 1;
        }

		@Override
		protected boolean isTriggerActive(ICoolantInjector cooling, ForgeDirection side, IStatementParameter parameter) {
			return cooling.getCoolantTank().getFluidAmount() < (cooling.getCoolantTank().getCapacity() / 2);
		}

		@Override
		protected String getMoreDescription() {
			return "halfEmpty";
		}

	}

	private static final class TriggerCoolantTankEmpty extends TriggerCoolant {

        @Override
        protected String getTriggerName() {
            return "CoolantTank|Empty";
        }

        @Override
        protected int getTriggerNumber() {
            return 0;
        }

		@Override
		protected boolean isTriggerActive(ICoolantInjector cooling, ForgeDirection side, IStatementParameter parameter) {
			return cooling.getCoolantTank().getFluidAmount() <= 0;
		}

		@Override
		protected String getMoreDescription() {
			return "empty";
		}

	}

}

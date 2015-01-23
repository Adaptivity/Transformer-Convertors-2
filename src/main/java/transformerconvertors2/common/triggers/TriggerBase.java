package transformerconvertors2.common.triggers;

import buildcraft.api.statements.IStatement;
import buildcraft.core.statements.BCStatement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public abstract class TriggerBase extends BCStatement {

	protected IIcon triggerIcon = null;

	@Override
	public String getUniqueTag() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return triggerIcon;
	}

	@Override
	public IStatement rotateLeft() {
		return this;
	}

}

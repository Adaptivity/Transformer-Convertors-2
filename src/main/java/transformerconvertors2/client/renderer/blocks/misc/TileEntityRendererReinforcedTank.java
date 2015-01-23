package transformerconvertors2.client.renderer.blocks.misc;

import buildcraft.core.render.FluidRenderer;
import buildcraft.core.render.RenderUtils;
import core.client.renderer.tileentity.TileEntityRendererCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedTank;

@SideOnly(Side.CLIENT)
public class TileEntityRendererReinforcedTank extends TileEntityRendererCoreBase {

	@Override
	protected void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        if (tile instanceof TileEntityReinforcedTank) {
            TileEntityReinforcedTank tank = (TileEntityReinforcedTank) tile;
            FluidStack liquid = tank.tank.getFluid();
            int color = tank.tank.colorRenderCache;
            if (liquid == null || liquid.amount <= 0) {
                return;
            }
            int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tank.getWorldObj(), false);
            if (displayList == null) {
                return;
            }
            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            bindTexture(FluidRenderer.getFluidSheet(liquid));
            RenderUtils.setGLColorFromInt(color);
            GL11.glTranslatef(new Double(xCoord).floatValue() + 0.125F, new Double(yCoord).floatValue() + 0.5F, new Double(zCoord).floatValue() + 0.125F);
            GL11.glScalef(0.75F, 0.999F, 0.75F);
            GL11.glTranslatef(0, -0.5F, 0);
            GL11.glCallList(displayList[(int) ((float) liquid.amount / (float) (tank.tank.getCapacity()) * (FluidRenderer.DISPLAY_STAGES - 1))]);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
	}

}

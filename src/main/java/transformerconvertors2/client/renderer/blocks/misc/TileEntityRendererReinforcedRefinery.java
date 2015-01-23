package transformerconvertors2.client.renderer.blocks.misc;

import buildcraft.core.fluids.Tank;
import buildcraft.core.render.FluidRenderer;
import buildcraft.core.render.RenderUtils;
import core.client.renderer.tileentity.TileEntityRendererCoreBase;
import core.helpers.TextureHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedRefinery;

@SideOnly(Side.CLIENT)
public class TileEntityRendererReinforcedRefinery extends TileEntityRendererCoreBase {

	public static final float PIXEL = 1.0F / 16.0F;
	private final ModelRenderer tank;
	private final ModelRenderer[] magnet = new ModelRenderer[4];
	private final ModelBase model = new ModelBase() {};

	public TileEntityRendererReinforcedRefinery() {
		tank = new ModelRenderer(model, 0, 0);
		tank.addBox(-4F, -8F, -4F, 8, 16, 8);
		tank.rotationPointX = 8;
		tank.rotationPointY = 8;
		tank.rotationPointZ = 8;
		for (int i = 0; i < magnet.length; ++i) {
			magnet[i] = new ModelRenderer(model, 32, i * 8);
			magnet[i].addBox(0, -8F, -8F, 8, 4, 4);
			magnet[i].rotationPointX = 8;
			magnet[i].rotationPointY = 8;
			magnet[i].rotationPointZ = 8;
		}
	}

	@Override
	protected void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        if (tile instanceof TileEntityReinforcedRefinery) {
            render((TileEntityReinforcedRefinery)tile, xCoord, yCoord, zCoord);
        }
	}

	public void render(TileEntityReinforcedRefinery refinery, double x, double y, double z) {
		FluidStack liquid1 = null, liquid2 = null, liquidResult = null;
		int color1 = 0xFFFFFF, color2 = 0xFFFFFF, colorResult = 0xFFFFFF;
		float anim = 0;
		int angle = 0;
		ModelRenderer theMagnet = magnet[0];
		if (refinery != null) {
			if (refinery.tanks[0].getFluid() != null) {
				liquid1 = refinery.tanks[0].getFluid();
				color1 = refinery.tanks[0].colorRenderCache;
			}
			if (refinery.tanks[1].getFluid() != null) {
				liquid2 = refinery.tanks[1].getFluid();
				color2 = refinery.tanks[1].colorRenderCache;
			}
			if (refinery.result.getFluid() != null) {
				liquidResult = refinery.result.getFluid();
				colorResult = refinery.result.colorRenderCache;
			}
			anim = refinery.getAnimationStage();
			angle = 0;
			switch (refinery.getWorldObj().getBlockMetadata(refinery.xCoord, refinery.yCoord, refinery.zCoord)) {
			case 2:
				angle = 90;
				break;
			case 3:
				angle = 270;
				break;
			case 4:
				angle = 180;
				break;
			case 5:
				angle = 0;
				break;
			}
			if (refinery.animationSpeed <= 1) {
				theMagnet = magnet[0];
			} else if (refinery.animationSpeed <= 2.5) {
				theMagnet = magnet[1];
			} else if (refinery.animationSpeed <= 4.5) {
				theMagnet = magnet[2];
			} else {
				theMagnet = magnet[3];
			}
		}
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GL11.glScalef(0.99F, 0.99F, 0.99F);
		GL11.glRotatef(angle, 0, 1, 0);
		TextureHelper.bindTexture("/custom_resources/textures/models/misc/Reinforced-Refinery.png");
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		GL11.glTranslatef(-4F * PIXEL, 0, -4F * PIXEL);
		tank.render(PIXEL);
		GL11.glTranslatef(4F * PIXEL, 0, 4F * PIXEL);
		GL11.glTranslatef(-4F * PIXEL, 0, 4F * PIXEL);
		tank.render(PIXEL);
		GL11.glTranslatef(4F * PIXEL, 0, -4F * PIXEL);
		GL11.glTranslatef(4F * PIXEL, 0, 0);
		tank.render(PIXEL);
		GL11.glTranslatef(-4F * PIXEL, 0, 0);
		GL11.glPopMatrix();
		float trans1, trans2;
		if (anim <= 100) {
			trans1 = 12F * PIXEL * anim / 100F;
			trans2 = 0;
		} else if (anim <= 200) {
			trans1 = 12F * PIXEL - (12F * PIXEL * (anim - 100F) / 100F);
			trans2 = 12F * PIXEL * (anim - 100F) / 100F;
		} else {
			trans1 = 12F * PIXEL * (anim - 200F) / 100F;
			trans2 = 12F * PIXEL - (12F * PIXEL * (anim - 200F) / 100F);
		}
		GL11.glPushMatrix();
		GL11.glScalef(0.99F, 0.99F, 0.99F);
		GL11.glTranslatef(-0.51F, trans1 - 0.5F, -0.5F);
		theMagnet.render(PIXEL);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(0.99F, 0.99F, 0.99F);
		GL11.glTranslatef(-0.51F, trans2 - 0.5F, 12F * PIXEL - 0.5F);
		theMagnet.render(PIXEL);
		GL11.glPopMatrix();
		if (refinery != null) {
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			GL11.glScalef(0.5F, 1, 0.5F);
			if (liquid1 != null && liquid1.amount > 0) {
				int[] list1 = FluidRenderer.getFluidDisplayLists(liquid1, refinery.getWorldObj(), false);
				if (list1 != null) {
					bindTexture(FluidRenderer.getFluidSheet(liquid1));
					RenderUtils.setGLColorFromInt(color1);
					GL11.glCallList(list1[getDisplayListIndex(refinery.tanks[0])]);
				}
			}
			if (liquid2 != null && liquid2.amount > 0) {
				int[] list2 = FluidRenderer.getFluidDisplayLists(liquid2, refinery.getWorldObj(), false);
				if (list2 != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(0, 0, 1);
					bindTexture(FluidRenderer.getFluidSheet(liquid2));
					RenderUtils.setGLColorFromInt(color2);
					GL11.glCallList(list2[getDisplayListIndex(refinery.tanks[1])]);
					GL11.glPopMatrix();
				}
			}
			if (liquidResult != null && liquidResult.amount > 0) {
				int[] list3 = FluidRenderer.getFluidDisplayLists(liquidResult, refinery.getWorldObj(), false);
				if (list3 != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(1, 0, 0.5F);
					bindTexture(FluidRenderer.getFluidSheet(liquidResult));
					RenderUtils.setGLColorFromInt(colorResult);
					GL11.glCallList(list3[getDisplayListIndex(refinery.result)]);
					GL11.glPopMatrix();
				}
			}
			GL11.glPopAttrib();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private int getDisplayListIndex(Tank tank) {
		return Math.min((int) ((float) tank.getFluidAmount() / (float) tank.getCapacity() * (FluidRenderer.DISPLAY_STAGES - 1)), FluidRenderer.DISPLAY_STAGES - 1);
	}

}

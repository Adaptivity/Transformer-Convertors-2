package transformerconvertors2.client.renderer.blocks.dynamo;

import core.api.tileentity.IRotatable;
import core.client.renderer.tileentity.TileEntityRendererCoreBase;
import core.common.resources.CoreResources;
import core.helpers.GLHelper;
import core.helpers.RandomHelper;
import core.helpers.RotationHelper;
import core.helpers.TextureHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import transformerconvertors2.api.dynamoengine.IDynamoEngine;

/**
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class TileEntityRendererDynamoEngine extends TileEntityRendererCoreBase {

    protected final ModelBase model;
    protected final ModelRenderer box, trunk, movingBox, chamber;
    public static final float[] ANGLE_MAP = new float[] { RandomHelper.convertDoubleToFloat(Math.PI), 0.0F, RandomHelper.convertDoubleToFloat(-Math.PI / 2.0D), RandomHelper.convertDoubleToFloat(Math.PI / 2.0D), RandomHelper.convertDoubleToFloat(Math.PI / 2.0D), RandomHelper.convertDoubleToFloat(-Math.PI / 2.0D) };
    public static final float MODEL_FACTOR = 1.0F / 16.0F;
    public static final float MODEL_CHAMBER_FACTOR = 2.0F / 16.0F;
    private final boolean inventoryRender;

    public TileEntityRendererDynamoEngine() {
        this(false);
    }

	public TileEntityRendererDynamoEngine(boolean inventoryRender) {
		this.inventoryRender = inventoryRender;
		model = new ModelBase() {};
		box = new ModelRenderer(model, 0, 1);
		box.addBox(-8.0F, -8.0F, -8.0F, 16, 4, 16);
		box.rotationPointX = 8.0F;
		box.rotationPointY = 8.0F;
		box.rotationPointZ = 8.0F;
		trunk = new ModelRenderer(model, 1, 1);
		trunk.addBox(-4.0F, -4.0F, -4.0F, 8, 12, 8);
		trunk.rotationPointX = 8.0F;
		trunk.rotationPointY = 8.0F;
		trunk.rotationPointZ = 8.0F;
		movingBox = new ModelRenderer(model, 0, 1);
		movingBox.addBox(-8.0F, -4.0F, -8.0F, 16, 4, 16);
		movingBox.rotationPointX = 8.0F;
		movingBox.rotationPointY = 8.0F;
		movingBox.rotationPointZ = 8.0F;
		chamber = new ModelRenderer(model, 1, 1);
		chamber.addBox(-5.0F, -4.0F, -5.0F, 10, 2, 10);
		chamber.rotationPointX = 8.0F;
		chamber.rotationPointY = 8.0F;
		chamber.rotationPointZ = 8.0F;
	}

	@Override
	protected final void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
		if (inventoryRender || !(tile instanceof IRotatable) || !(tile instanceof IDynamoEngine)) {
			return;
		}
        IRotatable rotatable = (IRotatable)tile;
        IDynamoEngine engine = (IDynamoEngine)tile;
		renderDynamoEngine(rotatable.getDirection(), engine.getEngineProgress(), engine.doesUseResourceLocation(), engine.getDynamoChamberTexture(), engine.getDynamoTexture(), engine.getTrunkTexture(), xCoord, yCoord, zCoord);
	}

    public final void renderEngineInInventory(ForgeDirection direction, boolean doesUseResourceLocation, float progress, String dynamoEngineTexture, String trunkTexture, String chamberTexture) {
        renderDynamoEngine(direction, progress, doesUseResourceLocation, chamberTexture, dynamoEngineTexture, trunkTexture, -0.5D, -0.5D, -0.5D);
    }

    /**
     * Code taken from the original {@link buildcraft.energy.render.RenderEngine} class.
     */
    protected void renderDynamoEngine(ForgeDirection orientation, float progress, boolean doesUseResourceLocation, String chamberT, String dynamoEngineT, String trunkT, double xCoord, double yCoord, double zCoord) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GLHelper.glColour4f();
        GL11.glTranslated(xCoord, yCoord, zCoord);
        float step;
        if (progress > 0.5) {
            step = 7.99F - (progress - 0.5F) * 2F * 7.99F;
        } else {
            step = progress * 2F * 7.99F;
        }
        float translatefact = step / 16;
        float[] angle = new float[3];
        float[] translate = { orientation.offsetX, orientation.offsetY, orientation.offsetZ };
        switch(orientation) {
            case DOWN:
                angle[2] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
            case UP:
                angle[0] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
            case NORTH:
                angle[0] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
            case SOUTH:
                angle[0] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
            case WEST:
                angle[2] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
            case EAST:
                angle[2] = ANGLE_MAP[RotationHelper.convertForgeDirectionToInt(orientation)];
                break;
        }
        box.rotateAngleX = angle[0];
        box.rotateAngleY = angle[1];
        box.rotateAngleZ = angle[2];
        trunk.rotateAngleX = angle[0];
        trunk.rotateAngleY = angle[1];
        trunk.rotateAngleZ = angle[2];
        movingBox.rotateAngleX = angle[0];
        movingBox.rotateAngleY = angle[1];
        movingBox.rotateAngleZ = angle[2];
        chamber.rotateAngleX = angle[0];
        chamber.rotateAngleY = angle[1];
        chamber.rotateAngleZ = angle[2];
        if (doesUseResourceLocation) {
            CoreResources.getTextureManager().bindTexture(new ResourceLocation(dynamoEngineT));
        } else {
            TextureHelper.bindTexture(dynamoEngineT);
        }
        box.render(TileEntityRendererDynamoEngine.MODEL_FACTOR);
        GL11.glTranslatef(translate[0] * translatefact, translate[1] * translatefact, translate[2] * translatefact);
        movingBox.render(TileEntityRendererDynamoEngine.MODEL_FACTOR);
        GL11.glTranslatef(-translate[0] * translatefact, -translate[1] * translatefact, -translate[2] * translatefact);
        if (doesUseResourceLocation) {
            CoreResources.getTextureManager().bindTexture(new ResourceLocation(chamberT));
        } else {
            TextureHelper.bindTexture(chamberT);
        }
        for (int i = 0; i <= step + 2; i += 2) {
            chamber.render(TileEntityRendererDynamoEngine.MODEL_FACTOR);
            GL11.glTranslatef(translate[0] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR, translate[1] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR, translate[2] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR);
        }
        for (int i = 0; i <= step + 2; i += 2) {
            GL11.glTranslatef(-translate[0] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR, -translate[1] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR, -translate[2] * TileEntityRendererDynamoEngine.MODEL_CHAMBER_FACTOR);
        }
        if (doesUseResourceLocation) {
            CoreResources.getTextureManager().bindTexture(new ResourceLocation(trunkT));
        } else {
            TextureHelper.bindTexture(trunkT);
        }
        trunk.render(TileEntityRendererDynamoEngine.MODEL_FACTOR);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}

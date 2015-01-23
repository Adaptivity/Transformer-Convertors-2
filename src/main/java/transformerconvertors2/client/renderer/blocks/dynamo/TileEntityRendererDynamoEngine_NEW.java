package transformerconvertors2.client.renderer.blocks.dynamo;

import core.client.renderer.tileentity.TileEntityRendererCoreBase;
import core.common.resources.CoreResources;
import core.helpers.GLHelper;
import core.helpers.TextureHelper;
import core.helpers.TextureHelper.TextureUsageInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;

/**
 * Created by Master801 on 11/25/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class TileEntityRendererDynamoEngine_NEW extends TileEntityRendererCoreBase {

    public IModelCustom engineModel = AdvancedModelLoader.loadModel(new ResourceLocation(ConvertorResources.TC2_MOD_ID, "models/dynamo_engine/Engine.obj"));

    @Override
    protected final void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            TileEntityDynamoEngineConvertorBase dynamoEngine = (TileEntityDynamoEngineConvertorBase)tile;
            GL11.glPushMatrix();
            GLHelper.glColour4f();
            GL11.glTranslated(xCoord, yCoord, zCoord);
            TextureUsageInfo engineInfo = new TextureUsageInfo(dynamoEngine.doesUseResourceLocation(), dynamoEngine.getDynamoTexture());
            TextureUsageInfo trunkInfo = new TextureUsageInfo(dynamoEngine.doesUseResourceLocation(), dynamoEngine.getTrunkTexture());
            TextureUsageInfo chamberInfo = new TextureUsageInfo(dynamoEngine.doesUseResourceLocation(), dynamoEngine.getDynamoChamberTexture());
            TextureUsageInfo gearInfo = new TextureUsageInfo(dynamoEngine.doesUseResourceLocation(), dynamoEngine.getDynamoGearTexture());
            renderEngine(dynamoEngine.getDirection(), dynamoEngine.getEngineProgress(), engineInfo, trunkInfo, chamberInfo, gearInfo);
            GL11.glPopMatrix();
        }
    }

    public void renderEngine(ForgeDirection direction, float progress, TextureUsageInfo engineInfo, TextureUsageInfo trunkInfo, TextureUsageInfo chamberInfo, TextureUsageInfo gearInfo) {
        float angle = 0.0F;
        float changeInX = 1.0F;
        float changeInY = 1.0F;
        float changeInZ = 1.0F;
        switch(direction) {
            case UP:
                angle = 0.0F;
                //The model's default facing is up. So there's no need to set the angle to something else.
                //Fun-fact, 0° is the same thing as 360°.
                break;
            case NORTH:
                angle = 180.0F;
                changeInX -= 1.0F;
                changeInZ -= 1.0F;
                break;
            case EAST:
                angle = -90.0F;
                changeInX-= 1.0F;
                changeInY -= 1.0F;
                break;
            case WEST:
                angle = 90.0F;
                changeInX -= 1.0F;
                changeInY -= 1.0F;
                break;
            case SOUTH:
                angle = -90.0F;
                changeInY -= 1.0F;
                changeInZ -= 1.0F;
                break;
            case DOWN:
                angle = 180.0F;
                changeInY -= 1.0F;
                changeInZ -= 1.0F;
                break;
        }
        GL11.glRotatef(angle, changeInX, changeInY, changeInZ);//Rotates the model in the direction it's currently in.
        GL11.glPushMatrix();
        renderBase(engineInfo);
        renderTrunk(trunkInfo);
        renderPump(chamberInfo, progress);
        renderGear(gearInfo, progress);
        GL11.glPopMatrix();
    }

    protected void renderBase(TextureUsageInfo info) {
        if (info.doesUseResourceLocation()) {
            CoreResources.getTextureManager().bindTexture(info.getResourceLocation());
        } else {
            TextureHelper.bindTexture(info.getTexturePath());
        }
        engineModel.renderPart("Base");
    }

    protected void renderTrunk(TextureUsageInfo info) {
        if (info.doesUseResourceLocation()) {
            CoreResources.getTextureManager().bindTexture(info.getResourceLocation());
        } else {
            TextureHelper.bindTexture(info.getTexturePath());
        }
        engineModel.renderPart("Trunk");
    }

    protected void renderPump(TextureUsageInfo info, float progress) {
        if (info.doesUseResourceLocation()) {
            CoreResources.getTextureManager().bindTexture(info.getResourceLocation());
        } else {
            TextureHelper.bindTexture(info.getTexturePath());
        }
        GL11.glTranslatef(0.0F, 0.0F, 0.0F);//TODO Not idea how to translate this model.
        engineModel.renderPart("Pump");
    }

    protected void renderGear(TextureUsageInfo info, float progress) {
        if (info.doesUseResourceLocation()) {
            CoreResources.getTextureManager().bindTexture(info.getResourceLocation());
        } else {
            TextureHelper.bindTexture(info.getTexturePath());
        }
        GL11.glRotatef(progress, 1.0F, 0.0F, 0.0F);//TODO Progress should NOT be the angle this model should rotate at.
        engineModel.renderPart("Gear");
    }

}

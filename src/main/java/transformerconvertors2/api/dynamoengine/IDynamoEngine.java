package transformerconvertors2.api.dynamoengine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Master801
 */
public interface IDynamoEngine {

    @SideOnly(Side.CLIENT)
    String getDynamoGearTexture();

    @SideOnly(Side.CLIENT)
	String getDynamoTexture();

    @SideOnly(Side.CLIENT)
	String getTrunkTexture();

    @SideOnly(Side.CLIENT)
	String getDynamoChamberTexture();

    @SideOnly(Side.CLIENT)
	boolean doesUseResourceLocation();

    float getEngineProgress();

    float getHeat();

    float getMaxHeat();

    void addHeat(float additionalHeat);

    void setHeat(float heat);

}

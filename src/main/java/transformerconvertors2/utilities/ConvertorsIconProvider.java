package transformerconvertors2.utilities;

import buildcraft.api.core.IIconProvider;
import core.helpers.SpriteHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

@SideOnly(Side.CLIENT)
@Optional.Interface(modid = "BuildCraft|Core", iface = "buildcraft.api.core.IIconProvider")
public final class ConvertorsIconProvider implements IIconProvider {

	private static final IIcon[] ICONS = new IIcon[2];
    public static final String CUSTOM_PIPE_TEXTURE_LOCATION = "/custom_resources/textures/pipes/";
    public static Object INSTANCE = new ConvertorsIconProvider();

	@Override
    @Optional.Method(modid = "BuildCraft|Core")
	public IIcon getIcon(int iconIndex) {
		switch(iconIndex) {
            case 0:
                return ConvertorsIconProvider.ICONS[0];
		    case 1:
			    return ConvertorsIconProvider.ICONS[1];
		    default:
                return null;
		}
	}

	@Override
    @Optional.Method(modid = "BuildCraft|Core")
    public void registerIcons(IIconRegister register) {
        ConvertorsIconProvider.ICONS[0] = SpriteHelper.createNewCustomSpriteAtlas(ConvertorsIconProvider.CUSTOM_PIPE_TEXTURE_LOCATION + "heavy_duty/Heavy_Duty_Redstone_Conductive_Pipe.png", "heavy_duty_redstone_conductive_pipe", register);
        ConvertorsIconProvider.ICONS[1] = SpriteHelper.createNewCustomSpriteAtlas(ConvertorsIconProvider.CUSTOM_PIPE_TEXTURE_LOCATION + "heavy_duty/Heavy_Duty_Redstone_Conductive_Receiver_Pipe.png", "heavy_duty_redstone_conductive_receiver_pipe", register);
	}

}

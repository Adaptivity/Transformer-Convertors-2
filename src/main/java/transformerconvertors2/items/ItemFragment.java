package transformerconvertors2.items;

import core.item.ItemCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import transformerconvertors2.common.resources.ConvertorResources;

/**
 * Created by Master801 on 11/30/2014 at 4:08 PM.
 * @author Master801
 */
public class ItemFragment extends ItemCoreBase {

    public static final String[] FRAGMENT_NAMES = new String[] { "nickel", "platinum", "mithril" };
    public static final IIcon[] FRAGMENT_ICONS = new IIcon[ItemFragment.FRAGMENT_NAMES.length];

    public ItemFragment() {
        super(true);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
    }

    @Override
    public String getUnlocalizedName(int metadata) {
        return "fragment." + ItemFragment.FRAGMENT_NAMES[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void registerIcons(TextureMap itemMap) {
        for(int i = 0; i < ItemFragment.FRAGMENT_NAMES.length; i++) {
            ItemFragment.FRAGMENT_ICONS[i] = itemMap.registerIcon(new ResourceLocation(ConvertorResources.TC2_MODID, "fragments/" + ItemFragment.FRAGMENT_NAMES[i]).toString());
        }
    }

}

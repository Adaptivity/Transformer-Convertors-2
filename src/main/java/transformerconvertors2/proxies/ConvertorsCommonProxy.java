package transformerconvertors2.proxies;

import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import cpw.mods.fml.relauncher.Side;
import transformerconvertors2.TransformerConvertors2;

public class ConvertorsCommonProxy implements IProxy {

    @Override
    public void registerBlockRenderers() {
    }

    @Override
    public void registerTileEntityRenderers() {
    }

    @Override
    public void registerItemRenderers() {
    }

    @Override
    public void addRenderID(String id, int renderID) {
    }

    @Override
    public int getRenderIDFromSpecialID(String id) {
        return 0;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    @Override
    public IMod getOwningMod() {
        return TransformerConvertors2.instance;
    }

}

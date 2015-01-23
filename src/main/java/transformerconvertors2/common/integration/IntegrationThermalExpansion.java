package transformerconvertors2.common.integration;

import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.item.ItemCoreBase;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 1/15/2015 at 7:11 AM.
 * @author Master801
 */
public final class IntegrationThermalExpansion implements IModIntegrationHandler {

    public static final ItemCoreBase AUGMENT_WIRELESS = null;

    @Override
    public void addModBlocksAndItems() {
    }

    @Override
    public void addModRecipes() {
    }

    @Override
    public IMod getParentMod() {
        return TransformerConvertors2.instance;
    }

    @Override
    public String getModIDToIntegrateWith() {
        return "ThermalExpansion";//TODO Not sure if this is the correct mod id.
    }

}

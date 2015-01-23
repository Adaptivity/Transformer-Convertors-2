package transformerconvertors2.tileentity.misc;

import buildcraft.factory.TileRefinery;
import buildcraft.factory.gui.ContainerRefinery;
import buildcraft.factory.gui.GuiRefinery;
import core.api.client.gui.IGuiHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * Empty casing.
 * @author Master801
 */
public final class TileEntityReinforcedRefinery extends TileRefinery implements IGuiHelper {

    @Override
    public Container getServerGui(InventoryPlayer inventory) {
        return new ContainerRefinery(inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer getClientGui(InventoryPlayer inventory) {
        return new GuiRefinery(inventory, this);
    }

    /**
     * @return true if it is an item, else return false if it is not.
     */
    @Override
    public boolean isItem() {
        return false;
    }

    /**
     * To check if the tile-entity/item does in-fact, use a gui.
     */
    @Override
    public boolean doesHaveGui() {
        return true;
    }

}

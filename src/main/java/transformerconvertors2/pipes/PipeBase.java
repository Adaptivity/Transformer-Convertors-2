package transformerconvertors2.pipes;

import buildcraft.api.core.IIconProvider;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.transport.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.utilities.ConvertorsIconProvider;

/**
 * @author Master801
 */
public abstract class PipeBase extends Pipe<PipeTransport> {

    protected PipeBase(PipeTransport transport, Item item) {
        super(transport, item);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
        return (IIconProvider)ConvertorsIconProvider.INSTANCE;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    @Override
    public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
        if (tile instanceof TileGenericPipe) {
            Pipe pipe = ((TileGenericPipe)tile).pipe;
            if (canOnlyReceivePower()) {
                if (pipe instanceof IPipeTransportPowerHook) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean canOnlyReceivePower() {
        return false;
    }

    protected final PipeTransport getTransport() {
        return transport;
    }

    public static void registerPipeStorageValue(Class<? extends PipeBase> pipe, int pipeStorage) {
        PipeTransportPower.powerCapacities.put(pipe, pipeStorage);
    }

    public static Item initPipe(Class<? extends PipeBase> pipeClass, String unlocalizedName, CreativeTabs tab) {
        ItemPipe pipeItem = BlockGenericPipe.registerPipe(pipeClass, CreativeTabBuildCraft.PIPES);
        pipeItem.setUnlocalizedName(unlocalizedName);
        if (tab != null) {
            pipeItem.setCreativeTab(tab);
        } else {
            pipeItem.setCreativeTab(CreativeTabBuildCraft.PIPES.get());
        }
        return pipeItem;
    }

}

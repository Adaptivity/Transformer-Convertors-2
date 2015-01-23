package transformerconvertors2.blocks.misc;

import buildcraft.api.tools.IToolWrench;
import buildcraft.core.fluids.TankUtils;
import buildcraft.core.utils.Utils;
import core.api.block.IRender;
import core.api.common.mod.IMod;
import core.block.BlockCoreBase;
import core.helpers.GuiHelper;
import core.helpers.PlayerHelper;
import core.helpers.WorldHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedRefinery;

/**
 * I'm not claiming this as mine.
 * @author Master801
 */
public class BlockReinforcedRefinery extends BlockCoreBase implements IRender {

	public BlockReinforcedRefinery() {
		super(Material.iron, false);
		setResistance(150.0F);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcon(TextureMap map) {
    }

    @Override
	public String getAdjustedUnlocalizedName() {
		return "reinforcedRefinery";
	}

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityReinforcedRefinery();
    }

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);
		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		switch (ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z))) {
		case WEST:
			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.SOUTH.ordinal(), 3);
			break;
		case EAST:
			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.NORTH.ordinal(), 3);
			break;
		case NORTH:
			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.WEST.ordinal(), 3);
			break;
		default:
			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.EAST.ordinal(), 3);
			break;
		}
		world.markBlockForUpdate(x, y, z);
		return true;
	}

	@Override
	protected boolean isCustomBlock() {
		return true;
	}

    @Override
    public boolean onBlockActivated(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityReinforcedRefinery) {
            TileEntityReinforcedRefinery refinery = (TileEntityReinforcedRefinery)tile;
            ItemStack current = player.getCurrentEquippedItem();
            if (current == null) {
                return false;
            }
            Item equipped = current.getItem();
            if (player.isSneaking() && equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(player, xCoord, yCoord, zCoord)) {
                refinery.resetFilters();
                ((IToolWrench) equipped).wrenchUsed(player, xCoord, yCoord, zCoord);
                return true;
            }
            if (current.getItem() != Items.bucket) {
                if (WorldHelper.isServer(world)) {
                    if (TankUtils.handleRightClick(refinery, ForgeDirection.getOrientation(side), player, true, false)) {
                        return true;
                    }
                } else if (FluidContainerRegistry.isContainer(current)) {
                    return true;
                }
            }
            if (PlayerHelper.isPlayerNotSneaking(player)) {
                return GuiHelper.openGui(TransformerConvertors2.instance, player, refinery);
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.Reinforced_Refinery";
    }

    @Override
    public IMod getMod() {
        return TransformerConvertors2.instance;
    }

}

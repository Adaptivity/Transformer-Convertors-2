package transformerconvertors2.blocks.misc;

import buildcraft.BuildCraftCore;
import buildcraft.core.inventory.InvUtils;
import core.block.BlockCoreBase;
import core.helpers.SpriteHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.tileentity.misc.TileEntityReinforcedTank;

/**
 * I'm not claiming this as my own.
 * @author Master801
 */
public class BlockReinforcedTank extends BlockCoreBase {

    private static final IIcon[] REINFORCED_TANK_ICONS = new IIcon[4];

	public BlockReinforcedTank() {
		super(Material.glass, false);
		setResistance(150.0F);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 1F, 0.875F);
		setHardness(0.5F);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
	}

    @Override
    @SideOnly(Side.CLIENT)
    protected void registerIcon(TextureMap map) {
        final String spriteSheetPath = "/custom_resources/textures/blocks/Blocks_TC2_0.png";
        BlockReinforcedTank.REINFORCED_TANK_ICONS[0] = SpriteHelper.createNewSpriteSheetAtlas(spriteSheetPath, "reinforced_tank_top", map, 0, 0);
        BlockReinforcedTank.REINFORCED_TANK_ICONS[1] = SpriteHelper.createNewSpriteSheetAtlas(spriteSheetPath, "reinforced_tank_bottom", map, 0, 0);
        BlockReinforcedTank.REINFORCED_TANK_ICONS[2] = SpriteHelper.createNewSpriteSheetAtlas(spriteSheetPath, "reinforced_tank_side", map, 2, 0);
        BlockReinforcedTank.REINFORCED_TANK_ICONS[3] = SpriteHelper.createNewSpriteSheetAtlas(spriteSheetPath, "reinforced_tank_side_stacked", map, 1, 0);
    }

    @Override
	public String getAdjustedUnlocalizedName() {
		return "reinforcedTank";
	}

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityReinforcedTank();
    }

    @Override
	public boolean shouldSideBeRendered(IBlockAccess world, int xCoord, int yCoord, int zCoord, int side) {
        return true;
	}

	@Override
	public int getLightValue(IBlockAccess world, int xCoord, int yCoord, int zCoord) {
		TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
		if (tile instanceof TileEntityReinforcedTank) {
			return ((TileEntityReinforcedTank)tile).getFluidLightLevel();
		}
		return super.getLightValue(world, xCoord, yCoord, zCoord);
	}

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getIcon(ForgeDirection side, int metadata) {
        switch(side) {
            case UP:
                return BlockReinforcedTank.REINFORCED_TANK_ICONS[0];
            case DOWN:
                return BlockReinforcedTank.REINFORCED_TANK_ICONS[1];
        }
        return BlockReinforcedTank.REINFORCED_TANK_ICONS[2];
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getBlockTexture(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        switch(side) {
            case DOWN:
                return BlockReinforcedTank.REINFORCED_TANK_ICONS[1];
            case UP:
                return BlockReinforcedTank.REINFORCED_TANK_ICONS[0];
            default:
                if (world.getBlock(xCoord, yCoord - 1, zCoord) == this) {
                    return BlockReinforcedTank.REINFORCED_TANK_ICONS[3];
                }
                return BlockReinforcedTank.REINFORCED_TANK_ICONS[1];
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityReinforcedTank) {
            TileEntityReinforcedTank tank = (TileEntityReinforcedTank)tile;
            ItemStack current = player.inventory.getCurrentItem();
            if (current != null) {
                FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
                if (liquid != null) {
                    int qty = tank.fill(ForgeDirection.UNKNOWN, liquid, true);
                    if (qty != 0 && !BuildCraftCore.debugWorldgen && !player.capabilities.isCreativeMode) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, InvUtils.consumeItem(current));
                    }
                    return true;
                } else {
                    FluidStack available = tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
                    if (available != null) {
                        ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);
                        liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
                        if (liquid != null) {
                            if (!BuildCraftCore.debugWorldgen && !player.capabilities.isCreativeMode) {
                                if (current.stackSize > 1) {
                                    if (!player.inventory.addItemStackToInventory(filled)) {
                                        return false;
                                    } else {
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, InvUtils.consumeItem(current));
                                    }
                                } else {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, InvUtils.consumeItem(current));
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
                                }
                            }
                            tank.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}

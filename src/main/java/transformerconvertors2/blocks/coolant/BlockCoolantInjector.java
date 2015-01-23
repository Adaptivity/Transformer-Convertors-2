package transformerconvertors2.blocks.coolant;

import core.api.tileentity.coolant.ICoolantInjector;
import core.api.tileentity.coolant.ICoolantInjector.InjectorTypes;
import core.api.tileentity.coolant.ICoolantIntake;
import core.block.BlockCoreBase;
import core.helpers.TileEntityHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.tileentity.coolant.TileEntityCoolantInjector;

/**
 * Created by Master801 on 11/4/2014.
 * @author Master801
 */
public class BlockCoolantInjector extends BlockCoreBase {

    public BlockCoolantInjector() {
        super(Material.iron, true);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcon(TextureMap map) {
    }

    @Override
    public String getAdjustedUnlocalizedName() {
        return null;
    }

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityCoolantInjector(InjectorTypes.values()[metadata]);
    }

    @Override
    public void onNeighborBlockChange(World world, int xCoord, int yCoord, int zCoord, Block block) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            Block gotBlock = TileEntityHelper.getBlockInDirection(world, new ChunkCoordinates(xCoord, yCoord, zCoord), direction);
            if (gotBlock == block) {
                if (block instanceof ITileEntityProvider) {
                    ITileEntityProvider provider = (ITileEntityProvider)block;
                    TileEntity blockTile = provider.createNewTileEntity(world, world.getBlockMetadata(xCoord, yCoord, zCoord));
                    if (tile instanceof ICoolantInjector && blockTile instanceof ICoolantIntake) {
                        ICoolantInjector coolantInjector = (ICoolantInjector)tile;
                        ICoolantIntake coolantIntake = (ICoolantIntake)blockTile;
                        coolantInjector.setIntakeFromSide(coolantIntake, direction);
                    }
                }
            }
        }
    }

    @Override
    protected int getAmountOfSubtypes() {
        return InjectorTypes.values().length;
    }

}

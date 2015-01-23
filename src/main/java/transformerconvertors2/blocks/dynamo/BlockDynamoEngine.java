package transformerconvertors2.blocks.dynamo;

import buildcraft.core.ICustomHighlight;
import core.api.block.IRender;
import core.api.common.mod.IMod;
import core.block.BlockCoreBase;
import core.helpers.RandomHelper;
import core.helpers.ReflectionHelper;
import core.helpers.WrapperHelper.WrappingData;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.TransformerConvertors2;
import transformerconvertors2.common.resources.ConvertorResources;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;

import java.util.List;
import java.util.Random;

@Optional.Interface(modid = "BuildCraft|Core", iface = "buildcraft.core.ICustomHighlight")
public abstract class BlockDynamoEngine extends BlockCoreBase implements ICustomHighlight, IRender {

    /**
     * Credits to the BuildCraft team for this.
     * Well, for the actual boxes code. I just use reflection to get it.
     */
    public static AxisAlignedBB[][] boxes = null;

    /**
     * DoesUseResourceLocation, Dynamo Engine Base Texture, Trunk Texture, and Chamber Texture.
     * @param metadata The metadata you want to render at.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public abstract WrappingData getWrappingData(int metadata);

    public BlockDynamoEngine(Material material, boolean doesHaveSubtypes) {
        super(material, doesHaveSubtypes);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
    }

    @Override
    protected boolean isCustomBlock() {
        return true;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            return side == ((TileEntityDynamoEngineConvertorBase)tile).getDirection().getOpposite();
        }
        return super.isSideSolid(world, xCoord, yCoord, zCoord, side);
    }

    /**
     * Stolen from the original engine code.
     * Credits to the BuildCraft team for it.
     */
    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    public AxisAlignedBB[] getBoxes(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            return BlockDynamoEngine.boxes[((TileEntityDynamoEngineConvertorBase)tile).getRotation()];

        }
        return new AxisAlignedBB[] { AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) };
    }

    /**
     * Stolen from the original engine code.
     * Credits to the BuildCraft team for it.
     */
    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    public double getExpansion() {
        return 0.0075;
    }

    /**
     * Stolen from the original engine code.
     * Credits to the BuildCraft team for it.
     */
    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    protected void addCollisionBoxesToList(World world, int xCoord, int yCoord, int zCoord, AxisAlignedBB axis, List<AxisAlignedBB> list, EntityLivingBase entity) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            AxisAlignedBB[] aabbs = BlockDynamoEngine.boxes[((TileEntityDynamoEngineConvertorBase)tile).getRotation()];
            for (AxisAlignedBB aabb : aabbs) {
                AxisAlignedBB aabbTmp = aabb.getOffsetBoundingBox(xCoord, yCoord, zCoord);
                if (axis.intersectsWith(aabbTmp)) {
                    list.add(aabbTmp);
                }
            }
            return;
        }
        super.addCollisionBoxesToList(world, xCoord, yCoord, zCoord, axis, list, entity);
    }

    /**
     * Stolen from the original engine code.
     * Credits to the BuildCraft team for it.
     */
    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    public MovingObjectPosition collisionRayTrace(World world, int xCoord, int yCoord, int zCoord, Vec3 vec, Vec3 direction) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            AxisAlignedBB[] aabbs = BlockDynamoEngine.boxes[((TileEntityDynamoEngineConvertorBase)tile).getRotation()];
            MovingObjectPosition closest = null;
            for (AxisAlignedBB aabb : aabbs) {
                MovingObjectPosition mop = aabb.getOffsetBoundingBox(xCoord, yCoord, zCoord).calculateIntercept(vec, direction);
                if (mop != null) {
                    if (closest != null && mop.hitVec.distanceTo(vec) < closest.hitVec.distanceTo(vec)) {
                        closest = mop;
                    } else {
                        closest = mop;
                    }
                }
            }
            if (closest != null) {
                closest.blockX = xCoord;
                closest.blockY = yCoord;
                closest.blockZ = zCoord;
            }
            return closest;
        }
        return super.collisionRayTrace(world, xCoord, yCoord, zCoord, vec, direction);
    }

    @Override
    public void onPostBlockPlaced(World world, int xCoord, int yCoord, int zCoord, int metadata) {
        super.onPostBlockPlaced(world, xCoord, yCoord, zCoord, metadata);
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            ((TileEntityDynamoEngineConvertorBase)tile).checkAndSetOrientation();
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int xCoord, int yCoord, int zCoord, int otherX, int otherY, int otherZ) {
        super.onNeighborChange(world, xCoord, yCoord, zCoord, otherX, otherY, otherZ);
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            ((TileEntityDynamoEngineConvertorBase)tile).checkAndSetOrientation();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int xCoord, int yCoord, int zCoord, Random random) {
        TileEntity tile = (TileEntity)world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof TileEntityDynamoEngineConvertorBase) {
            TileEntityDynamoEngineConvertorBase dynamoEngine = (TileEntityDynamoEngineConvertorBase)tile;
            if (!dynamoEngine.isAllowedToSendPower()) {
                return;
            }
            float f = RandomHelper.convertIntegerToFloat(xCoord) + 0.5F;
            float f1 = RandomHelper.convertIntegerToFloat(yCoord) + 0.5F;
            float f2 = RandomHelper.convertIntegerToFloat(zCoord) + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
            world.spawnParticle("reddust", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("reddust", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("reddust", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("reddust", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean doesUseWrappingData() {
        return true;
    }

    /**
     * This is set in the ItemBlock.
     */
    @Override
    public String getAdjustedUnlocalizedName() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcon(TextureMap map) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getSpecialRenderID() {
        return "TC2|Renderer.Block.DynamoEngine";
    }

    @Override
    public IMod getMod() {
        return TransformerConvertors2.instance;
    }

    static {
        if (Loader.isModLoaded("BuildCraft|Energy") && BlockDynamoEngine.boxes == null) {
            BlockDynamoEngine.boxes = ReflectionHelper.getFieldValue(ReflectionHelper.getField("buildcraft.energy.BlockEngine", "boxes"), null);
        }
    }

}

package transformerconvertors2.tileentity.dynamoengine.special;

import core.helpers.ExternalModsHelper;
import core.helpers.RandomHelper;
import core.helpers.TileEntityHelper;
import core.utilities.Coordinates;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deatrathias.cogs.machine.ISteamConsumer;
import flaxbeard.steamcraft.api.ISteamTransporter;
import flaxbeard.steamcraft.api.steamnet.SteamNetwork;
import flaxbeard.steamcraft.api.util.Coord4;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.tileentity.dynamoengine.TileEntityDynamoEngineConvertorBase;

import java.util.HashSet;

/**
 * Created by Master801 on 1/22/2015 at 7:13 PM.
 * @author Master801
 */
@Optional.InterfaceList({@Optional.Interface(modid = "Steamcraft", iface = "flaxbeard.steamcraft.api.ISteamTransporter"), @Optional.Interface(modid = "Cogs", iface = "deatrathias.cogs.machine")})
public final class TileEntityTurbineEngine extends TileEntityDynamoEngineConvertorBase implements ISteamTransporter, ISteamConsumer {

    private byte tier = RandomHelper.convertIntegerToByte(0);
    private int steam = 0, pressure = 0, steamCapacity = 0;
    private Object network = null;

    public TileEntityTurbineEngine(int rfCapacity, int maxHeat, byte tier) {
        super(rfCapacity, maxHeat);
        this.tier = tier;
        steamCapacity = 5000 * RandomHelper.convertByteToInteger(tier);
    }

    @Override
    public boolean isAllowedToSendPower() {
        return isRedstonePowered();
    }

    @Override
    protected void updateDynamoEngine() {
        //TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDynamoTexture() {
        return null;//TODO
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesUseResourceLocation() {
        return false;
    }

    @Override
    @Optional.Method(modid =  "Cogs")
    public float addSteam(float steam) {
        this.steam += steam;
        resolveSteamErrors();
        return this.steam;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tier = nbt.getByte("tier");
        steam = nbt.getInteger("steam");
        pressure = nbt.getInteger("pressure");
        steamCapacity = nbt.getInteger("steamCapacity");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("tier", tier);
        nbt.setInteger("steam", steam);
        nbt.setInteger("pressure", pressure);
        nbt.setInteger("steamCapacity", steamCapacity);
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public float getPressure() {
        return pressure;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public float getPressureResistance() {
        return 0.0F;//Not resistant to steam pressure at all.
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public boolean canInsert(ForgeDirection face) {
        return !face.equals(getDirection()) && steam < steamCapacity;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public int getCapacity() {
        return steamCapacity;
    }


    @Override
    @Optional.Method(modid = "Steamcraft")
    public int getSteamShare() {
        return 0;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void explode() {
        float explosion = 1.5F;
        if (tier > 0) {
            explosion *= RandomHelper.convertByteToInteger(tier) + 1;
        } else {
            explosion *= 1.7552F;
        }
        worldObj.newExplosion(null, xCoord, yCoord, zCoord, explosion, true, true);
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void insertSteam(int amount, ForgeDirection face) {
        if (face == getDirection()) {
            return;
        }
        steam += amount;
        resolveSteamErrors();
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void decrSteam(int i) {
        steam -= i;
        resolveSteamErrors();
    }

    protected void resolveSteamErrors() {
        if (steam < steamCapacity) {
            steam = 0;
        }
        if (steam > steamCapacity) {
            steam = steamCapacity;
        }
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public boolean doesConnect(ForgeDirection face) {
        return getDirection() != face;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public boolean acceptsGauge(ForgeDirection face) {
        return getDirection() != face;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public HashSet<ForgeDirection> getConnectionSides() {
        return null;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public World getWorld() {
        return getWorldObj();
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public String getNetworkName() {
        return null;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void setNetworkName(String name) {
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public SteamNetwork getNetwork() {
        return (SteamNetwork)network;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void setNetwork(SteamNetwork network) {
        this.network = network;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void refresh() {
        TileEntityHelper.markBlockForUpdate(this);
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public Coord4 getCoords() {
        return ExternalModsHelper.createNewCoord4(Coordinates.createNewCoordinates(this), RandomHelper.convertIntegerToByte(0));
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public int getDimension() {
        return getWorldObj().provider.dimensionId;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public int getSteam() {
        return steam;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void updateSteam(int steam) {
        insertSteam(steam, ForgeDirection.UP);
        resolveSteamErrors();
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public String getName() {
        return null;
    }

    @Override
    @Optional.Method(modid = "Steamcraft")
    public void wasAdded() {
        TileEntityHelper.markBlockForUpdate(this);
    }

}

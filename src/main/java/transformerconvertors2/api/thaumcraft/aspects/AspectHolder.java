package transformerconvertors2.api.thaumcraft.aspects;

import core.common.resources.CoreEnums;
import core.helpers.LoggerHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.common.Loader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 11/20/2014.
 * <p>
 *     Used only to store aspects within something(?).
 * </p>
 * @author Master801
 */
public class AspectHolder {

    private Object aspect = null;

    /**
     * @param aspect The aspect this holder can store.
     */
    public AspectHolder(Object aspect) {
        this.aspect = aspect;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (!Loader.isModLoaded("Thaumcraft")) {
            return nbt;
        }
        if (((Aspect) aspect).getComponents() != null) {
            NBTTagCompound mainTag = new NBTTagCompound();
            for (int i = 0; i < ((Aspect) aspect).getComponents().length; i++) {
                AspectHolder.writeAspectToNBT(aspect, mainTag, i);
            }
            nbt.setTag("mainTag", mainTag);
            return nbt;
        }
        AspectHolder.writeAspectToNBT(aspect, nbt, 0);
        return nbt;
    }

    public AspectHolder readFromNBT(NBTTagCompound nbt) {
        if (!Loader.isModLoaded("Thaumcraft")) {
            return this;
        }
        if (((Aspect) aspect).getComponents() != null) {
            for (int i = 0; i < ((Aspect) aspect).getComponents().length; i++) {
                NBTTagCompound mainTag = (NBTTagCompound) nbt.getTag("mainTag");
                if (mainTag == null) {
                    LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, CoreEnums.LoggerEnum.ERROR, "Failed to read from the main tag! Class: '%s'", getClass().toString());
                    return null;
                }
                AspectHolder.readAspectFromNBT(aspect, nbt, i);
            }
            return this;
        }
        AspectHolder.readAspectFromNBT(aspect, nbt, 0);
        return this;
    }

    /**
     * @param aspectID This can be set to 0 if the aspect is not made with other aspects.
     */
    public static void writeAspectToNBT(Object aspect, NBTTagCompound nbt, int aspectID) {
        if (!Loader.isModLoaded("Thaumcraft")) {
            return;
        }
        NBTTagCompound aspectNBT = new NBTTagCompound();
        String name = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getTag"), aspect);
        int colour = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getColor"), aspect);
        String chatColour = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getChatcolor"), aspect);
        int blend = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getBlend"), aspect);
        ResourceLocation texture = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getImage"), aspect);

        aspectNBT.setString("name", name);
        aspectNBT.setInteger("colour", colour);
        aspectNBT.setString("chatColour", chatColour);
        aspectNBT.setInteger("blend", blend);
        aspectNBT.setString("texture", texture.toString());
        nbt.setTag("aspect_" + aspectID, aspectNBT);
    }

    /**
     * @param aspectID This can be set to 0 if the aspect is not made with other aspects.
     */
    public static void readAspectFromNBT(Object aspect, NBTTagCompound nbt, int aspectID) {
        if (!Loader.isModLoaded("Thaumcraft")) {
            return;
        }
        NBTTagCompound aspectNBT = (NBTTagCompound)nbt.getTag("aspect_" + aspectID);
        if (aspectNBT == null) {
            String tag = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "getTag"), aspect);
            LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, CoreEnums.LoggerEnum.ERROR, "Failed to read the aspect from its NBT Data! Aspect: '%s', ID: '%d'", tag, aspectID);
            return;
        }
        ReflectionHelper.invokeVoidMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "setTag"), aspect, aspectNBT.getString("name"));
        ReflectionHelper.setFieldValue(ReflectionHelper.getField(Aspect.class, "color"), aspect, aspectNBT.getInteger("colour"));
        ReflectionHelper.invokeVoidMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "setChatcolor"), aspect, aspectNBT.getString("chatColour"));
        ReflectionHelper.setFieldValue(ReflectionHelper.getField(Aspect.class, "image"), aspect, new ResourceLocation(aspectNBT.getString("texture")));
        ReflectionHelper.invokeVoidMethod(ReflectionHelper.getMethod("thaumcraft.api.aspects.Aspect", "setBlend"), aspect, aspectNBT.getString("blend"));
    }

}

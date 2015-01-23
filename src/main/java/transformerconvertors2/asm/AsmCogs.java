package transformerconvertors2.asm;

import core.asm.transformerbases.ClassTransformerCoreBase;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.helpers.TextureHelper;
import deatrathias.cogs.gears.Gear;
import deatrathias.cogs.gears.GearConstants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import transformerconvertors2.TransformerConvertors2;

/**
 * Created by Master801 on 1/17/2015 at 2:01 PM.
 * @author Master801
 */
public final class AsmCogs extends ClassTransformerCoreBase {

    @Override
    protected byte[] transformClass(String classToTransform, byte[] classData) {
        if (!classToTransform.equals("deatrathias.cogs.gears.TileEntityGearRenderer") || !classToTransform.equals("deatrathias.cogs.gears.ItemCogwheelRenderer")) {
            return classData;
        }
        final String className = classToTransform.substring(classToTransform.lastIndexOf('.'));
        LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, LoggerEnum.INFO, "Attempting to patch class %s...", className);
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classData);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classReader.accept(classNode, 0);
        classNode.accept(classWriter);
        MethodNode renderTileEntityAt = null;
        for(MethodNode method : classNode.methods) {
            if ((method.name.equals("aor") || method.name.equals("func_147500_a") || method.name.equals("renderTileEntityAt")) && (method.desc.equals("(Laor;DDDF)V") || method.desc.equals("(Lnet/minecraft/tileentity/TileEntity;DDDF)V"))) {
                renderTileEntityAt = method;//Once we found our method, we stop iterating over all of the methods.
                break;
            }
        }
        LoggerHelper.addAdvancedMessageToLogger(TransformerConvertors2.instance, LoggerEnum.INFO, "Successfully patched class %s!", className);
        return classWriter.toByteArray();
    }

    private static void bindGearTextures(Gear gear) {
        if (gear.getBaseType() != GearConstants.GearBaseType.NONE || gear.getBaseType() != GearConstants.GearBaseType.WOODEN || gear.getBaseType() != GearConstants.GearBaseType.IRON || gear.getBaseType() != GearConstants.GearBaseType.BRASS || gear.getBaseType() != GearConstants.GearBaseType.GOLD) {
            TextureHelper.bindTexture("/custom_resources/textures/models/misc/Custom_Gears.png");
        }
    }

}

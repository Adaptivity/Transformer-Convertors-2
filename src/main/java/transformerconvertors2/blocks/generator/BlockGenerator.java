package transformerconvertors2.blocks.generator;

import core.block.BlockCoreBase;
import net.minecraft.block.material.Material;

/**
 * Created by Master801 on 11/22/2014.
 * @author Master801
 */
public abstract class BlockGenerator extends BlockCoreBase {

    protected BlockGenerator(Material material, boolean doesHaveSubtypes) {
        super(material, doesHaveSubtypes);
    }

    /**
     * Do not remove this method.
     * This for setting the unlocalized name.
     * Which you actually can't set in the constructor. :\
     * This may seem redundant, but it's worth it.
     */
    @Override
    public String getAdjustedUnlocalizedName() {
        return null;
    }

}

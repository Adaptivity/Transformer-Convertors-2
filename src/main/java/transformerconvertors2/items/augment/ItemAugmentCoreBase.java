package transformerconvertors2.items.augment;

/**
 * Created by Master801 on 1/17/2015 at 3:38 PM.
 * @author Master801
 */

/*
@Optional.Interface(modid = "CoFHCore", iface = "cofh.api.item.IAugmentItem")
public abstract class ItemAugmentCoreBase extends ItemCoreBase implements IAugmentItem {

    @Optional.Method(modid = "CoFHCore")
    protected abstract AugmentLevels getAugmentLevel(ItemStack stack);

    public ItemAugmentCoreBase(boolean doesHaveSubtypes) {
        this(doesHaveSubtypes, false);
    }

    public ItemAugmentCoreBase(boolean doesHaveSubtypes, boolean full3d) {
        super(doesHaveSubtypes, full3d);
        setCreativeTab(ConvertorResources.TC2_CREATIVE_TAB);
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public final int getAugmentLevel(ItemStack stack, String type) {
        return RandomHelper.convertByteToInteger(getAugmentLevel(stack).getLevel());
    }

    @Override
    @Optional.Method(modid = "CoFHCore")
    public Set<String> getAugmentTypes(ItemStack stack) {
        return new HashSet<String>();
    }

    public static enum AugmentLevels {

        HARDENED(RandomHelper.convertIntegerToByte(0)),

        REINFORCED(RandomHelper.convertIntegerToByte(1)),

        RESONANT(RandomHelper.convertIntegerToByte(2));

        public static final AugmentLevels[] VALUES = new AugmentLevels[] { AugmentLevels.HARDENED, AugmentLevels.REINFORCED, AugmentLevels.RESONANT };

        final byte level;

        private AugmentLevels(byte level) {
            this.level = level;
        }

        public byte getLevel() {
            return level;
        }

    }

}
*/

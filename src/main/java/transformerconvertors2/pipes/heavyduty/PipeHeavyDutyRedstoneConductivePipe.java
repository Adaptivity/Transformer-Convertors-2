package transformerconvertors2.pipes.heavyduty;

import buildcraft.transport.PipeTransportPower;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.ForgeDirection;
import transformerconvertors2.pipes.PipeBase;

public class PipeHeavyDutyRedstoneConductivePipe extends PipeBase {

	public PipeHeavyDutyRedstoneConductivePipe(Item item) {
		super(new PipeTransportPower(), item);
        ((PipeTransportPower)getTransport()).initFromPipe(PipeHeavyDutyRedstoneConductivePipe.class);
	}

    /**
     * Should return the index in the array returned by GetTextureIcons() for a
     * specified direction
     *
     * @param direction - The direction for which the indexed should be
     *                  rendered. Unknown for pipe center
     * @return An index valid in the array returned by getTextureIcons()
     */
    @Override
    public int getIconIndex(ForgeDirection direction) {
        return 0;
    }
}

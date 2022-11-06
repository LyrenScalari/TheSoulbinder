package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class SoulBarrierIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("SoulBarrier");
    private static SoulBarrierIcon singleton;

    public SoulBarrierIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/SoulBarrier.png"));
    }

    public static SoulBarrierIcon get()
    {
        if (singleton == null) {
            singleton = new SoulBarrierIcon();
        }
        return singleton;
    }
}

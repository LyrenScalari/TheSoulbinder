package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class DarkIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Dark");
    private static DarkIcon singleton;

    public DarkIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Dark.png"));
    }

    public static DarkIcon get() {
        if (singleton == null) {
            singleton = new DarkIcon();
        }
        return singleton;
    }
}

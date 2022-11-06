package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class StormIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Storm");
    private static StormIcon singleton;

    public StormIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Storm.png"));
    }

    public static StormIcon get() {
        if (singleton == null) {
            singleton = new StormIcon();
        }
        return singleton;
    }
}
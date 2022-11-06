package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class FrostIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Frost");
    private static FrostIcon singleton;

    public FrostIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Ice.png"));
    }

    public static FrostIcon get()
    {
        if (singleton == null) {
            singleton = new FrostIcon();
        }
        return singleton;
    }
}
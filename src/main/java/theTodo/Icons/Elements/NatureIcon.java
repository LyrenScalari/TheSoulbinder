package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class NatureIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Nature");
    private static NatureIcon singleton;

    public NatureIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Earth.png"));
    }

    public static NatureIcon get() {
        if (singleton == null) {
            singleton = new NatureIcon();
        }
        return singleton;
    }
}

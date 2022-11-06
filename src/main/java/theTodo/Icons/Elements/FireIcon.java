package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class FireIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Fire");
    private static FireIcon singleton;

    public FireIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Fire.png"));
    }

    public static FireIcon get() {
        if (singleton == null) {
            singleton = new FireIcon();
        }
        return singleton;
    }
}
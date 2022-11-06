package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class RimeIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Rime");
    private static RimeIcon singleton;

    public RimeIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Chill.png"));
    }

    public static RimeIcon get() {
        if (singleton == null) {
            singleton = new RimeIcon();
        }
        return singleton;
    }
}
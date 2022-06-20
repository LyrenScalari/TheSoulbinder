package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class WeakIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Weak");
    private static WeakIcon singleton;

    public WeakIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Weak.png"));
    }

    public static WeakIcon get()
    {
        if (singleton == null) {
            singleton = new WeakIcon();
        }
        return singleton;
    }
}

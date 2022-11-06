package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class BloodcastIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Bloodcast");
    private static BloodcastIcon singleton;

    public BloodcastIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Bloodcast.png"));
    }

    public static BloodcastIcon get()
    {
        if (singleton == null) {
            singleton = new BloodcastIcon();
        }
        return singleton;
    }
}
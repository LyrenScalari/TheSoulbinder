package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class TempHPIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("TempHP");
    private static TempHPIcon singleton;

    public TempHPIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/tempHP.png"));
    }

    public static TempHPIcon get()
    {
        if (singleton == null) {
            singleton = new TempHPIcon();
        }
        return singleton;
    }
}
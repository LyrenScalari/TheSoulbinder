package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class FrostFeverIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("FrostFever");
    private static FrostFeverIcon singleton;

    public FrostFeverIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/FrostFever.png"));
    }

    public static FrostFeverIcon get() {
        if (singleton == null) {
            singleton = new FrostFeverIcon();
        }
        return singleton;
    }
}
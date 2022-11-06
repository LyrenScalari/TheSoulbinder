package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class ArcaneIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Arcane");
    private static ArcaneIcon singleton;

    public ArcaneIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Arcane.png"));
    }

    public static ArcaneIcon get() {
        if (singleton == null) {
            singleton = new ArcaneIcon();
        }
        return singleton;
    }
}
package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class VenomIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Venom");
    private static VenomIcon singleton;

    public VenomIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Venom.png"));
    }

    public static VenomIcon get()
    {
        if (singleton == null) {
            singleton = new VenomIcon();
        }
        return singleton;
    }
}
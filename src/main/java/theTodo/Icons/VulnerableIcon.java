package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class VulnerableIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Vulnerable");
    private static VulnerableIcon singleton;

    public VulnerableIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Vulnerable.png"));
    }

    public static VulnerableIcon get()
    {
        if (singleton == null) {
            singleton = new VulnerableIcon();
        }
        return singleton;
    }
}

package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class SyphonIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Syphon");
    private static SyphonIcon singleton;

    public SyphonIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Syphon.png"));
    }

    public static SyphonIcon get()
    {
        if (singleton == null) {
            singleton = new SyphonIcon();
        }
        return singleton;
    }
}

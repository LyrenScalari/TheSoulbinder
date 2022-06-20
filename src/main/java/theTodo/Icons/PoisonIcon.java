package theTodo.Icons;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class PoisonIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Poison");
    private static PoisonIcon singleton;

    public PoisonIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Poison.png"));
    }

    public static PoisonIcon get()
    {
        if (singleton == null) {
            singleton = new PoisonIcon();
        }
        return singleton;
    }
}
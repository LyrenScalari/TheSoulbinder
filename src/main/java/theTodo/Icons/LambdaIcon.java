package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class LambdaIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Lambda");
    private static LambdaIcon singleton;

    public LambdaIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/Upgrade_Lambda.png"));
    }

    public static LambdaIcon get()
    {
        if (singleton == null) {
            singleton = new LambdaIcon();
        }
        return singleton;
    }
}
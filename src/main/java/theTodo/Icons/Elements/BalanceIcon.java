package theTodo.Icons.Elements;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.Icons.FrostIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class BalanceIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Balance");
    private static BalanceIcon singleton;

    public BalanceIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Balance.png"));
    }

    public static BalanceIcon get() {
        if (singleton == null) {
            singleton = new BalanceIcon();
        }
        return singleton;
    }
}
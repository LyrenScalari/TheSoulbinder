package theTodo.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theTodo.SoulbinderMod;
import theTodo.util.TexLoader;

public class BlockIcon extends AbstractCustomIcon {
    public static final String ID = SoulbinderMod.makeID("Block");
    private static BlockIcon singleton;

    public BlockIcon() {
        super(ID, TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/block.png"));
    }

    public static BlockIcon get()
    {
        if (singleton == null) {
            singleton = new BlockIcon();
        }
        return singleton;
    }
}

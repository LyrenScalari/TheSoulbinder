package theTodo.Patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class SoulsField {
    public static SpireField<Integer> Souls = new SpireField(() -> {
        return 0;
    });

    public SoulsField() {
    }
}

package theTodo.Patches.SoulsBarrierPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "endBattle"
)
public class BattleEnd {
    public BattleEnd() {
    }

    public static void Prefix(AbstractRoom __instance) {
        SoulsField.SoulBarrier.set(AbstractDungeon.player, 0);
    }
}

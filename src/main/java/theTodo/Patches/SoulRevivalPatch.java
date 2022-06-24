package theTodo.Patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import javassist.CtBehavior;

@SpirePatch2(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
public class SoulRevivalPatch {
    @SpireInsertPatch(
            locator= EvokeLocator.class
    )
    public static void SoulRevival(AbstractPlayer __instance, DamageInfo info){
        if (!__instance.hasRelic(MarkOfTheBloom.ID)){
            if (SoulsField.Souls.get(__instance) > 0){
                int healTarget= AbstractDungeon.player.maxHealth / 2;
                int SoulCount = 0;
                for (int i = 0; i < healTarget;){
                    SoulCount += 1;
                    i += 9;
                    if (SoulCount > SoulsField.Souls.get(AbstractDungeon.player)){
                        SoulCount = SoulsField.Souls.get(AbstractDungeon.player);
                        break;
                    }
                }
                for (int ii = 0; ii < SoulCount; ii++) {
                    SoulsField.Souls.set(AbstractDungeon.player, SoulsField.Souls.get(AbstractDungeon.player) - 1);
                    __instance.decreaseMaxHealth(3);
                    AbstractDungeon.player.heal(9, true);
                }
            }
        }
    }
    private static class EvokeLocator extends SpireInsertLocator {
        private EvokeLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}

package theTodo.Patches.SoulsBarrierPatches;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.NewExprMatcher;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import java.util.ArrayList;
import java.util.Iterator;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class PlayerDamage {
    static boolean hadBarrier;

    public PlayerDamage() {
    }

    @SpireInsertPatch(
            locator = PlayerDamage.Locator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        hadBarrier = false;
        if (damageAmount[0] > 0) {
            Iterator var4 = DamageModifierManager.getDamageMods(info).iterator();

            while(var4.hasNext()) {
                AbstractDamageModifier mod = (AbstractDamageModifier)var4.next();
                if (mod.ignoresTempHP(__instance)) {
                    return;
                }
            }

            int temporaryHealth = (Integer)SoulsField.SoulBarrier.get(__instance);

                hadBarrier = true;

                for(int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(__instance.hb.cX, __instance.hb.cY));
                }

                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, false);
                if (temporaryHealth >= damageAmount[0]) {
                    temporaryHealth -= damageAmount[0];
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, damageAmount[0]));
                    damageAmount[0] = 0;
                } else {
                    damageAmount[0] -= temporaryHealth;
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, temporaryHealth));
                    temporaryHealth = 0;
                }

            SoulsField.SoulBarrier.set(__instance, temporaryHealth);
            }

        }

    @SpireInsertPatch(
            locator = PlayerDamage.StrikeEffectLocator.class
    )
    public static SpireReturn<Void> Insert(AbstractCreature __instance, DamageInfo info) {
        if (hadBarrier) {
            return SpireReturn.Return();
        } else {
            return SpireReturn.Continue();
        }
    }

    private static class StrikeEffectLocator extends SpireInsertLocator {
        private StrikeEffectLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new NewExprMatcher(StrikeEffect.class);
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            return new int[]{all[all.length - 1]};
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return offset(LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher), 1);
        }

        private static int[] offset(int[] originalArr, int offset) {
            for(int i = 0; i < originalArr.length; ++i) {
                originalArr[i] += offset;
            }

            return originalArr;
        }
    }
}


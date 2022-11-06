package theTodo.Patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import javassist.CtBehavior;
import theTodo.SoulbinderMod;

@SpirePatch2(
        clz = UseCardAction.class,
        method = "update"
)
public class SelfExilePatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(UseCardAction __instance) {
            AbstractCard targetcard = ReflectionHacks.getPrivate(__instance,UseCardAction.class,"targetCard");
            if (targetcard.hasTag(SoulbinderMod.SelfExile)) {
                __instance.isDone = true;
                AbstractDungeon.player.cardInUse = null;
                AbstractDungeon.effectsQueue.add(new PurgeCardEffect(targetcard));
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
}

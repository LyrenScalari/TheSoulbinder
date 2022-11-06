package theTodo.Patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.smartcardio.Card;

@SpirePatch2(clz = EmptyDeckShuffleAction.class, method = "update")
public class EntombShufflePatch {
    @SpirePrefixPatch()
    public static void RemoveCard(EmptyDeckShuffleAction __instance){
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (CardModifierManager.hasModifier(c,"EntombedMod")){
                CardModifierManager.removeModifiersById(c,"EntombedMod",false);
                AbstractDungeon.actionManager.addToBottom(new ExhaustToHandAction(c));
            }
        }
    }
}

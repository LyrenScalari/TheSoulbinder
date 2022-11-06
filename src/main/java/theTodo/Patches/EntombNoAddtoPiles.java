package theTodo.Patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch2(clz = CardGroup.class, method = "addToTop", paramtypez = {AbstractCard.class})
@SpirePatch2(clz = CardGroup.class, method = "addToBottom", paramtypez = {AbstractCard.class})
@SpirePatch2(clz = CardGroup.class, method = "addToHand", paramtypez = {AbstractCard.class})
@SpirePatch2(clz = CardGroup.class, method = "addToRandomSpot", paramtypez = {AbstractCard.class})
public class EntombNoAddtoPiles {
    @SpirePrefixPatch()
    public static SpireReturn EntombedCheck(CardGroup __instance, AbstractCard c){
            if (CardModifierManager.hasModifier(c,"EntombedMod")){
                if (AbstractDungeon.player.exhaustPile.group.contains(c)){
                    return SpireReturn.Return();
                }
                return SpireReturn.Continue();
            }
        return SpireReturn.Continue();
    }
}

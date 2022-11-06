package theTodo.Patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch2(clz = CardGroup.class, method = "removeCard", paramtypez = {AbstractCard.class})
public class EntombPatches {
    @SpirePrefixPatch()
    public static SpireReturn RemoveCard(CardGroup __instance, AbstractCard c){
        if (__instance == AbstractDungeon.player.exhaustPile){
            if (CardModifierManager.hasModifier(c,"EntombedMod")){
                c.triggerOnExhaust();
                for (AbstractRelic relic : AbstractDungeon.player.relics){
                    relic.onExhaust(c);
                }
                for (AbstractPower power : AbstractDungeon.player.powers){
                 power.onExhaust(c);
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        return SpireReturn.Continue();
    }
}

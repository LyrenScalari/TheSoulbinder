package theTodo.cardmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import theTodo.SoulbinderMod;

public class EntombedMod extends AbstractCardModifier {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(SoulbinderMod.modID+":EntombedModifer");
    public EntombedMod() {
    }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return cardStrings.EXTENDED_DESCRIPTION[0]+ rawDescription;
    }
    public String identifier(AbstractCard card) {
        return "EntombedMod";
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new EntombedMod();
    }
}

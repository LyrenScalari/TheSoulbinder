package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import theTodo.SoulbinderMod;

import java.util.Objects;

public class PurgeMod extends AbstractCardModifier {
    private boolean wasExhaust = false;
    private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("soulbindermod:CardmodStrings");

    public PurgeMod() {}

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.hasTag(SoulbinderMod.SelfExile)){
            card.tags.add(SoulbinderMod.SelfExile);
        } else {
            wasExhaust = true;
        }
    }
    @Override
    public void onRemove(AbstractCard c){
        if (!wasExhaust){
            c.tags.remove(SoulbinderMod.SelfExile);
        }
    }
    @Override
    public void onApplyPowers(AbstractCard owner){
        if (Objects.equals(owner.glowColor.toString(), "AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy()")){
            owner.glowColor = Color.RED.cpy();
        }
    }
    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  rawDescription + uiStrings.TEXT[0];
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PurgeMod();
    }
}
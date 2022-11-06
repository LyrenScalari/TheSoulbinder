package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.AbstractEasyCard;

import static theTodo.SoulbinderMod.makeID;

public class GraveDigger extends AbstractEasyCard {
    public final static String ID = makeID("GraveDigger");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public GraveDigger() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(card)->true,(AbstractCard)->{
            AbstractCard.forEach(card -> {
                CardModifierManager.addModifier(card,new EntombedMod());
                card.initializeDescription();
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            });
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}
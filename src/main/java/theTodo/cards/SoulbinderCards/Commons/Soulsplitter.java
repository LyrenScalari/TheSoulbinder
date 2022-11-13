package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.actions.ExileAction;
import theTodo.cardmods.EntombedMod;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;

import javax.smartcardio.Card;

import static theTodo.SoulbinderMod.makeID;

public class Soulsplitter extends AbstractEasyCard {
    public final static String ID = makeID("Soulsplitter");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Soulsplitter() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.exhaustPile.size() < 1) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        } else {
            return canUse;
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.exhaustPile.group.isEmpty()) {
            if (AbstractDungeon.player.exhaustPile.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(AbstractDungeon.player.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        AbstractCard copy = card.makeStatEquivalentCopy();
                        if (!CardModifierManager.hasModifier(card,"EntombedMod")){
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                            AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                        } else {
                            card.triggerOnExhaust();
                        }

                        CardModifierManager.removeModifiersById(copy,"EntombedMod",true);
                        CardModifierManager.addModifier(copy,new PurgeMod());
                        addToBot(new MakeTempCardInHandAction(copy));
                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard copy = AbstractDungeon.player.exhaustPile.getTopCard().makeStatEquivalentCopy();
                        
                        AbstractCard copy2 =  AbstractDungeon.player.exhaustPile.getNCardFromTop(1);
                        CardModifierManager.addModifier(copy,new PurgeMod());
                        CardModifierManager.addModifier(copy2,new PurgeMod());
                        if (!CardModifierManager.hasModifier( AbstractDungeon.player.exhaustPile.getTopCard(),"EntombedMod")){
                            AbstractDungeon.player.exhaustPile.getTopCard().stopGlowing();
                            AbstractDungeon.player.exhaustPile.getTopCard().unhover();
                            AbstractDungeon.player.exhaustPile.getTopCard().unfadeOut();
                            AbstractDungeon.player.exhaustPile.moveToDiscardPile(AbstractDungeon.player.exhaustPile.getTopCard());
                            if (upgraded){
                                AbstractDungeon.player.exhaustPile.getNCardFromTop(1).stopGlowing();
                                AbstractDungeon.player.exhaustPile.getNCardFromTop(1).unhover();
                                AbstractDungeon.player.exhaustPile.getNCardFromTop(1).unfadeOut();
                                AbstractDungeon.player.exhaustPile.moveToDiscardPile(AbstractDungeon.player.exhaustPile.getNCardFromTop(1));
                            } else {
                                AbstractDungeon.player.exhaustPile.getNCardFromTop(1).triggerOnExhaust();
                            }
                        } else {
                            AbstractDungeon.player.exhaustPile.getTopCard().triggerOnExhaust();
                        }

                        CardModifierManager.removeModifiersById(copy,"EntombedMod",true);
                        addToBot(new MakeTempCardInHandAction(copy));
                        if (upgraded) {
                            CardModifierManager.removeModifiersById(copy2,"EntombedMod",true);
                            addToBot(new MakeTempCardInHandAction(copy2));
                        }
                        isDone = true;
                    }
                });
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
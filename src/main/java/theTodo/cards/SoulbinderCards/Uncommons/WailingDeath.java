package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theTodo.actions.ExileAction;
import theTodo.cardmods.EntombedMod;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;

import java.util.function.Supplier;

import static theTodo.SoulbinderMod.makeID;

public class WailingDeath extends AbstractEasyCard {
    public final static String ID = makeID("WailingDeath");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public WailingDeath() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (!AbstractDungeon.player.exhaustPile.group.isEmpty()) {
            if (AbstractDungeon.player.drawPile.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(AbstractDungeon.player.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        AbstractCard copy = card.makeStatEquivalentCopy();
                        if (!CardModifierManager.hasModifier(card,"EntombedMod")){
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                        }
                        AbstractDungeon.player.exhaustPile.removeCard(card);
                        CardModifierManager.removeModifiersById(copy,"EntombedMod",true);
                        CardModifierManager.addModifier(copy,new PurgeMod());
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy,true,0,true,true));
                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard copy = AbstractDungeon.player.exhaustPile.getTopCard().makeStatEquivalentCopy();
                        CardModifierManager.addModifier(copy,new PurgeMod());
                        if (!CardModifierManager.hasModifier( AbstractDungeon.player.exhaustPile.getTopCard(),"EntombedMod")){
                            AbstractDungeon.player.exhaustPile.getTopCard().stopGlowing();
                            AbstractDungeon.player.exhaustPile.getTopCard().unhover();
                            AbstractDungeon.player.exhaustPile.getTopCard().unfadeOut();
                        }
                        AbstractDungeon.player.exhaustPile.removeCard(AbstractDungeon.player.exhaustPile.getTopCard());
                        CardModifierManager.removeModifiersById(copy,"EntombedMod",true);
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy,true,0,true,true));
                        isDone = true;
                    }
                });
            }
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}
package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTodo.SoulbinderMod;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class ItLives extends AbstractEasyCard {
    public final static String ID = makeID("ItLives");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public ItLives() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 2;
        cardsToPreview = new Skeleton();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.exhaustPile.group.isEmpty()) {
            if (AbstractDungeon.player.drawPile.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(AbstractDungeon.player.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        AbstractCard copy = cardsToPreview.makeStatEquivalentCopy();
                        if (!CardModifierManager.hasModifier(card, "EntombedMod")) {
                            CardModifierManager.addModifier(card, new PurgeMod());
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                        }
                        AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                        copy.setCostForTurn(0);
                        addToBot(new MakeTempCardInHandAction(copy,magicNumber,false));
                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard copy = cardsToPreview.makeStatEquivalentCopy();
                        CardModifierManager.addModifier(copy, new PurgeMod());
                        if (!CardModifierManager.hasModifier(AbstractDungeon.player.exhaustPile.getTopCard(), "EntombedMod")) {
                            CardModifierManager.addModifier(AbstractDungeon.player.exhaustPile.getTopCard(), new PurgeMod());
                            AbstractDungeon.player.exhaustPile.getTopCard().stopGlowing();
                            AbstractDungeon.player.exhaustPile.getTopCard().unhover();
                            AbstractDungeon.player.exhaustPile.getTopCard().unfadeOut();
                        }
                        AbstractDungeon.player.exhaustPile.moveToDiscardPile(AbstractDungeon.player.exhaustPile.getTopCard());
                        copy.setCostForTurn(0);
                        addToBot(new MakeTempCardInHandAction(copy,magicNumber,false));
                        isDone = true;
                    }
                });
            }
        }
    }
    public void triggerOnExhaust() {
        addToBot(new GainEnergyAction(secondMagic));
    }
    @Override
    public void upp() {
        upgradeSecondMagic(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        cardsToPreview.upgrade();
        initializeDescription();
    }

}
package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;
import theTodo.cards.SoulbinderCards.Swappables.Necropotence;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Necroblast extends AbstractSwappableCard {
    public final static String ID = makeID("Necroblast");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private ArrayList<AbstractCard> FilteredExhaustCards = new ArrayList<>();
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        return retVal;
    }
    public Necroblast() {
        this(null);
    }
    public Necroblast(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 1;
        if (linkedCard == null) {
            setLinkedCard(new Necropotence(this));
        } else {
            setLinkedCard(linkedCard);
        }
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.exhaustPile.size() < 1) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        } else if (!ExileCheck()) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[2];
            return ExileCheck();
        } else {
            return canUse;
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (c.baseDamage > 0 || c.baseBlock > 0){
                FilteredExhaustCards.add(c);
            }
        }
        if (!FilteredExhaustCards.isEmpty()) {
            if (FilteredExhaustCards.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(FilteredExhaustCards, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        AbstractCard copy = card.makeStatEquivalentCopy();
                        if (!CardModifierManager.hasModifier(card,"EntombedMod")){
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                            if (upgraded){
                                AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                            } else AbstractDungeon.player.exhaustPile.removeCard(card);
                        } else {
                            card.triggerOnExhaust();
                        }

                        if (card.baseDamage > card.baseBlock){
                            addToBot(new DamageAction(m,new DamageInfo(p,card.baseDamage*2, DamageInfo.DamageType.NORMAL)));
                            addToBot(new GainBlockAction(p,card.baseDamage*2));
                        } else {
                            addToBot(new DamageAction(m,new DamageInfo(p,card.baseBlock*2, DamageInfo.DamageType.NORMAL)));
                            addToBot(new GainBlockAction(p,card.baseBlock*2));
                        }
                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard copy = FilteredExhaustCards.get(0).makeStatEquivalentCopy();
                        CardModifierManager.addModifier(copy,new PurgeMod());
                        if (!CardModifierManager.hasModifier( FilteredExhaustCards.get(0),"EntombedMod")){
                            FilteredExhaustCards.get(0).stopGlowing();
                            FilteredExhaustCards.get(0).unhover();
                            FilteredExhaustCards.get(0).unfadeOut();
                            if (upgraded){
                                AbstractDungeon.player.exhaustPile.moveToDiscardPile(FilteredExhaustCards.get(0));
                            } else AbstractDungeon.player.exhaustPile.removeCard(FilteredExhaustCards.get(0));
                        } else {
                            FilteredExhaustCards.get(0).triggerOnExhaust();
                        }
                        if (FilteredExhaustCards.get(0).baseDamage > FilteredExhaustCards.get(0).baseBlock){
                            addToBot(new DamageAction(m,new DamageInfo(p,FilteredExhaustCards.get(0).baseDamage*2, DamageInfo.DamageType.NORMAL)));
                            addToBot(new GainBlockAction(p,FilteredExhaustCards.get(0).baseDamage*2));
                        } else {
                            addToBot(new DamageAction(m,new DamageInfo(p,FilteredExhaustCards.get(0).baseBlock*2, DamageInfo.DamageType.NORMAL)));
                            addToBot(new GainBlockAction(p,FilteredExhaustCards.get(0).baseBlock*2));
                        }
                        isDone = true;
                    }
                });
            }
        }
    }

    public boolean ExileCheck(){
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (c.baseDamage > 0 || c.baseBlock > 0){
                return true;
            }
        }
        return false;
    }

    public void upgrade() {
        upgradeDamage(2);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
        super.upgrade();
    }

    @Override
    public void upp() {

    }
}
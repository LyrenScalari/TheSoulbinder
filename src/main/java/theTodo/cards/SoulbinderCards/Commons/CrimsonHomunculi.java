package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.BoneBurst;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;
import theTodo.cards.SoulbinderCards.Swappables.Bloodcraft;
import theTodo.cards.SoulbinderCards.Tokens.CorpseSpider;
import theTodo.cards.SoulbinderCards.Tokens.FleshGolem;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class CrimsonHomunculi extends AbstractSwappableCard {
    public final static String ID = makeID("CrimsonHomunculi");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tags = new ArrayList<>();
        tags.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        return tags;
    }
    public CrimsonHomunculi() {
        this(null);
    }
    public CrimsonHomunculi (AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL,CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 4;
        secondMagic = baseSecondMagic = 2;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
        if (linkedCard == null) {
            setLinkedCard(new Bloodcraft(this));
        } else {
            setLinkedCard(linkedCard);
        }
        cardToPreview.add(new FleshGolem());
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.currentHealth <= energyCosts.get(TypeEnergyHelper.Mana.Health)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.isInAutoplay){
            addToBot(new DamageAction(p,new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS)));
        }
        for (int i = 0; i < secondMagic; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard card = cardsToPreview.makeStatEquivalentCopy();
                    AbstractDungeon.player.limbo.addToBottom(card);
                    CardModifierManager.addModifier(card, new EntombedMod());
                    card.initializeDescription();
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    isDone = true;
                }
            });
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                magicNumber +=1;
                baseMagicNumber += 1;
                energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(-1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
        cardToPreview.get(0).upgrade();
        initializeDescription();
    }
}
package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.LifeLinkPower;
import theTodo.util.FrostDamage;
import theTodo.util.SyphonDamage;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Lifelink extends AbstractEasyCard {
    public final static String ID = makeID("Lifelink");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        return retVal;
    }
    public Lifelink () {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseBlock = block = 4;
        baseMagicNumber = magicNumber = 2;
        secondMagic = baseSecondMagic = 4;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
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
        this.addToBot(new AnimateOrbAction(1));
        this.addToBot(new EvokeOrbAction(1));
        AbstractCard card = SoulbinderMod.getRandomUndead();
        AbstractDungeon.player.limbo.addToBottom(card);
        CardModifierManager.addModifier(card,new EntombedMod());
        addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
        card.initializeDescription();
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
        upgradeBaseCost(0);
    }
}
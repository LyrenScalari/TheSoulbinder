package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.RimePower;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class SigninBlood extends AbstractEasyCard {
    public final static String ID = makeID("SigninBlood");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    public SigninBlood() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
        secondMagic = baseSecondMagic = 4;
        energyCosts.put(TypeEnergyHelper.Mana.Health,secondMagic);
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
            addToBot(new DamageAction(p,new DamageInfo(p, secondMagic, DamageInfo.DamageType.HP_LOSS)));
        }
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                secondMagic +=1;
                baseSecondMagic += 1;
                energyCosts.put(TypeEnergyHelper.Mana.Health,secondMagic);
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}
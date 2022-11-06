package theTodo.cards.SoulbinderCards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theTodo.Orbs.FangsOrb;
import theTodo.actions.CoalesceAction;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.SyphonDamage;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeCardPath;
import static theTodo.SoulbinderMod.makeID;

public class Absorb extends AbstractEasyCard {
    public final static String ID = makeID("Absorb");
    public static final String textureImg = makeCardPath("Absorb.png");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:TempHPIcon] " + BaseMod.getKeywordTitle("temporary hp"), BaseMod.getKeywordDescription("temporary hp")));
        return retVal;
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
    public Absorb() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        magicNumber = baseMagicNumber = 2;
        secondMagic = baseSecondMagic = 6;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.isInAutoplay) {
            addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS)));
        }
        addToBot(new VFXAction(new BiteEffect(m.hb.cX,m.hb.cY)));
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new AddTemporaryHPAction(p,p,secondMagic));
        addToBot(new CoalesceAction(new FangsOrb()));
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
        upgradeDamage(3);
    }
}
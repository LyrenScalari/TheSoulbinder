package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.LifeLinkPower;
import theTodo.util.SyphonDamage;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class DrainingFog extends AbstractEasyCard {
    public final static String ID = makeID("DrainingFog");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:TempHPIcon] " + BaseMod.getKeywordTitle("temporary hp"), BaseMod.getKeywordDescription("temporary hp")));
        return retVal;
    }
    public DrainingFog () {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        damage = baseDamage = 8;
        baseMagicNumber = magicNumber = 3;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new DamageAction(mo,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (mo.hasPower(PoisonPower.POWER_ID)){
                addToBot(new VFXAction(new BiteEffect(mo.hb.cX,mo.hb.cY)));
                addToBot(new AddTemporaryHPAction(p,p,magicNumber));
            }
        }
    }

    public void upp() {
        upgradeDamage(4);
    }
}

package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.FrostFeverPower;
import theTodo.powers.SoulbinderPowerz.RimePower;
import theTodo.util.FrostDamage;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class HowlingBlast extends AbstractEasyCard {
    public final static String ID = makeID("HowlingBlast");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Frost_Fever"), BaseMod.getKeywordDescription("soulbindermod:Frost_Fever")));
        return retVal;
    }
    public HowlingBlast() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 3;
        secondMagic = baseSecondMagic = 6;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new VFXAction(new BlizzardEffect(secondMagic,false)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(mo,p,new RimePower(mo,secondMagic)));
            if (mo.hasPower(PoisonPower.POWER_ID)){
                addToBot(new ApplyPowerAction(mo,p,new FrostFeverPower(mo,magicNumber)));
            }
        }
    }
    @Override
    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }
}
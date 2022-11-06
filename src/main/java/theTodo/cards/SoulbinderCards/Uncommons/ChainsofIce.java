package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theTodo.SoulbinderMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.RimePower;
import theTodo.util.SyphonDamage;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class ChainsofIce extends AbstractEasyCard {
    public final static String ID = makeID("ChainsofIce");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        return retVal;
    }
    public ChainsofIce () {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        tags.add(SoulbinderMod.SelfExile);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(RimePower.POWER_ID)){
            addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,-m.getPower(RimePower.POWER_ID).amount)));
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}

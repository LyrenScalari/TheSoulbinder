package theTodo.util.Elements;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theTodo.powers.SoulbinderPowerz.RimePower;
import theTodo.util.VenomDamage;

import java.util.ArrayList;

public class BalanceDamage extends AbstractDamageModifier {
    boolean inherent;
    String TipType;
    public BalanceDamage(boolean isinherent){
        inherent = isinherent;
    }
    public boolean isInherent() {
        return inherent;
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Balance"), BaseMod.getKeywordDescription("soulbindermod:Balance")));
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Attune"), BaseMod.getKeywordDescription("soulbindermod:Attune")));
        return tips;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new VenomDamage(inherent);
    }
    public int priority() {
        return 2;
    }
}
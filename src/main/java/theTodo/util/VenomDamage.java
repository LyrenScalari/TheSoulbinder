package theTodo.util;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class VenomDamage extends AbstractDamageModifier {
    boolean inherent;
    String TipType;
    public VenomDamage(boolean isinherent){
        inherent = isinherent;
    }
    public boolean isInherent() {
        return inherent;
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Venom"), BaseMod.getKeywordDescription("soulbindermod:Venom")));
        tips.add(new TooltipInfo("[soulbindermod:PoisonIcon] " + TipHelper.capitalize(GameDictionary.POISON.NAMES[0]), GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));
        return tips;
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        if (unblockedAmount > 0 && info.type != DamageInfo.DamageType.HP_LOSS){
            addToBot(new ApplyPowerAction(target,info.owner,new PoisonPower(target,info.owner,(int)Math.ceil(unblockedAmount/2))));
        }
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new VenomDamage(inherent);
    }
    public int priority() {
        return 2;
    }
}
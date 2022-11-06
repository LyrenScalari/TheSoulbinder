package theTodo.util;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModContainer;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import theTodo.Patches.SoulsBarrierPatches.SoulsField;
import theTodo.actions.GainSoulBarrierAction;

import java.util.ArrayList;

public class SyphonDamage extends AbstractDamageModifier {
    boolean inherent;
    String TipType;
    public SyphonDamage(boolean isinherent){
        inherent = isinherent;
    }
    public SyphonDamage(boolean isinherent, boolean autobind){
        inherent = isinherent;
        automaticBindingForCards = autobind;
    }
    public boolean isInherent() {
        return inherent;
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Syphon"), BaseMod.getKeywordDescription("soulbindermod:Syphon")));
        return tips;
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        if (unblockedAmount > 0 && info.type != DamageInfo.DamageType.HP_LOSS){
            if (!Settings.FAST_MODE) {
                for (int i = 0; i < unblockedAmount; i++) {
                    addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                }
                addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,unblockedAmount));
            } else {
                for (int i = 0; i < unblockedAmount; i++) {
                    AbstractDungeon.effectsQueue.add(new FlyingOrbEffect(target.hb.cX, target.hb.cY));
                }
                addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,unblockedAmount));
            }
        }
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new SyphonDamage(inherent);
    }
    public int priority() {
        return 2;
    }
}
package theTodo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import theTodo.Patches.SoulsBarrierPatches.SoulsField;

public class GainSoulBarrierAction extends AbstractGameAction {
    boolean ToHeal;
    public GainSoulBarrierAction(AbstractCreature target, int amount, boolean toHeal) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.HEAL;
        ToHeal = toHeal;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (SoulsField.SoulBarrier.get(target) + amount > SoulsField.MaxSouls.get(target)*3){
                SoulsField.SoulBarrier.set(target, SoulsField.MaxSouls.get(target)*3);
                if (SoulsField.SoulBarrier.get(target) > 0){
                    if(ToHeal) {
                        addToTop(new HealAction(target, target, (int) Math.ceil(((SoulsField.SoulBarrier.get(target) + amount) -
                                (SoulsField.MaxSouls.get(target) * 3)) * 0.75)));
                    }
                } else {
                    if(ToHeal) {
                        addToTop(new HealAction(target, target, (int) Math.ceil(((SoulsField.SoulBarrier.get(target) + amount) -
                                (SoulsField.MaxSouls.get(target) * 3)))));
                    }
                }
            } else {
                SoulsField.SoulBarrier.set(target, SoulsField.SoulBarrier.get(target) + amount);
            }
            if (amount > 0) {
                AbstractDungeon.effectsQueue.add(new HealEffect(target.hb.cX - target.animX, target.hb.cY, amount));
                target.healthBarUpdatedEvent();
            }
        }

        tickDuration();
    }
}

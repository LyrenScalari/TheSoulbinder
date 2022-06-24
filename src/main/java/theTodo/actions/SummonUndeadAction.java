package theTodo.actions;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.powers.SoulbinderPowerz.SummoningSickness;
import theTodo.powers.SoulbinderPowerz.UndeadPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SummonUndeadAction extends AbstractGameAction {
    private AbstractUndeadMonster undeadMonster;
    public SummonUndeadAction(AbstractUndeadMonster Undead) {
        this.duration = Settings.ACTION_DUR_FAST;
        undeadMonster = Undead;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() < 5){
                MinionUtils.addMinion(AbstractDungeon.player, undeadMonster);
                undeadMonster.setTakenTurn(true);
                addToTop(new ApplyPowerAction(undeadMonster,undeadMonster,new UndeadPower(undeadMonster)));
                if (undeadMonster.PersonalEffect != null){
                    addToTop(new ApplyPowerAction(undeadMonster,undeadMonster,undeadMonster.PersonalEffect));
                }
                addToTop(new ApplyPowerAction(undeadMonster,undeadMonster,new SummoningSickness(undeadMonster)));
            } else {
                List<AbstractMonster> duplicates = new ArrayList<>();
                ArrayList<AbstractMonster> HoardResolve = new ArrayList(MinionUtils.getMinions(AbstractDungeon.player).monsters);
                for (AbstractMonster minion : HoardResolve){
                    for (AbstractMonster matchcheck : HoardResolve){
                        if (minion instanceof AbstractUndeadMonster && minion.id.equals(matchcheck.id) && matchcheck != minion && !duplicates.contains(minion)){
                            if (((AbstractUndeadMonster) minion).baseblock != -1){
                                ((AbstractUndeadMonster)minion).baseblock +=  ((AbstractUndeadMonster)matchcheck).baseblock/2;
                            }
                            if (((AbstractUndeadMonster) minion).basedamage != -1){
                                ((AbstractUndeadMonster)minion).basedamage += ((AbstractUndeadMonster)matchcheck).basedamage/2;
                            }
                            minion.maxHealth += ((AbstractUndeadMonster)matchcheck).basemhp/2;
                            minion.heal(((AbstractUndeadMonster)matchcheck).basemhp/2);
                            ((AbstractUndeadMonster) minion).clearMoves();
                            ((AbstractUndeadMonster) minion).addMoves();
                            MinionUtils.getMinions(AbstractDungeon.player).monsters.remove(matchcheck);
                            duplicates.add(matchcheck);
                        }
                    }
                }
                for (AbstractMonster minion : MinionUtils.getMinions(AbstractDungeon.player).monsters){
                    if (minion instanceof AbstractUndeadMonster && minion.id.equals(undeadMonster.id)){
                        if (((AbstractUndeadMonster) minion).baseblock != -1){
                            ((AbstractUndeadMonster)minion).baseblock += undeadMonster.baseblock/2;
                        }
                        if (((AbstractUndeadMonster) minion).basedamage != -1){
                            ((AbstractUndeadMonster)minion).basedamage += undeadMonster.basedamage/2;
                        }
                        minion.maxHealth += undeadMonster.basemhp/2;
                        minion.heal(undeadMonster.basemhp/2);
                        ((AbstractUndeadMonster) minion).clearMoves();
                        ((AbstractUndeadMonster) minion).addMoves();
                    }
                }
                isDone = true;
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
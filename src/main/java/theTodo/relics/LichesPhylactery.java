package theTodo.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import theTodo.TheSoulbinder;

import static theTodo.SoulbinderMod.makeID;

public class LichesPhylactery extends AbstractEasyRelic {
    public static final String ID = makeID("LichesPhylactery");

    public LichesPhylactery() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, TheSoulbinder.Enums.SOULBINDER_COLOR);
        counter = 3;
    }
    public void atBattleStart() {
        if (usedUp){
            usedUp = false;
        }
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !m.hasPower(MinionPower.POWER_ID) && !usedUp) {
            this.flash();
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,counter));
            usedUp = true;
        }

    }
}

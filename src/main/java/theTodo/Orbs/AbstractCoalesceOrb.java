package theTodo.Orbs;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.powers.SoulbinderPowerz.NetherworldResonancePower;

public abstract class AbstractCoalesceOrb extends CustomOrb {
    public AbstractCoalesceOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
    }
    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power != null && !this.ID.equals("Plasma")) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        }
        AbstractPower power2 = AbstractDungeon.player.getPower(NetherworldResonancePower.POWER_ID);
        if (power2 != null && !this.ID.equals("Plasma")) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power2.amount);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power2.amount);
        }
    }
}

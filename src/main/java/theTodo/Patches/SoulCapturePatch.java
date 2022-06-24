package theTodo.Patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theTodo.relics.LichesPhylactery;

import static theTodo.SoulbinderMod.getSoulsRender;
import static theTodo.SoulbinderMod.renderSoulsCounter;

public class SoulCapturePatch {
    @SpirePatch(clz = AbstractMonster.class, method = "die", paramtypez = {boolean.class})
    public static class SoulCapture{
        public static void Postfix(AbstractMonster __instance, boolean triggerRelics){
            if (!__instance.hasPower(MinionPower.POWER_ID)){
                SoulsField.Souls.set(AbstractDungeon.player,SoulsField.Souls.get(AbstractDungeon.player)+1);
                AbstractDungeon.player.increaseMaxHp(3,true);
            }
        }
    }
}

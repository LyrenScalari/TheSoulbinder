package theTodo.Patches.SoulsPatches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import theTodo.TheSoulbinder;

public class SoulCapturePatch {
    @SpirePatch(clz = AbstractMonster.class, method = "die", paramtypez = {boolean.class})
    public static class SoulCapture{
        public static void Postfix(AbstractMonster __instance, boolean triggerRelics){
            if (!__instance.hasPower(MinionPower.POWER_ID)){
                if ((AbstractDungeon.player.chosenClass == TheSoulbinder.Enums.THE_SOULBINDER || AbstractDungeon.player.hasRelic(PrismaticShard.ID))) {
                    SoulsField.Souls.set(AbstractDungeon.player, SoulsField.Souls.get(AbstractDungeon.player) + 1);
                    SoulsField.MaxSouls.set(AbstractDungeon.player, SoulsField.MaxSouls.get(AbstractDungeon.player) + 1);
                }
                if (Loader.isModLoaded("downfall")){
                    if (AbstractDungeon.player.chosenClass == TheSnecko.Enums.THE_SNECKO && SneckoMod.validColors.contains(TheSoulbinder.Enums.SOULBINDER_COLOR)){
                        SoulsField.Souls.set(AbstractDungeon.player, SoulsField.Souls.get(AbstractDungeon.player) + 1);
                        SoulsField.MaxSouls.set(AbstractDungeon.player, SoulsField.MaxSouls.get(AbstractDungeon.player) + 1);
                    }
                }
            }
        }
    }
}

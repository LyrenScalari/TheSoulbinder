package theTodo.Patches;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theTodo.SoulbinderMod;
import theTodo.TheSoulbinder;
import theTodo.vfx.SoulCounterEffect;

import static theTodo.SoulbinderMod.*;

public class RenderSoulsCounter {
    public static SoulCounterEffect SoulCounter = new SoulCounterEffect();
    private static boolean Rendering = false;
        @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
        public static class RenderPatch{
            public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
                if(getSoulsRender()){
                    renderSoulsCounter(sb, __instance.current_x);
                }
            }
        }

        @SpirePatch(clz = EnergyPanel.class, method = "update")
        public static class UpdatePatch{
            public static void Prefix(){
                // Only update when rendering elements counter
                if (getSoulsRender()) {
                    updateSoulsCounter();
                }
            }
        }
}

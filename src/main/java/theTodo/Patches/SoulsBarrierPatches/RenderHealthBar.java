package theTodo.Patches.SoulsBarrierPatches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth"
)
public class RenderHealthBar {
    private static float HEALTH_BAR_HEIGHT = -1.0F;
    private static float HEALTH_BAR_OFFSET_Y = -1.0F;

    public RenderHealthBar() {
    }

    @SpireInsertPatch(
            locator = RenderHealthBar.Locator.class,
            localvars = {"x", "y"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
        if (HEALTH_BAR_HEIGHT == -1.0F) {
            HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
            HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
        }

        if (!Gdx.input.isKeyPressed(36) && (Integer) SoulsField.SoulBarrier.get(__instance) > 0 && __instance.hbAlpha > 0.0F) {
            renderTempHPIconAndValue(__instance, sb, x, y);
        }

    }

    private static <O, T> T getPrivate(Class<O> cls, Object obj, String varName, Class<T> type) {
        try {
            Field f = cls.getDeclaredField(varName);
            f.setAccessible(true);
            return type.cast(f.get(obj));
        } catch (IllegalAccessException | NoSuchFieldException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static <O, T> T getPrivate(Class<O> obj, String varName, Class<T> type) {
        return getPrivate(obj, (Object)null, varName, type);
    }

    private static void renderTempHPIconAndValue(AbstractCreature creature, SpriteBatch sb, float x, float y) {
        sb.setColor(Color.NAVY.cpy());
        sb.draw(StSLib.TEMP_HP_ICON, x + (Float)getPrivate(AbstractCreature.class, "BLOCK_ICON_X", Float.class) - 16.0F + creature.hb.width,
                y + (Float)getPrivate(AbstractCreature.class, "BLOCK_ICON_Y", Float.class) - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F,
                Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString((Integer)SoulsField.SoulBarrier.get(creature)), x + (Float)getPrivate(AbstractCreature.class,
                "BLOCK_ICON_X", Float.class) + 16.0F + creature.hb.width, y - 16.0F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            ArrayList<Matcher> matchers = new ArrayList();
            matchers.add(finalMatcher);
            return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher);
        }
    }
}

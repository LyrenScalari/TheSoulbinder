package theTodo.util;

import basemod.BaseMod;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import theTodo.Patches.SoulsBarrierPatches.SoulsField;

public class SoulsCounter extends ClickableUIElement {
    public Hitbox hb;
    private FrameBuffer fbo;
    private static float hb_w = 100.0F;
    private static float hb_h = 148.0F;
    private static final float baseX = 198.0F * Settings.scale;
    private static final float baseY = 320.0F * Settings.scale;
    private float x = baseX;
    private float y = baseY;
    private static int IMG_DIM = 256;
    private static int rotation;
    public static float fontScale = 1.0F;
    public static Texture Gem;
    public static Texture Fill;
    public SoulsCounter(Texture image){
        super(image, baseX, baseY , hb_w, hb_h);
        this.image = image;
        Gem = ImageMaster.loadImage("soulbindermodResources/images/ui/SoulGem.png");
        Fill = ImageMaster.loadImage("soulbindermodResources/images/ui/SoulGem_Fill.png");
        hb = new Hitbox(baseX + hb_w/2,baseY/2,hb_w,hb_h);
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false, false);
        this.setClickable(false);
    }
    public void render(SpriteBatch sb, float current_x){
        rotation += 1;
        updateHitboxPosition(current_x + x - baseX, y);
        sb.setBlendFunction(770, 1);
        sb.draw(Fill, this.x - (float) image.getWidth(), this.y - (float) image.getHeight()/1.5f, (float) Fill.getWidth() / 2.0F, (float)Fill.getHeight() / 2.0F,
                (float)Fill.getHeight(), (float)Fill.getHeight(), 2.0f * Settings.scale, 2.0f * Settings.scale, 0,0,0,50,74,false,false);
        if (SoulsField.Souls.get(AbstractDungeon.player) > 0 ){
            sb.draw(image, this.x - (float) image.getWidth() / 2.0F, this.y - (float) image.getWidth() / 2.5F, (float) image.getWidth() / 2.0F, (float)image.getHeight() / 2.0F,
                    (float)image.getHeight(), (float) image.getHeight(), 2.0f * Settings.scale, 2.0f * Settings.scale, rotation,0,0,45,46,false,false);
        }
        sb.setBlendFunction(770, 771);
        sb.draw(Gem, this.x - (float) image.getWidth(), this.y - (float) image.getHeight()/1.5f, (float) Gem.getWidth() / 2.0F, (float)Gem.getHeight() / 2.0F,
                (float)Gem.getHeight(), (float) Gem.getHeight(), 2.0f * Settings.scale, 2.0f * Settings.scale, 0,0,0,50,74,false,false);
        if (SoulsField.MaxSouls.get(AbstractDungeon.player) > 0 ) {
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, "" + SoulsField.MaxSouls.get(AbstractDungeon.player)*3, current_x + x - baseX, y - 40.0f * Settings.scale, Color.WHITE, fontScale);
        }
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, SoulsField.Souls.get(AbstractDungeon.player).toString(),  current_x + x - baseX, y + 4.0f * Settings.scale, Color.WHITE, fontScale);
        hb.render(sb);
    }

    private void updateHitboxPosition(float x, float y){
        hb.translate(x - hb_w/2, y/2);
    }
    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(x + 55.0F * Settings.scale, y + 23.0F * Settings.scale, BaseMod.getKeywordTitle("soulbindermod:Soul_Gem"), BaseMod.getKeywordDescription("soulbindermod:Soul_Gem"));
        TipHelper.renderGenericTip(x + 55.0F * Settings.scale, y + 50.0F * Settings.scale, BaseMod.getKeywordTitle("soulbindermod:Soul_Barrier"), BaseMod.getKeywordDescription("soulbindermod:Soul_Barrier"));
    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }
}

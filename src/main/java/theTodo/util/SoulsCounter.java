package theTodo.util;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import theTodo.Patches.RenderSoulsCounter;
import theTodo.Patches.SoulsField;

public class SoulsCounter extends ClickableUIElement {
    public Hitbox hb;
    private SpriteBatch sb2;
    private FrameBuffer fbo;
    private static final float ELEMENTS_IMG_SCALE = 1.15F * Settings.scale;
    private static float hb_w = 70.0F * ELEMENTS_IMG_SCALE;
    private static float hb_h = 96.0F * ELEMENTS_IMG_SCALE;
    private static final float baseX = 198.0F * Settings.scale;
    private static final float baseY = 320.0F * Settings.scale;
    private float x = baseX;
    private float y = baseY;
    private static int IMG_DIM = 256;
    private static int rotation;
    public static float fontScale = 0.6F;
    public SoulsCounter(Texture image){
        super(image, baseX, baseY , hb_w, hb_h);
        this.image = image;
        hb = new Hitbox(baseX - hb_w/2,baseY,hb_w,hb_h);
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false, false);
        this.setClickable(false);
        sb2 = new SpriteBatch(20);
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0,0, IMG_DIM, IMG_DIM);
        sb2.setProjectionMatrix(matrix);
    }
    public void render(SpriteBatch sb, float current_x){
        sb.setColor(Color.CYAN.cpy());
        float x2 = current_x + x - baseX;
        updateHitboxPosition(x2, y);
        rotation += 1;
        sb.setBlendFunction(770, 1);
        sb.draw(image, this.x - (float) image.getWidth() / 2.0F, this.y - (float) image.getWidth() / 2.0F, (float) image.getWidth() / 2.0F, (float)image.getHeight() / 2.0F,
                (float)image.getHeight(), (float) image.getHeight(), 10.0f * Settings.scale, 10.0f * Settings.scale, rotation,0,0,96,96,false,false);
        sb.setBlendFunction(770, 771);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, SoulsField.Souls.get(AbstractDungeon.player).toString(), x2, y - 31.0F * ELEMENTS_IMG_SCALE, Color.WHITE, fontScale);
    }

    private void updateHitboxPosition(float x, float y){
        hb.translate(x - hb_w/2, y/2);
    }
    @Override
    protected void onHover() {

    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }
}

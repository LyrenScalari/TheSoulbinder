package theTodo.powers.SoulbinderPowerz;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static theTodo.SoulbinderMod.makePowerPath;

public class SummoningSickness extends AbstractEasyPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("SummoningSickness");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("SummoningSickness84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("SummoningSickness32.png"));

    public SummoningSickness(final AbstractCreature owner) {
        super(powerStrings.NAME, NeutralPowertypePatch.NEUTRAL,true,owner,-1);
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = false;

        // We load those txtures here.
        this.img = ImageMaster.loadImage("images/stslib/powers/32/stun.png");
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        updateDescription();
    }
    public void atEndOfTurn(boolean isplayer) {
        addToTop(new RemoveSpecificPowerAction(owner,owner,this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SummoningSickness(owner);
    }
}
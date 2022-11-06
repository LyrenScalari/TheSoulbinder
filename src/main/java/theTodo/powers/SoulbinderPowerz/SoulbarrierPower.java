package theTodo.powers.SoulbinderPowerz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

public class SoulbarrierPower extends AbstractEasyPower
{
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("SoulbarrierPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/SoulBarrier.png");
    private static final Texture tex32 = TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/SoulBarrier84.png");

    public SoulbarrierPower(final AbstractCreature owner, int amount) {
        super(powerStrings.NAME, PowerType.DEBUFF,true,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        // We load those txtures here.
        region48 = new TextureAtlas.AtlasRegion(tex32,0,0,32,32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        updateDescription();
    }
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            addToBot(new ReducePowerAction(owner,owner,this,1));
            damageAmount -= amount;
            return damageAmount;
        }
        return  damageAmount;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulbarrierPower(owner,amount);
    }
}
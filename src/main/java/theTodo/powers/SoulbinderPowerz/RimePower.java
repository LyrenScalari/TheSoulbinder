package theTodo.powers.SoulbinderPowerz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.SoulbinderMod;
import theTodo.cards.SoulbinderCards.Uncommons.SnapFreeze;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static theTodo.SoulbinderMod.makePowerPath;

public class RimePower extends AbstractEasyPower
    {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("RimePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Chill.png");
    private static final Texture tex32 = TexLoader.getTexture("soulbindermodResources/images/ui/powerIcons/Chill32.png");

    public RimePower(final AbstractCreature owner, int amount) {
        super(powerStrings.NAME, PowerType.DEBUFF,true,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.DEBUFF;
        isTurnBased = false;
        // We load those txtures here.
        region48 = new TextureAtlas.AtlasRegion(tex32,0,0,32,32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        updateDescription();
    }
        public int onAttacked(DamageInfo info, int damageAmount) {
            if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
                this.flash();
                this.addToTop(new GainBlockAction(AbstractDungeon.player, this.amount/2, Settings.FAST_MODE));
                if (!owner.hasPower(FrostFeverPower.POWER_ID)){
                    addToBot(new ReducePowerAction(owner,owner,this,1));
                }
                if (AbstractDungeon.player.hasPower(SnapFreezePower.POWER_ID)){
                    AbstractDungeon.player.getPower(SnapFreezePower.POWER_ID).flash();
                    addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,AbstractDungeon.player.getPower(SnapFreezePower.POWER_ID).amount, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
            }

            return damageAmount;
        }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount/2 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RimePower(owner,amount);
    }
}
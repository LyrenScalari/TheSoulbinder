package theTodo.powers.SoulbinderPowerz;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static theTodo.SoulbinderMod.makePowerPath;

public class UndeadPower extends AbstractEasyPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("UndeadPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public UndeadPower(final AbstractCreature owner) {
        super(powerStrings.NAME, NeutralPowertypePatch.NEUTRAL,true,owner,-1);
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = false;

        // We load those txtures here.
        this.loadRegion("minion");

        updateDescription();
    }
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentHealth + owner.currentBlock) {
            this.addToTop(new DamageAction(AbstractDungeon.player,new DamageInfo(info.owner,info.output-(owner.currentHealth + owner.currentBlock),info.type)));
            return owner.currentHealth + owner.currentBlock;
        }
        return damageAmount;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UndeadPower(owner);
    }
}
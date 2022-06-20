package theTodo.powers.SoulbinderPowerz;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.patches.MonsterAddFieldsPatch;
import hlysine.friendlymonsters.utils.MonsterIntentUtils;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static theTodo.SoulbinderMod.makePowerPath;

public class DefenderPower extends AbstractEasyPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("DefenderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractMonster Attacker;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public DefenderPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(powerStrings.NAME, NeutralPowertypePatch.NEUTRAL,true,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = true;

        // We load those txtures here.
        this.loadRegion("platedarmor");

        updateDescription();
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        addToTop(new ReducePowerAction(owner,owner,this,1));
        return damageAmount;
    }
    public void atStartOfTurn() {
        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,this));
    }
    public void onRemove() {
        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,ProtectedPower.POWER_ID));
    }
    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = this.owner.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else description = this.owner.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DefenderPower(owner, source, amount);
    }
}
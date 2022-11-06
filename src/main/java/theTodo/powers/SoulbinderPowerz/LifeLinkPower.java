package theTodo.powers.SoulbinderPowerz;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.*;
import static theTodo.SoulbinderMod.makePowerPath;

public class LifeLinkPower extends AbstractEasyPower implements CloneablePowerInterface, NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("LifeLinkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractMonster Linked;
    public AbstractCard LinkedCard;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public LifeLinkPower(final AbstractCreature owner, final AbstractCreature source, int amount, AbstractMonster LinkedMonster, AbstractCard LinkedCard) {
        super(powerStrings.NAME, NeutralPowertypePatch.NEUTRAL,true,owner,-1);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = true;
        Linked = LinkedMonster;
        this.LinkedCard = LinkedCard;
        // We load those txtures here.
        this.loadRegion("heartDef");

        updateDescription();
    }
    public void atStartOfTurn() {
        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,this));
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new DamageAction(Linked, new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS), SLASH_HORIZONTAL, true));
            addToBot(new AddTemporaryHPAction(owner,owner,amount));
        }

        return damageAmount;
    }
    @Override
    public void updateDescription() {
        if (Linked != null) {
            if (amount > 1) {
                description = Linked.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            } else description = Linked.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else {
            if (amount > 1) {
                description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            } else description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new LifeLinkPower(owner, source,amount, Linked, LinkedCard);
    }
}
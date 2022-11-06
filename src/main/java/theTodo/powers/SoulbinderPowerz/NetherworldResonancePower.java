package theTodo.powers.SoulbinderPowerz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

public class NetherworldResonancePower extends AbstractEasyPower
{
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("NetherworldResonancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NetherworldResonancePower(final AbstractCreature owner, int amount) {
        super(powerStrings.NAME, PowerType.BUFF,true,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
    }
    public void atStartOfTurn() {
        this.flash();
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(SoulbinderMod.Undead)){
            damage = damage + this.amount;
        }
        return super.atDamageGive(damage, type, card);
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if (card.hasTag(SoulbinderMod.Undead)){
            block = block + this.amount;
        }
        return super.modifyBlock(block, card);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new NetherworldResonancePower(owner,amount);
    }
}

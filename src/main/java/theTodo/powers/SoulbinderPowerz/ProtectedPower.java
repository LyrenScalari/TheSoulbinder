package theTodo.powers.SoulbinderPowerz;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import hlysine.friendlymonsters.utils.MonsterIntentUtils;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.SoulbinderMod;
import theTodo.powers.AbstractEasyPower;
import theTodo.util.TexLoader;

import static theTodo.SoulbinderMod.makePowerPath;

public class ProtectedPower extends AbstractEasyPower implements CloneablePowerInterface, NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = SoulbinderMod.makeID("ProtectedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractUndeadMonster Defender;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ProtectedPower(final AbstractCreature owner, final AbstractCreature source, AbstractUndeadMonster defender) {
        super(powerStrings.NAME, NeutralPowertypePatch.NEUTRAL,true,owner,-1);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        amount = -1;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = true;
        Defender = defender;
        // We load those txtures here.
        this.loadRegion("heartDef");

        updateDescription();
    }

    public void onInitialApplication() {
        for (AbstractMonster minion : MinionUtils.getMinions(AbstractDungeon.player).monsters){
            ((AbstractUndeadMonster) minion).clearMoves();
            ((AbstractUndeadMonster) minion).addMoves();
        }
    }
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            this.addToTop(new DamageAction(Defender,info));
        }
        return 0;
    }
    @Override
    public void updateDescription() {
        if (Defender != null) {
            if (amount > 1) {
                description = Defender.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            } else description = Defender.name + DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else {
            if (amount > 1) {
                description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            } else description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new ProtectedPower(owner, source, Defender);
    }
}
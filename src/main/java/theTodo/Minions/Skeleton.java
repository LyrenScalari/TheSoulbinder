package theTodo.Minions;

import basemod.devcommands.power.Power;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import hlysine.friendlymonsters.monsters.MinionMove;
import theTodo.powers.SoulbinderPowerz.DefenderPower;
import theTodo.powers.SoulbinderPowerz.ProtectedPower;
import theTodo.powers.SoulbinderPowerz.SummoningSickness;

import static theTodo.SoulbinderMod.modID;

public class Skeleton extends AbstractUndeadMonster {

    private static String NAME = "Skeleton";
    public  static String ID = "Skeleton";
    private AbstractMonster target;


    public Skeleton(int offsetX, int offsetY) {
        super(NAME, ID, 6, -8.0F, 10.0F, 166.0F, 327.0F, modID + "Resources/images/char/mainChar/main.png", offsetX, offsetY);
        flipHorizontal = true;
        name = NAME;
        baseblock = 4;
        basedamage = 4;
        basemagic = 1;
        basemhp = 6;
        addMoves();

    }

    public void addMoves(){
        if (!this.hasPower(SummoningSickness.POWER_ID)){
            this.moves.addMove(new MinionMove("Bone Club", this, new Texture(modID + "Resources/images/monsters/attack_monster_intent_1.png"), "Deal #b" + basedamage + " damage", () -> {
                target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(this,basedamage,DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
            if (!AbstractDungeon.player.hasPower(ProtectedPower.POWER_ID)){
                this.moves.addMove(new MinionMove("Guard", this, new Texture(modID + "Resources/images/monsters/Minion_Block.png"),"Gain #b" +baseblock+ " block, Redirect the next #b" + basemagic+ " attack this turn", () -> {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this,this,  baseblock));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new ProtectedPower(AbstractDungeon.player,this,this)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new DefenderPower(this,this,basemagic)));
                }));
            }
        }
    }
    public void applyPowers() {
    }
}
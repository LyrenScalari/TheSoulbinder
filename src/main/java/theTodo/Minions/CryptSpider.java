package theTodo.Minions;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hlysine.friendlymonsters.monsters.MinionMove;
import theTodo.powers.SoulbinderPowerz.DefenderPower;
import theTodo.powers.SoulbinderPowerz.ProtectedPower;
import theTodo.powers.SoulbinderPowerz.SummoningSickness;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.*;
import static theTodo.SoulbinderMod.modID;

public class CryptSpider extends AbstractUndeadMonster {

    private static String NAME = "Crypt Spider";
    public  static String ID = "Crypt Spider";
    private AbstractMonster target;



    public CryptSpider(int offsetX, int offsetY) {
        super(NAME, ID, 25, -8.0F, 10.0F, 166.0F, 327.0F, modID + "Resources/images/char/mainChar/main.png", offsetX, offsetY);
        flipHorizontal = true;
        name = NAME;
        basedamage = 6;
        basemagic = 2;
        basemhp = 25;
        addMoves();
        PersonalEffect = new EnvenomPower(this,3);
    }

    public void addMoves(){
        if (!this.hasPower(SummoningSickness.POWER_ID)){
            this.moves.addMove(new MinionMove("Plague Bite", this, new Texture(modID + "Resources/images/monsters/attack_monster_intent_2.png"), "Deal #b" + basedamage + " damage twice", () -> {
                target = AbstractDungeon.getRandomMonster();
                DamageInfo info = new DamageInfo(this,basedamage,DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
                target = AbstractDungeon.getRandomMonster();
                info = new DamageInfo(this,basedamage,DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
            this.moves.addMove(new MinionMove("Toxic Webs", this, new Texture(modID + "Resources/images/monsters/attack_monster_intent_6.png"),"Deal #b"+ basedamage/2 +" damage and apply #b"+basemagic+" #yWeak to all enemies.", () -> {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this,DamageInfo.createDamageMatrix(basedamage/2), DamageInfo.DamageType.NORMAL, AttackEffect.POISON));
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    addToBot(new ApplyPowerAction(m,this,new WeakPower(m,basemagic,false)));
                }
            }));
        }
    }
    public void applyPowers() {
    }
}
package theTodo.cards.SoulbinderCards.Commons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import theTodo.Minions.CryptSpider;
import theTodo.Minions.Skeleton;
import theTodo.actions.SummonUndeadAction;
import theTodo.cards.AbstractEasyCard;

import static theTodo.SoulbinderMod.makeID;

public class CorpseJar extends AbstractEasyCard {
    public final static String ID = makeID("CorpseJar");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public CorpseJar() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        int offsetX= -700;
        int offsetY= -55;
        if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() == 0){
            offsetX = -700;
            offsetY = -45;
        }
        if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() == 1){
            offsetX = -750;
            offsetY= 65;
        }
        if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() == 2){
            offsetX = -555;
            offsetY = -20;
        }
        if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() == 3){
            offsetX = -1150;
            offsetY= -25;
        }
        if (MinionUtils.getMinions(AbstractDungeon.player).monsters.size() == 4){
            offsetX = -1000;
            offsetY= 55;
        }
        addToBot(new SummonUndeadAction(new CryptSpider(offsetX,offsetY)));
    }

    public void upp() {
        upgradeDamage(3);
    }
}
package theTodo.cards.SoulbinderCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import theTodo.Minions.Skeleton;
import theTodo.actions.SummonUndeadAction;
import theTodo.cards.AbstractEasyCard;

import static theTodo.SoulbinderMod.makeID;

public class BoneSculpt extends AbstractEasyCard {
    public final static String ID = makeID("BoneSculpt");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public BoneSculpt () {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
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
        addToBot(new SummonUndeadAction(new Skeleton(offsetX,offsetY)));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}
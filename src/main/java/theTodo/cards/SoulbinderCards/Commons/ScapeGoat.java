package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.Minions.Skeleton;
import theTodo.actions.SummonUndeadAction;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.DefenderPower;
import theTodo.powers.SoulbinderPowerz.ProtectedPower;
import theTodo.util.MinionTargeting;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class ScapeGoat extends AbstractEasyCard {
    public final static String ID = makeID("ScapeGoat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        return retVal;
    }
    public ScapeGoat () {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
         baseBlock = block = 8;
         baseMagicNumber = magicNumber = 2;
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        if (MinionUtils.getMinions(p).monsters.isEmpty()){
            cantUseMessage = BaseMod.getKeywordDescription("soulbinder:No_Minions");
            return false;
        }
        return super.canUse(p,m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (AbstractDungeon.player.hasPower(ProtectedPower.POWER_ID)) {
            for (AbstractMonster minion : MinionUtils.getMinions(AbstractDungeon.player).monsters){
                if (minion.hasPower(DefenderPower.POWER_ID)){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(minion,p,baseBlock));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(minion,p,new DefenderPower(minion,p,magicNumber)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,p,new ProtectedPower(p,p, (AbstractUndeadMonster) minion)));
                }
            }
        }
        else {
            AbstractCreature target = MinionUtils.getRandomMinion(AbstractDungeon.player,true);
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target,p,baseBlock));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,p,new DefenderPower(target,p,magicNumber)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,p,new ProtectedPower(p,p, (AbstractUndeadMonster) target)));
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}
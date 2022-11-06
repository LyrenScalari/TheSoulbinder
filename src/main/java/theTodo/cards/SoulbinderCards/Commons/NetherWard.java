package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.actions.GainSoulBarrierAction;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class NetherWard extends AbstractEasyCard {
    public final static String ID = makeID("NetherWard");
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Soul_Barrier"), BaseMod.getKeywordDescription("soulbindermod:Soul_Barrier")));
        return retVal;
    }
    public NetherWard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new GainSoulBarrierAction(p,magicNumber,false));
    }

    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(1);
    }
}
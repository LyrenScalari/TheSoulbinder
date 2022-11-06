package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class SnapFreeze extends AbstractEasyCard {
    public final static String ID = makeID("SnapFreeze");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        return retVal;
    }
    public SnapFreeze() {
        super(ID, 2, CardType.POWER , CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 4;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
    @Override
    public void upp() {
    }

}
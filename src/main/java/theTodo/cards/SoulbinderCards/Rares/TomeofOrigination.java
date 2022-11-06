package theTodo.cards.SoulbinderCards.Rares;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class TomeofOrigination extends AbstractEasyCard {
    public final static String ID = makeID("TomeofOrigination");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public TomeofOrigination() {
        super(ID, 2, CardType.POWER , CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
    @Override
    public void upp() {
    }

}
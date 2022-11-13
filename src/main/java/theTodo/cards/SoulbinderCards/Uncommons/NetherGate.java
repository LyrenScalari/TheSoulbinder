package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.SoulbarrierPower;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class NetherGate extends AbstractEasyCard {
    public final static String ID = makeID("NetherGate");
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Soul_Barrier"), BaseMod.getKeywordDescription("soulbindermod:Soul_Barrier")));
        return retVal;
    }

    public NetherGate() {
        super(ID, 2, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 6;
        secondMagic = baseSecondMagic =2;
        tags.add(SoulbinderMod.SelfExile);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SoulbarrierPower(p,magicNumber)));
        for (int i = 0; i < secondMagic ; i++) {
            this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), true));
        }
       }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
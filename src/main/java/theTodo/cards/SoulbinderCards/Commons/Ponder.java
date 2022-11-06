package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.RimePower;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Ponder extends AbstractEasyCard {
    public final static String ID = makeID("Ponder");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        return retVal;
    }
    public Ponder() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : DrawCardAction.drawnCards){
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                      addToTop(new ApplyPowerAction(mo,p,new RimePower(mo,c.costForTurn*3)));
                      isDone = true;
                    }
                }
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}
package theTodo.cards.SoulbinderCards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.BoneBurst;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class BoneSculpt extends AbstractSwappableCard{
    public final static String ID = makeID("BoneSculpt");
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tags = new ArrayList<>();
        tags.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        return tags;
    }
    public BoneSculpt (AbstractSwappableCard linkedCard) {
        super(ID, 0, CardType.SKILL,CardRarity.BASIC, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        if (linkedCard == null) {
            setLinkedCard(new BoneBurst(this));
        } else {
            setLinkedCard(linkedCard);
        }
        cardToPreview.add(1,new Skeleton());
    }
    public  BoneSculpt() {
        this(null);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(card)->true,(AbstractCard)->{
            AbstractCard.forEach(card -> {
                card.initializeDescription();
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            });
        }));
        for (int i = 0; i < magicNumber; i++){
            addToBot(new MakeTempCardInHandAction(cardToPreview.get(1).makeCopy()));
        }
    }

    public void upp() {
    }
    public void upgrade() {
        upgradeName();
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        cardToPreview.get(1).upgrade();
        initializeDescription();
        super.upgrade();
    }
}
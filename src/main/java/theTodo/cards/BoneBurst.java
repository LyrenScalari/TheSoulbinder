package theTodo.cards;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theTodo.Orbs.BonesOrb;
import theTodo.SoulbinderMod;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;
import theTodo.cards.SoulbinderCards.BoneSculpt;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;
@AutoAdd.Ignore
public class BoneBurst extends AbstractSwappableCard{
    public final static String ID = makeID("BoneBurst");
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tags = new ArrayList<>();
        tags.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        tags.addAll(super.getCustomTooltips());
        return tags;
    }
    public BoneBurst (AbstractSwappableCard linkedCard) {
        super(ID, 0, CardType.SKILL,CardRarity.BASIC, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        cardToPreview.add(new Skeleton());
        if (linkedCard == null) {
            setLinkedCard(new BoneSculpt(this));
        } else {
            setLinkedCard(linkedCard);
        }
    }
    public  BoneBurst() {
        this(null);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof BonesOrb) {
                addToTop(new EvokeSpecificOrbAction(o));
                break;
            }
        }
    }

    public void upp() {
        initializeDescription();
    }
}

package theTodo.cards.SoulbinderCards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTodo.SoulbinderMod;
import theTodo.actions.SwapCardsAction;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSwappableCard extends AbstractClickableCard {
    private AbstractGameAction action;
    private static boolean looping;

    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }

    public AbstractSwappableCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, cost, type, rarity, target);
    }

    protected void setLinkedCard(AbstractSwappableCard linkedCard) {
        if (linkedCard != null) {
            cardToPreview.add(0,linkedCard);
            linkedCard.cardToPreview.add(0,this);
        }
    }

    @Override
    public void upgrade() {
        if (cardToPreview.get(0) != null && !looping) {
            looping = true;
            cardToPreview.get(0).upgrade();
            looping = false;
        }
    }

    @Override
    public void onRightClick() {
        if (canSwap() && action == null &&  cardToPreview.get(0) != null) {
            CardCrawlGame.sound.play("CARD_SELECT", 0.1F);
            if (AbstractDungeon.player.hand.contains(this)){
                action = new SwapCardsAction(this, cardToPreview.get(0),(AbstractDungeon.player.hand));
            }
            this.addToTop(action);
        }
    }

    @Override
    public void update() {
        super.update();
        if (action != null && action.isDone) {
            action = null;
        }
    }

    public boolean canSwap() {
        return true;
    }

    public void onSwapOut() {}

    public void onSwapIn() {}
}

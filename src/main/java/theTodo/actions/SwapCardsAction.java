package theTodo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;

public class SwapCardsAction extends AbstractGameAction {
    private AbstractCard toReplace;
    private AbstractCard newCard;
    private CardGroup currentlocation;

    public SwapCardsAction(AbstractCard toReplace, AbstractCard newCard, CardGroup location) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;
        this.toReplace = toReplace;
        this.newCard = newCard;
        currentlocation = location;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int index = 0;
        boolean found = false;
        boolean hand = false;
        boolean draw = false;
        boolean discard = false;
        boolean exhaust = false;
        if (currentlocation == AbstractDungeon.player.hand){
            for (AbstractCard card : p.hand.group)
            {
                if (card == toReplace) {
                    hand = true;
                    found = true;
                    break;
                }
                index++;
            }
        }
        if (currentlocation == AbstractDungeon.player.drawPile){
            for (AbstractCard card : p.drawPile.group)
            {
                if (card == toReplace) {
                    draw = true;
                    found = true;
                    break;
                }
                index++;
            }
        }
        if (currentlocation == AbstractDungeon.player.discardPile){
            for (AbstractCard card : p.discardPile.group)
            {
                if (card == toReplace) {
                    discard = true;
                    found = true;
                    break;
                }
                index++;
            }
        }
        if (currentlocation == AbstractDungeon.player.exhaustPile){
            for (AbstractCard card : p.exhaustPile.group)
            {
                if (card == toReplace) {
                    exhaust = true;
                    found = true;
                    break;
                }
                index++;
            }
        }
        if(found && toReplace != null) {
            if (toReplace instanceof AbstractSwappableCard && newCard instanceof AbstractSwappableCard) {
                ((AbstractSwappableCard) toReplace).onSwapOut();
                ((AbstractSwappableCard) newCard).onSwapIn();
            }
            newCard.cardsToPreview = toReplace.makeStatEquivalentCopy();
            newCard.applyPowers();
            newCard.cardsToPreview.applyPowers();
            if (AbstractDungeon.player.hoveredCard == toReplace) {
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.cardQueue.removeIf(q -> q.card == toReplace);
            this.addToTop(new UpdateAfterTransformAction(newCard));
            if (hand){
                this.addToTop(new TransformCardInHandAction(index, newCard));
            }
        }
        this.isDone = true;
    }
}

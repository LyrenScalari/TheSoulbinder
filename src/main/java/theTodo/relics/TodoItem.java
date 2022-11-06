package theTodo.relics;

import basemod.helpers.CardModifierManager;
import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theTodo.SoulbinderMod;
import theTodo.TheSoulbinder;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;

import static theTodo.SoulbinderMod.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");
    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheSoulbinder.Enums.SOULBINDER_COLOR);
    }
    public void atTurnStart() {
        usedUp = false;
    }
    public void onExhaust(AbstractCard card) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !card.hasTag(SoulbinderMod.Undead) && !usedUp) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard card = new Skeleton().makeStatEquivalentCopy();
                    AbstractDungeon.player.limbo.addToBottom(card);
                    CardModifierManager.addModifier(card, new EntombedMod());
                    card.initializeDescription();
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    isDone = true;
                }
            });
            usedUp = true;
        }

    }
}

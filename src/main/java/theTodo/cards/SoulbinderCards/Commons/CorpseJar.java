package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cardmods.EntombedMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.SoulbinderCards.Tokens.CorpseSpider;

import static theTodo.SoulbinderMod.makeID;

public class CorpseJar extends AbstractEasyCard {
    public final static String ID = makeID("CorpseJar");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public CorpseJar() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        cardsToPreview = new CorpseSpider();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard card = cardsToPreview.makeStatEquivalentCopy();
                AbstractDungeon.player.limbo.addToBottom(card);
                CardModifierManager.addModifier(card,new EntombedMod());
                card.initializeDescription();
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeDamage(3);
    }
}
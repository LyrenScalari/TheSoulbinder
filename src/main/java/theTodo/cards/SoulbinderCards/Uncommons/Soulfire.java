package theTodo.cards.SoulbinderCards.Uncommons;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.Patches.SoulsBarrierPatches.SoulsField;
import theTodo.SoulbinderMod;
import theTodo.cards.AbstractEasyCard;

import static theTodo.SoulbinderMod.makeID;

public class Soulfire extends AbstractEasyCard {
    public final static String ID = makeID("Soulfire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    public Soulfire() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 10;
        tags.add(SoulbinderMod.SelfExile);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage+ SoulsField.Souls.get(p))));
    }

    public void upp() {
        upgradeDamage(4);
    }
}
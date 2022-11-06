package theTodo.cards.SoulbinderCards.Tokens;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.SoulbinderMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.TypeEnergyHelper;

import static theTodo.SoulbinderMod.makeID;

public class PartTheVeil extends AbstractEasyCard {
    public final static String ID = makeID("PartTheVeil");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    public PartTheVeil() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF,CardColor.COLORLESS);
        magicNumber = baseMagicNumber = 5;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
        tags.add(SoulbinderMod.SelfExile);
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.currentHealth <= energyCosts.get(TypeEnergyHelper.Mana.Health)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.isInAutoplay){
            addToBot(new DamageAction(p,new DamageInfo(p, secondMagic, DamageInfo.DamageType.HP_LOSS)));
        }
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                secondMagic +=1;
                baseSecondMagic += 1;
                energyCosts.put(TypeEnergyHelper.Mana.Health,secondMagic);
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}
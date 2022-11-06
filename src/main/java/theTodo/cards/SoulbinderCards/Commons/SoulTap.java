package theTodo.cards.SoulbinderCards.Commons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theTodo.Patches.SoulsBarrierPatches.SoulsField;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.NetherworldResonancePower;
import theTodo.util.Wiz;

import static theTodo.SoulbinderMod.makeID;

public class SoulTap extends AbstractEasyCard {
    public final static String ID = makeID("SoulTap");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    public SoulTap() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 3;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        Wiz.applyToSelfTemp(new NetherworldResonancePower(p,SoulsField.Souls.get(p)));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}

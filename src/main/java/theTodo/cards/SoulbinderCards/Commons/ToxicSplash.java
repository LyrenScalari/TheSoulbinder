package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.Icons.VenomIcon;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.FrostDamage;
import theTodo.util.VenomDamage;

import static theTodo.SoulbinderMod.makeID;

public class ToxicSplash extends AbstractEasyCard {
    public final static String ID = makeID("ToxicSplash");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public ToxicSplash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        DamageModifierManager.addModifier(this, new VenomDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, VenomIcon.get()));
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
    }
    @Override
    public void upp() {
        upgradeDamage(2);
    }

}
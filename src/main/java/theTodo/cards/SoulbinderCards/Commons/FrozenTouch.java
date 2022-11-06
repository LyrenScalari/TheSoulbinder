package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theTodo.Icons.FrostIcon;
import theTodo.Icons.VenomIcon;
import theTodo.SoulbinderMod;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.FrostDamage;
import theTodo.util.VenomDamage;
import theTodo.util.Wiz;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class FrozenTouch extends AbstractEasyCard {
    public final static String ID = makeID("FrozenTouch");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public FrozenTouch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        DamageModifierManager.addModifier(this, new FrostDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FrostIcon.get()));
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }
    @Override
    public void upp() {
        upgradeDamage(2);
    }

}
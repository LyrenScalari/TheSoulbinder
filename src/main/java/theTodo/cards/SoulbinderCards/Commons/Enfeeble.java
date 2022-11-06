package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTodo.Icons.Elements.FireIcon;
import theTodo.Icons.VenomIcon;
import theTodo.Icons.VulnerableIcon;
import theTodo.Icons.WeakIcon;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.RimePower;
import theTodo.util.VenomDamage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Enfeeble extends AbstractEasyCard {
    public final static String ID = makeID("Enfeeble");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:WeakIcon] " + TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));
        return retVal;
    }
    public Enfeeble() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 2;
        damage = baseDamage = 8;
        exhaust = true;
        DamageModifierManager.addModifier(this, new VenomDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, VenomIcon.get()));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC, WeakIcon.get()));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        addToTop(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
    }

    public void upp() {
        upgradeDamage(3);
    }
}
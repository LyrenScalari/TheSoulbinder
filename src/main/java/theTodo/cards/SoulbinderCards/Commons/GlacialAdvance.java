package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theTodo.Icons.FrostIcon;
import theTodo.Icons.VenomIcon;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.SoulbinderCards.Tokens.IcePhantom;
import theTodo.util.FrostDamage;

import javax.swing.*;

import static theTodo.SoulbinderMod.makeID;

public class GlacialAdvance extends AbstractEasyCard {
    public final static String ID = makeID("GlacialAdvance");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public GlacialAdvance() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 10;
        DamageModifierManager.addModifier(this, new FrostDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FrostIcon.get()));
        cardsToPreview = new IcePhantom();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new VFXAction(new BlizzardEffect(damage,false)));
        addToBot(new VFXAction(new BlizzardEffect(damage,false)));
        allDmg(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(),1,true,false));
    }
    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
        cardsToPreview.upgrade();
        upgradeDamage(2);
    }

}
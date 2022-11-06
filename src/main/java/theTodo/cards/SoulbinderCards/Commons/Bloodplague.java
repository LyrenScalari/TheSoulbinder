package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.Icons.VenomIcon;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.TypeEnergyHelper;
import theTodo.util.VenomDamage;

import static theTodo.SoulbinderMod.makeID;

public class Bloodplague extends AbstractEasyCard {
    public final static String ID = makeID("Bloodplague");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public Bloodplague() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 2;
        energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
        baseDamage = 5;
        DamageModifierManager.addModifier(this, new VenomDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, VenomIcon.get()));
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.currentHealth < energyCosts.get(TypeEnergyHelper.Mana.Health)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.isInAutoplay){
            addToBot(new DamageAction(p,new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS)));
        }
        for (int i = 0; i < magicNumber ; i++){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p,baseDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                magicNumber +=1;
                baseMagicNumber += 1;
                energyCosts.put(TypeEnergyHelper.Mana.Health,magicNumber);
                isDone = true;
            }
        });
    }
    @Override
    public void upp() {
        upgradeDamage(2);
    }

}
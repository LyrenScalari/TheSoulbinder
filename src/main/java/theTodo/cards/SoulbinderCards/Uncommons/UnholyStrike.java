package theTodo.cards.SoulbinderCards.Uncommons;

import basemod.BaseMod;
import basemod.devcommands.power.Power;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.SyphonDamage;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class UnholyStrike extends AbstractEasyCard {
    public final static String ID = makeID("UnholyStrike");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));
        return retVal;
    }
    public UnholyStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 9;
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 2;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX,m.hb.cY)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        if (!AbstractDungeon.player.exhaustPile.group.isEmpty()) {
            if (AbstractDungeon.player.exhaustPile.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(AbstractDungeon.player.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        if (!CardModifierManager.hasModifier(card, "EntombedMod")) {
                            CardModifierManager.addModifier(card, new PurgeMod());
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                            AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                        } else {
                            card.triggerOnExhaust();
                        }

                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard copy = cardsToPreview.makeStatEquivalentCopy();
                        CardModifierManager.addModifier(copy, new PurgeMod());
                        if (!CardModifierManager.hasModifier(AbstractDungeon.player.exhaustPile.getTopCard(), "EntombedMod")) {
                            CardModifierManager.addModifier(AbstractDungeon.player.exhaustPile.getTopCard(), new PurgeMod());
                            AbstractDungeon.player.exhaustPile.getTopCard().stopGlowing();
                            AbstractDungeon.player.exhaustPile.getTopCard().unhover();
                            AbstractDungeon.player.exhaustPile.getTopCard().unfadeOut();
                            AbstractDungeon.player.exhaustPile.moveToDiscardPile(AbstractDungeon.player.exhaustPile.getTopCard());
                        } else {
                            copy.triggerOnExhaust();
                        }
                        isDone = true;
                    }
                });
            }
        }
        addToBot(new DrawCardAction(magicNumber));
        if (m.hasPower(PoisonPower.POWER_ID)){
            addToBot(new ApplyPowerAction(m,p,new WeakPower(m,secondMagic,false)));
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}
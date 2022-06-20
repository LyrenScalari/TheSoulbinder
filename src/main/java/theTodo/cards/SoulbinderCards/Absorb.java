package theTodo.cards.SoulbinderCards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Absorb extends AbstractEasyCard {
    public final static String ID = makeID("Absorb");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:SyphonIcon] " + BaseMod.getKeywordTitle("soulbindermod:Syphon"), BaseMod.getKeywordDescription("soulbindermod:Syphon")));
        return retVal;
    }
    public Absorb() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 8;
        magicNumber = baseMagicNumber = 3;
        secondMagic = baseSecondMagic = 1;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p,new DamageInfo(p,magicNumber, DamageInfo.DamageType.HP_LOSS)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        addToBot(new HealAction(p,p,damage));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                magicNumber +=secondMagic;
                baseMagicNumber += secondMagic;
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeDamage(3);
    }
}
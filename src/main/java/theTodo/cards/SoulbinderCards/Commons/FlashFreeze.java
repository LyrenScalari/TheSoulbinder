package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theTodo.Icons.RimeIcon;
import theTodo.Icons.VulnerableIcon;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.RimePower;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class FlashFreeze extends AbstractEasyCard {
    public final static String ID = makeID("FlashFreeze");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:VulnerableIcon] " + TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        return retVal;
    }
    public FlashFreeze() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 4;
        secondMagic = baseSecondMagic = 2;
        exhaust = true;
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC2, VulnerableIcon.get()));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC, RimeIcon.get()));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ApplyPowerAction(m,p,new RimePower(m,magicNumber)));
        addToTop(new ApplyPowerAction(m,p,new VulnerablePower(m,secondMagic,false)));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}
package theTodo.cards.SoulbinderCards.Tokens;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTodo.Icons.BlockIcon;
import theTodo.Icons.VulnerableIcon;
import theTodo.Icons.WeakIcon;
import theTodo.Orbs.BonesOrb;
import theTodo.SoulbinderMod;
import theTodo.actions.CoalesceAction;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.Wiz;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Skeleton extends AbstractEasyCard{
    public final static String ID = makeID("Skeleton");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public Skeleton() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 4;
        magicNumber = baseMagicNumber = 1;
        block = baseBlock = 4;
        tags.add(SoulbinderMod.Undead);
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, BlockIcon.get()));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC, WeakIcon.get()));
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("soulbindermod:Undead"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        retVal.add(new TooltipInfo("[soulbindermod:WeakIcon] " + TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));
        return retVal;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
        blck();
    }
    public void triggerOnExhaust() {
        addToBot(new CoalesceAction(new BonesOrb()));
    }
    @Override
    public void upp() {
        upgradeBlock(1);
        upgradeDamage(1);
        upgradeMagicNumber(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
    }

}
package theTodo.cards.SoulbinderCards.Tokens;

import basemod.BaseMod;
import basemod.devcommands.draw.Draw;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTodo.Orbs.BonesOrb;
import theTodo.Orbs.FangsOrb;
import theTodo.SoulbinderMod;
import theTodo.actions.CoalesceAction;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class FleshGolem extends AbstractEasyCard {
    public final static String ID = makeID("FleshGolem");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public FleshGolem() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY,CardColor.COLORLESS);
        magicNumber = baseMagicNumber = 1;
        block = baseBlock = 8;
        tags.add(SoulbinderMod.Undead);
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
        return retVal;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new DrawCardAction(magicNumber));
    }
    public void triggerOnExhaust() {
        addToBot(new CoalesceAction(new FangsOrb()));
    }
    @Override
    public void upp() {
        upgradeBlock(3);
    }

}
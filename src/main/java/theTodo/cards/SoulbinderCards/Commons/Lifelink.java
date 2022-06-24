package theTodo.cards.SoulbinderCards.Commons;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hlysine.friendlymonsters.utils.MinionUtils;
import theTodo.Minions.AbstractUndeadMonster;
import theTodo.cards.AbstractEasyCard;
import theTodo.powers.SoulbinderPowerz.DefenderPower;
import theTodo.powers.SoulbinderPowerz.LifeLinkPower;
import theTodo.powers.SoulbinderPowerz.ProtectedPower;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Lifelink extends AbstractEasyCard {
    public final static String ID = makeID("Lifelink");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        retVal.add(new TooltipInfo("[soulbindermod:SyphonIcon] " + BaseMod.getKeywordTitle("soulbindermod:Syphon"), BaseMod.getKeywordDescription("soulbindermod:Syphon")));
        return retVal;
    }
    public Lifelink () {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseBlock = block = 4;
        baseMagicNumber = magicNumber = 4;
        secondMagic = baseSecondMagic = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p,new DamageInfo(p,magicNumber, DamageInfo.DamageType.HP_LOSS)));
        addToBot(new ApplyPowerAction(p,p,new LifeLinkPower(p,p,secondMagic,m)));
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
        upgradeBaseCost(0);
    }
}
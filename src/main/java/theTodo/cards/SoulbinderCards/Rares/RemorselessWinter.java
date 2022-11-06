package theTodo.cards.SoulbinderCards.Rares;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.FrostDamage;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class RemorselessWinter extends AbstractEasyCard {
    public final static String ID = makeID("RemorselessWinter");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Frost"), BaseMod.getKeywordDescription("soulbindermod:Frost")));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("soulbindermod:Rime"), BaseMod.getKeywordDescription("soulbindermod:Rime")));
        return retVal;
    }
    public RemorselessWinter() {
        super(ID, 2, CardType.POWER , CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        CommonKeywordIconsField.useIcons.set(this,true);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
    @Override
    public void upp() {
    }

}
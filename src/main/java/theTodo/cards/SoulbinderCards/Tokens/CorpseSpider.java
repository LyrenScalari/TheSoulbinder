package theTodo.cards.SoulbinderCards.Tokens;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theTodo.Orbs.BileOrb;
import theTodo.Orbs.BonesOrb;
import theTodo.SoulbinderMod;
import theTodo.actions.CoalesceAction;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.VenomDamage;
import theTodo.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class CorpseSpider extends AbstractEasyCard {
    public final static String ID = makeID("CorpseSpider");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public CorpseSpider() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 4;
        magicNumber = baseMagicNumber = 4;
        tags.add(SoulbinderMod.Undead);
        DamageModifierManager.addModifier(this, new VenomDamage(true));
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("soulbindermod:Undead"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }
    public void triggerOnExhaust() {
        addToBot(new CoalesceAction(new BileOrb()));
    }
    @Override
    public void upp() {
        upgradeMagicNumber(3);
        upgradeDamage(2);
    }

}
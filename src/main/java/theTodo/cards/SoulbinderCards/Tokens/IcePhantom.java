package theTodo.cards.SoulbinderCards.Tokens;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theTodo.Orbs.BonesOrb;
import theTodo.Orbs.EctoOrb;
import theTodo.SoulbinderMod;
import theTodo.actions.CoalesceAction;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.FrostDamage;
import theTodo.util.VenomDamage;
import theTodo.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class IcePhantom extends AbstractEasyCard {
    public final static String ID = makeID("IcePhantom");
    public boolean BranchUpgraded =false;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public IcePhantom() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY,CardColor.COLORLESS);
        baseDamage = 4;
        block = baseBlock = 4;
        tags.add(SoulbinderMod.Undead);
        DamageModifierManager.addModifier(this, new FrostDamage(true));
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("soulbindermod:Undead"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new VFXAction(new BlizzardEffect(baseDamage,false)));
        for (AbstractMonster m0 : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!m0.isDeadOrEscaped()){
                addToBot(new DamageAction(m0,new DamageInfo(p,baseDamage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }
    public void triggerOnExhaust() {
        addToBot(new CoalesceAction(new EctoOrb()));
    }
    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeDamage(3);
    }

}
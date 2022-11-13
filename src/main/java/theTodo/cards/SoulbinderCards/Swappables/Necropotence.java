package theTodo.cards.SoulbinderCards.Swappables;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theTodo.Orbs.AbstractCoalesceOrb;
import theTodo.Orbs.FangsOrb;
import theTodo.SoulbinderMod;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;
import theTodo.cards.SoulbinderCards.Commons.CrimsonHomunculi;
import theTodo.cards.SoulbinderCards.Commons.Necroblast;
import theTodo.powers.SoulbinderPowerz.NetherworldResonancePower;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;
@AutoAdd.Ignore
public class Necropotence extends AbstractSwappableCard {
    public final static String ID = makeID("Necropotence");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tags = new ArrayList<>();
        tags.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        return tags;
    }
    public Necropotence() {
        this(null);
    }
    public Necropotence (AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.SKILL,CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        if (linkedCard == null) {
            setLinkedCard(new Necroblast(this));
        } else {
            setLinkedCard(linkedCard);
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof AbstractCoalesceOrb) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (upgraded){
                            addToTop(new EvokeSpecificOrbAction(o));
                        } else {
                            p.orbs.remove(o);
                            p.maxOrbs -= 1;
                        }
                        isDone = true;
                    }
                });
                addToBot(new ApplyPowerAction(p,p,new NetherworldResonancePower(p,magicNumber)));
                break;
            }
        }
    }

    public void upp() {
    }
    public void upgrade() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        cardToPreview.get(0).upgrade();
        initializeDescription();
        super.upgrade();
    }
}


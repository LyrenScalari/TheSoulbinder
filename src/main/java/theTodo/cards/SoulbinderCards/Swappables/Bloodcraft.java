package theTodo.cards.SoulbinderCards.Swappables;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theTodo.Orbs.BonesOrb;
import theTodo.Orbs.FangsOrb;
import theTodo.SoulbinderMod;
import theTodo.cards.BoneBurst;
import theTodo.cards.SoulbinderCards.AbstractSwappableCard;
import theTodo.cards.SoulbinderCards.Commons.CrimsonHomunculi;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;
import theTodo.util.TypeEnergyHelper;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;
@AutoAdd.Ignore
public class Bloodcraft extends AbstractSwappableCard {
    public final static String ID = makeID("Bloodcraft");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tags = new ArrayList<>();
        tags.add(new TooltipInfo(BaseMod.getKeywordTitle(SoulbinderMod.modID+":Swappable"),BaseMod.getKeywordDescription(SoulbinderMod.modID+":Swappable")));
        return tags;
    }
    public Bloodcraft() {
        this(null);
    }
    public Bloodcraft (AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL,CardRarity.BASIC, CardTarget.SELF);
        exhaust = true;
        if (linkedCard == null) {
            setLinkedCard(new CrimsonHomunculi(this));
        } else {
            setLinkedCard(linkedCard);
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof FangsOrb) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        o.onEvoke();
                        isDone = true;
                    }
                });
                if (upgraded) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            o.onEvoke();
                            isDone = true;
                        }
                    });
                }
                addToBot(new EvokeSpecificOrbAction(o));
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

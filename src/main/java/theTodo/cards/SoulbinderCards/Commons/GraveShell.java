package theTodo.cards.SoulbinderCards.Commons;

import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.Icons.BlockIcon;
import theTodo.SoulbinderMod;
import theTodo.cardmods.AddIconToDescriptionMod;
import theTodo.cardmods.EntombedMod;
import theTodo.cardmods.PurgeMod;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class GraveShell extends AbstractEasyCard {
    public final static String ID = makeID("GraveShell");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        return retVal;
    }
    public GraveShell() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        magicNumber = baseMagicNumber = 1;
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, BlockIcon.get()));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (!AbstractDungeon.player.exhaustPile.group.isEmpty()) {
            if (AbstractDungeon.player.drawPile.size() > magicNumber) {
                addToBot(new SelectCardsCenteredAction(AbstractDungeon.player.exhaustPile.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], false, (card) -> true, (AbstractCard) -> {
                    AbstractCard.forEach(card -> {
                        if (!CardModifierManager.hasModifier(card, "EntombedMod")) {
                            CardModifierManager.addModifier(card, new PurgeMod());
                            card.stopGlowing();
                            card.unhover();
                            card.unfadeOut();
                        }
                        if (card.hasTag(SoulbinderMod.Undead)){
                            blck();
                        }
                        AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                    });
                }));
            } else {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (!CardModifierManager.hasModifier(AbstractDungeon.player.exhaustPile.getTopCard(), "EntombedMod")) {
                            CardModifierManager.addModifier(AbstractDungeon.player.exhaustPile.getTopCard(), new PurgeMod());
                            AbstractDungeon.player.exhaustPile.getTopCard().stopGlowing();
                            AbstractDungeon.player.exhaustPile.getTopCard().unhover();
                            AbstractDungeon.player.exhaustPile.getTopCard().unfadeOut();
                        }
                        if (AbstractDungeon.player.exhaustPile.getTopCard().hasTag(SoulbinderMod.Undead)){
                            blck();
                        }
                        AbstractDungeon.player.exhaustPile.moveToDiscardPile(AbstractDungeon.player.exhaustPile.getTopCard());

                        isDone = true;
                    }
                });
            }
        }
    }

    public void upp() {
        upgradeBlock(3);
    }
}
package theTodo.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static theTodo.SoulbinderMod.makeID;

public class Defend extends AbstractEasyCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , ,  5, 3, , 
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[soulbindermod:BlockIcon] " + TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        return retVal;
    }
    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}
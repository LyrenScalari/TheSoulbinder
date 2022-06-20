package theTodo.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTodo.Patches.SoulsField;
import theTodo.TheSoulbinder;

import static theTodo.SoulbinderMod.makeID;

public class LichesPhylactery extends AbstractEasyRelic {
    public static final String ID = makeID("LichesPhylactery");

    public LichesPhylactery() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL, TheSoulbinder.Enums.SOULBINDER_COLOR);
    }

}

package theTodo.relics;

import theTodo.Minions.Skeleton;
import theTodo.TheSoulbinder;
import theTodo.actions.SummonUndeadAction;

import static theTodo.SoulbinderMod.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheSoulbinder.Enums.SOULBINDER_COLOR);
    }

    public void atBattleStart() {
        addToBot(new SummonUndeadAction(new Skeleton(-700,-55)));
    }
}

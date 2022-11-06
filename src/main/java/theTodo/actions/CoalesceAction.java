package theTodo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class CoalesceAction extends AbstractGameAction
{
    private AbstractOrb gem;
    boolean chipOrb = false;
    private AbstractPlayer p;
    int maxSize = -1;
    public CoalesceAction(AbstractOrb newOrbType)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = newOrbType;
        p = AbstractDungeon.player;
    }

    public void update()
    {
        addToTop(new ChannelAction(gem, false));
        p.maxOrbs += amount;

        int i;
        for(i = 0; i < amount; ++i) {
            p.orbs.add(new EmptyOrbSlot());
        }

        for(i = 0; i < p.orbs.size(); ++i) {
            ((AbstractOrb)p.orbs.get(i)).setSlot(i,p.maxOrbs);
        }
    }
}

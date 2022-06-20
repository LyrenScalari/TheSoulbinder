package theTodo.Minions;


import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;

public abstract class AbstractUndeadMonster extends AbstractFriendlyMonster {
    public int  basedamage = -1;
    public int baseblock = -1;
    public int  basemagic = -1;
    public int basemhp = -1;
    public AbstractUndeadMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }
    public void addMoves(){

    }
}

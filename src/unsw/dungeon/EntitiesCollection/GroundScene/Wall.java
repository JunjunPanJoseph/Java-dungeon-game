package unsw.dungeon.EntitiesCollection.GroundScene;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.Interfaces.Enterable;

/**
 * Wall
 * @author ASUS
 *
 */
public class Wall extends Entity implements Enterable {
    /**
     * 
     * @param dungeon
     * @param x
     * @param y
     */
	public Wall(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.imageProperty().set(dungeon.entityImage().getWallImage());
    }

	@Override
	public boolean checkEnter(Entity e) {
		return false;
	}
	@Override
	public boolean checkLeave(Entity e) {
		return false;
	}
	@Override
	public void enter(Entity e) {}
	@Override
	public void leave(Entity e) {}
    
}

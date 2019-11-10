package unsw.dungeon.EntitiesCollection.GroundScene;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.Interfaces.Enterable;
/**
 * Switch
 * @author ASUS
 *
 */
public class Switch extends Entity implements Enterable {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Switch(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.imageProperty().set(dungeon.entityImage().getSwitchImage());
	}
	@Override
	public void enter(Entity e) {
		if (e instanceof Boulder) {
			getDungeon().getGoals().switchesSub();
		}
	}
	@Override
	public void leave(Entity e) {
		if (e instanceof Boulder) {
			getDungeon().getGoals().switchesAdd();
		}
	}
	@Override
	public boolean checkEnter(Entity e) {
		return true;
	}
	@Override
	public boolean checkLeave(Entity e) {
		return true;
	}

}

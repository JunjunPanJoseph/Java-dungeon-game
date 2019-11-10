package unsw.dungeon.EntitiesCollection.GroundScene;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.Interfaces.Enterable;
/**
 * 
 * @author ASUS
 *
 */
public class Exit extends Entity implements Enterable {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Exit(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.imageProperty().set(dungeon.entityImage().getExitImage());
	}

	@Override
	public void enter(Entity e) {
		if (e instanceof Player)
			getDungeon().getGoals().exitSub();
	}
	@Override
	public void leave(Entity e) {
		if (e instanceof Player)
			getDungeon().getGoals().exitAdd();
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

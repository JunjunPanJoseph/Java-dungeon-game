package unsw.dungeon.EntitiesCollection.Character.Enemy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
/**
 * Hound
 * @author ASUS
 *
 */
public class EnemyHound extends Enemy {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public EnemyHound(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, 3);
		this.imageProperty().set(dungeon.entityImage().getEnemyHoundImage());
		setActionPeriod(2);
	}
	@Override
	public void attack(Character enemy) {
		enemy.damage(this, 1);
	}
}

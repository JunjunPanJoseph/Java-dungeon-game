package unsw.dungeon.EntitiesCollection.Character.Enemy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.Strategy.EnemyRedGenomeStrategy;

/**
 * Red gnome
 * @author ASUS
 *
 */
public class RedGnome extends Enemy {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public RedGnome(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, 10);
		this.imageProperty().set(dungeon.entityImage().getEnemyRedGnomeImage());
		setActionPeriod(20);
		this.setStrategy(new EnemyRedGenomeStrategy(this));
	}
	@Override
	public void attack(Character enemy) {
		enemy.damage(this, 1);
	}
}

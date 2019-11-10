package unsw.dungeon.EntitiesCollection.Character.Enemy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
/**
 * Enemy elf
 * @author ASUS
 *
 */
public class EnemyElf extends Enemy  {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public EnemyElf(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, 1);
		this.imageProperty().set(dungeon.entityImage().getEnemyElfImage());
		setActionPeriod(6);

	}
	@Override
	public void attack(Character enemy) {
		enemy.damage(this, 1);
	}
}

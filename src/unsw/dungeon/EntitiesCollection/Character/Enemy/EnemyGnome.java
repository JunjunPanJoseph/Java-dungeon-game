package unsw.dungeon.EntitiesCollection.Character.Enemy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.BombLit;
import unsw.dungeon.EntitiesCollection.Character.Strategy.EnemyGenomeStrategy;
/**
 * Genome
 * @author ASUS
 *
 */

public class EnemyGnome extends Enemy  {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public EnemyGnome(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, 20);
		this.imageProperty().set(dungeon.entityImage().getEnemyGenomeImage());
		this.setStrategy(new EnemyGenomeStrategy(this));
		setActionPeriod(6);
	}
	@Override
	/**
	 * Attack
	 */
	public void attack(Character enemy) {
		enemy.damage(this, 1);
	}
	@Override
	/**
	 * Be damaged
	 */
	public void damage(Character enemy, int damage) {
		if (enemy instanceof BombLit) {
		} else {
			super.damage(enemy, damage);
		}
	}
	@Override
	/**
	 * Switching behavior
	 */
	public void behavior(int frameNo) {
		super.behavior(frameNo);
	}
}

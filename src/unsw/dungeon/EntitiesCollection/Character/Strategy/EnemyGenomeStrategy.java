package unsw.dungeon.EntitiesCollection.Character.Strategy;

import java.util.Random;

import unsw.dungeon.EntitiesCollection.Character.Enemy.Enemy;
import unsw.dungeon.Interfaces.BehaviorStrategy;
/**
 * Strategy for genome.
 * @author ASUS
 *
 */
public class EnemyGenomeStrategy implements BehaviorStrategy {
	//private Enemy enemy;
	private MoveStrategy moveStrategy;
	private AttackStrategy attackStrategy;
	private boolean activated;
	/**
	 * 
	 * @param enemy
	 */
	public EnemyGenomeStrategy(Enemy enemy){
		//this.enemy = enemy;
		moveStrategy = new MoveStrategy(enemy.getDungeon(), enemy);
		attackStrategy = new AttackStrategy(enemy.getDungeon(), enemy);
		this.activated = false;
	}
	@Override
	public void behavior(int frameNo) {
		int distToPlayer = moveStrategy.toPlayer(0, 0);
		if (distToPlayer < 10) {
			activated = true;
		}
		if (!activated) {
			return;
		}
		Random random = new Random();
		if (distToPlayer < 7) {
			switch (random.nextInt(5)) {
			case 0:
				attackStrategy.bomberSickleAttack(7, 100);
				break;
			case 1:
				attackStrategy.summonFireBouderAttack(2, false, 1, 1, 3);
			default:
				moveStrategy.moveToPlayerBehavior();
				break;
			}
		} else {
			switch (random.nextInt(12)) {
			case 0:
				attackStrategy.summonHoundStrategy(10, 100);
				break;
			case 1:
				attackStrategy.summonFireBouderAttack(10, true, 150, 7, 7);
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				attackStrategy.summonFireBouderAttack(2, false, 1, 1, 3);
			default:
				moveStrategy.moveAwayFromPlayerBehavior();
			}
		}
		
	}

}

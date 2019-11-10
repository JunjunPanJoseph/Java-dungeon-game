package unsw.dungeon.EntitiesCollection.Character.Strategy;

import unsw.dungeon.EntitiesCollection.Character.Enemy.Enemy;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.Interfaces.BehaviorStrategy;
/**
 * Move:if invicible - move away from player
 *  otherwise, move to player
 * @author ASUS
 *
 */
public class EnemyMoveStrategy implements BehaviorStrategy {
	private Enemy enemy;
	private MoveStrategy moveStrategy;
	/**
	 * 
	 * @param enemy
	 */
	public EnemyMoveStrategy(Enemy enemy){
		this.enemy = enemy;
		moveStrategy = new MoveStrategy(enemy.getDungeon(), enemy);
	}
	@Override
	/**
	 * 
	 */
	public void behavior(int frameNo) {
		Player player = enemy.getDungeon().getPlayer();
		if (player.getInvincibleTime() > 0) {
			moveStrategy.moveAwayFromPlayerBehavior();
		} else {
			moveStrategy.moveToPlayerBehavior();
		}
	}
	/**
	 * Get distance to player
	 * @return
	 */
	public int distToPlayer() {
		 return moveStrategy.toPlayer(0, 0);
	}

}

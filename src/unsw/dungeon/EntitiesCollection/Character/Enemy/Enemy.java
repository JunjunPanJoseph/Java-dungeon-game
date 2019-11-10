package unsw.dungeon.EntitiesCollection.Character.Enemy;


import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Character.Strategy.EnemyMoveStrategy;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
import unsw.dungeon.Interfaces.BehaviorStrategy;
import unsw.dungeon.Interfaces.Collidable;
import unsw.dungeon.Interfaces.Enterable;
import unsw.dungeon.animation.EntityAnimation;
/**
 * Enemy
 * @author ASUS
 *
 */
public abstract class Enemy extends CharacterWithTimeframeBehavior implements Collidable, Enterable{

	private boolean dead;
	private BehaviorStrategy strategy;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param hp
	 */
	public Enemy(Dungeon dungeon, int x, int y, int hp) {
		super(dungeon, x, y, hp);
		strategy = new EnemyMoveStrategy(this);
		dead = false;
	}
	
	/**
	 * @return the strategy
	 */
	public BehaviorStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(BehaviorStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	/**
	 * Do behavior per frame.
	 */
	public void behavior(int frameNo) {
		strategy.behavior(frameNo);
	}
	@Override
	/**
	 * Get damaged
	 */
	public void damage(Character enemy, int damage) {
		if (!(enemy instanceof Enemy)) {
			System.out.println("Damaged");
			this.setLastDamageTarget(enemy);
			Hp().set(getHp() - damage);
		}
	}
	@Override
	/**
	 * Collide with player
	 */
	public void collide(Entity e) {
		if (e instanceof Player) {
			attack((Player) e);
		}
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
	@Override
	/**
	 * Die: goals.enemies --
	 */
	public boolean die(int oldHpValue) {
		if (dead) {
			return true;
		}
		System.out.println("Enemy die: x " + getX() + " y "+ getY() );
		dungeon.getGoals().enemiesSub();
		dead = true;
		//Dying animation...bomb
		EntityAnimation bombLitAnimation = dungeon.entityAnimation().getBombExploding().
				setupEntityAnimation(dungeon, 144, getX(), getY(), Direction.Down);
		bombLitAnimation.setStartCount(7);
		bombLitAnimation.startTimeline();
		return super.die(oldHpValue);
	}
}

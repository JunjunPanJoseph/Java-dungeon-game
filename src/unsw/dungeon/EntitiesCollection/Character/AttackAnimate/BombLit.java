package unsw.dungeon.EntitiesCollection.Character.AttackAnimate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
import unsw.dungeon.Interfaces.Enterable;
import unsw.dungeon.animation.EntityAnimation;
/**
 * Bomblit
 * @author ASUS
 *
 */
public class BombLit extends CharacterWithTimeframeBehavior implements Enterable{
	private int state;
	private int damage;
	EntityAnimation bombLitAnimation;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public BombLit(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, 1);
		setActionPeriod(6);
		this.state = 0;
		this.damage = 99999;
		this.bombLitAnimation = dungeon.entityAnimation().getBombExploding().
				setupEntityAnimation(dungeon, 144, x, y, Direction.Down);
		bombLitAnimation.startTimeline();
		
	}
	
	@Override
	/**
	 * Change this state counter until explode.
	 */
	public void behavior(int frameNo) {
		state++;
		if (state == 3) {
			explode();
		}
		if (state == 4) {
			die(0);
		}
	}
	/**
	 * Set current state to explode now.
	 * For special effect.
	 */
	public void explodeNow() {
		this.state = 2;
		bombLitAnimation.setStartCount(10);
	}
	@Override
	public void damage(Character enermy, int damage) {
	}

	@Override
	public void attack(Character enemy) {
		enemy.damage(this, this.damage);
	}
	/**
	 * Give huge damage to 3x3 grids.
	 */
	private void explode() {
		int x = getX();
		int y = getY();
		Dungeon d = getDungeon();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				for (Entity e: d.getEntityLocation(x + i, y + j))
					if (e instanceof Character)
						attack((Character) e);
	}
	@Override
	public boolean checkEnter(Entity e) {
		return false;
	}
	@Override
	public boolean checkLeave(Entity e) {
		return true;
	}
	@Override
	public void enter(Entity e) {}
	@Override
	public void leave(Entity e) {}
	/**
	 * Change bomber's damage.
	 * @param i
	 */
	public void setDamage(int i) {
		this.damage = i;
	}
}

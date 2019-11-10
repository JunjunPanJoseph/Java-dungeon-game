package unsw.dungeon.EntitiesCollection.GroundScene;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.BombLit;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.Interfaces.Collidable;
import unsw.dungeon.Interfaces.Enterable;
/**
 * Boulder
 * @author ASUS
 *
 */
public class Boulder extends Character implements Collidable, Enterable {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y, 1);
        this.getState().setRotateBeforeMove(false);
        this.imageProperty().set(dungeon.entityImage().getBoulderImage());
    }

	@Override
	/**
	 * Boulder can be pushed by player
	 */
	public void collide(Entity e) {
		if (e instanceof Player) {
			switch (((Player) e).getDirection()) {
			case Up:
				this.moveUp();
				break;
			case Down:
				this.moveDown();
				break;
			case Left:
				this.moveLeft();
				break;
			case Right:
				this.moveRight();
				break;
			}
		}
	}
	@Override
	public void attack(Character enemy) {
		return;
	}
	@Override
	/**
	 * Boulder only can be damaged by bomb
	 */
	public void damage(Character enermy, int damage) {
		//only can be broke by bomb 
		if (enermy instanceof BombLit) {
			Hp().set(getHp() - damage);
		}
		return;	
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
    
}

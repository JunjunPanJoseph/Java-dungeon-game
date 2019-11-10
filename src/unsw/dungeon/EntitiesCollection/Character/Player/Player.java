package unsw.dungeon.EntitiesCollection.Character.Player;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;
import unsw.dungeon.Interfaces.*;
import unsw.dungeon.animation.EntityAnimation;
/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends CharacterWithTimeframeBehavior implements Collidable, Enterable{
	private Pack backPack;
	private int invincibleTime;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param hp
	 */
    public Player(Dungeon dungeon, int x, int y, int hp) {
        super(dungeon, x, y, hp);
        this.backPack = new Pack(this);
        this.invincibleTime = 0;
        this.imageProperty().set(dungeon.entityImage().getPlayerImage());
        this.setActionPeriod(10);
    }
    /**
     * Die - will stop the timeline.
     */
    public boolean die(int oldHpValue) {
    	boolean retVal =  super.die(oldHpValue);
    	if (retVal) {
    		//stop the timeline
    		dungeon.getController().stopTimeline();
    		//Dying animation...bomb
    		
    		EntityAnimation bombLitAnimation = dungeon.entityAnimation().getBombExploding().
    				setupEntityAnimation(dungeon, 144, getX(), getY(), Direction.Down);
    		bombLitAnimation.setStartCount(7);
    		bombLitAnimation.startTimeline();
    		dungeon.playerDie();
    	}
    	return retVal;
    }
    /**
     * print the pack.
     */
	public void printBackPack() {
		System.out.println(backPack.toString());
	}
	public void pickUpItem() {
		List <Entity> entityList = dungeon.getEntityLocation(getX(), getY());
		for (Entity e: entityList) {
			if (e instanceof Item) {
				if (backPack.checkAddToPack((Item) e)) {
					backPack.addToPack((Item) e);
					((Item) e).picked(this);
				}
			}
		}
	}
	/**
	 * Use current item.
	 * @param method
	 */	
	public void useItem(String method) {
		backPack.useCurrItem(method);
		return;
	}
	/**
	 * Drop current item
	 */
	public void dropItem() {
		backPack.dropItem();
	}
	/**
	 * Get player's pack
	 * @return
	 */
	public Pack getPack() {
		return backPack;
	}
	/**
	 * Damageed by enemy
	 */
	@Override
	public void damage(Character enemy, int damage) {
		if (enemy != this) {
			if (invincibleTime > 0) {
				enemy.damage(this, 999);
			} else {
				this.setLastDamageTarget(enemy);
				Hp().set(getHp() - damage);
			}
		}
	}
	@Override
	/**
	 * Attack
	 */
	public void attack(Character enemy) {
		if (invincibleTime > 0) {
			enemy.damage(this, 999);
		}
		return;
	}

	@Override
	/**
	 * Behavior: decrease invicible time.
	 */
	public void behavior(int frameNo) {
		if (invincibleTime > 0) {
			invincibleTime--;
		}
	}
	/**
	 * @return the invincibleTime
	 */
	public int getInvincibleTime() {
		return invincibleTime;
	}
	/**
	 * Become invincible - that is it cannot be damaged and will crash all enermy that want to damage him
	 * @param time
	 */
    public void invincible(int time) {
    	invincibleTime = invincibleTime > time? invincibleTime : time;
    }
	@Override
	/**
	 * When collide with other character: be attacked.
	 */
	public void collide(Entity e) {
		if (e instanceof Character) {
			((Character) e).attack(this);
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
	public void enter(Entity e) {
	}
	@Override
	public void leave(Entity e) {
	}
	
}

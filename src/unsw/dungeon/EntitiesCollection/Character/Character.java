package unsw.dungeon.EntitiesCollection.Character;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableIntegerValue;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.MoveState;
/**
 * Character class
 * @author ASUS
 *
 */
public abstract class Character extends Entity{
	private Character lastDamageTarget;
	private MoveState state;
	private int waitTicks;
	private IntegerProperty hp;
	private int maxHp;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param hp
	 */
	public Character(Dungeon dungeon, int x, int y, int hp) {
		super(dungeon, x, y);
		lastDamageTarget = null;
		this.state = new MoveState(this, true, MoveState.Direction.Down);
		this.setLayer(3);
		this.maxHp = hp;
		this.hp = new SimpleIntegerProperty(hp);
        this.hp.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	if (newValue.intValue() > maxHp) {
            		((WritableIntegerValue) observable).set(maxHp);
        		}
            	if (newValue.intValue() <= 0) {
            		die(oldValue.intValue());
            	}
            }
        });
	}
	/**
	 * Get the wait ticks
	 * @return
	 */
	public int getWaitTicks() {
		return waitTicks;
	}
	/**
	 * @param waitTicks the waitTicks to set
	 */
	public void setWaitTicks(int waitTicks) {
		this.waitTicks = waitTicks;
	}
	/**
	 * @return the lastDamageTarget
	 */
	public Character getLastDamageTarget() {
		return lastDamageTarget;
	}
	/**
	 * @param lastDamageTarget the lastDamageTarget to set
	 */
	public void setLastDamageTarget(Character lastDamageTarget) {
		this.lastDamageTarget = lastDamageTarget;
	}
	/**
	 * @return the state
	 */
	public MoveState getState() {
		return state;
	}
    public abstract void attack(Character enemy);
	public abstract void damage(Character enermy, int damage);
	/**
	 * If hp below 0, die. Remove the entity from dungeon.
	 * @param oldHpValue
	 * @return
	 */
	public boolean die(int oldHpValue) {
		// set hp to 0
		hp.set(0);
		dungeon.deleteEntity(this);
		return true;
	}
	/**
	 * Attack.
	 * @param enemies
	 */
	public void attack(List<Entity> enemies) {
		for (Entity enemy: enemies){
			if (enemy instanceof Character) {
				attack((Character) enemy);
			}
		}
	}
	/**
	 * Get direction
	 * @return
	 */
	public MoveState.Direction getDirection() {
		return state.getDirection();
	}
	/**
	 * Change curr direction
	 * @param direction
	 */
	public void setDirection(MoveState.Direction direction) {
		state.setDirection(direction);
	}
	/**
	 * 
	 * @return
	 */
	public boolean moveToward() {
		return state.moveToward();
	}
	/**
	 * 
	 * @return
	 */
	public boolean moveUp() {
		return state.move(MoveState.Direction.Up);
	}
	/**
	 * 
	 * @return
	 */	
	public boolean moveDown() {
		return state.move(MoveState.Direction.Down);
	}
	/**
	 * 
	 * @return
	 */
	public boolean moveLeft() {
		return state.move(MoveState.Direction.Left);
	}
	/**
	 * 
	 * @return
	 */
	public boolean moveRight() {
		return state.move(MoveState.Direction.Right);
	}
	/**
	 * 
	 * @return
	 */
	public IntegerProperty Hp() {
        return hp;
    }
	/**
	 * 
	 * @return
	 */
    public int getHp() {
        return Hp().get();
    }	
    /**
     * 
     * @return
     */
    public int getMaxHp(){
    	return maxHp;
    }
}
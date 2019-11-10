package unsw.dungeon.EntitiesCollection.Character.AttackAnimate;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
/**
 * Arrow
 * @author ASUS
 *
 */
public class Arrow extends CharacterWithTimeframeBehavior{
	Character owner;
	/**
	 * 
	 * @param dungeon
	 * @param owner
	 */
	public Arrow(Dungeon dungeon, Character owner) {
		super(dungeon, owner.getState().frountX(), owner.getState().frountY(), 1);
		this.owner = owner;
		this.setActionPeriod(1);
		this.imageProperty().setValue(dungeon.entityImage().getArrowImage());
		this.setDirection(owner.getDirection());
	}
	@Override
	/**
	 * Arrow always move forward until collide with something.
	 */
	public void behavior(int frameNo) {
		boolean result = moveToward();
		if (!result) {
			attack(dungeon.getEntityLocation(getState().frountX(), getState().frountY()));
			this.die(0);
		}
	}
	/**
	 * Set the rotated picture.
	 */
	public void setUpPicture() {
		this.getView().setRotate(getState().getRotationDegrees() + 135);
		this.getView().setVisible(false);
	}
	@Override
	/**
	 * Attack enemy: give 1 damage.
	 */
	public void attack(Character enemy) {
		if (enemy != owner) {
			enemy.damage(owner, 1);
		}
	}
	@Override
	/**
	 * Arrow can not be damaged.
	 */
	public void damage(Character enermy, int damage) {
	}

}

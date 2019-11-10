package unsw.dungeon.EntitiesCollection.Pickable.Items.Weapons;


import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.Arrow;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;
import unsw.dungeon.animation.EntityAnimation;

/**
 * Bow class
 * @author ASUS
 *
 */
public class Bow extends Item {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param numItem
	 */
	public Bow(Dungeon dungeon, int x, int y, int numItem) {
		super(dungeon, x, y, "Bow", numItem, 99);
		this.imageProperty().set(dungeon.entityImage().getBowImage());
	}
	
	@Override
	public boolean drop(Pack pack, int x, int y) {
		pack.removeItem(this);
		x().set(x);
		y().set(y);
		dungeon.addEntityRuntime(this);
		return true;
	}

	@Override
	public boolean overLap() {
		// TODO Auto-generated method stub
		return true; 
	}

	@Override
	/**
	 * Use the bow for attack. Will summon arrow entity.
	 */
	public boolean use(Pack pack, String method) {
		Character owner = pack.getOwner();
		subOne();
		if(owner instanceof Player) {
			dungeon.getController().setPlayerWait(12);
		}	
		
		if (dungeon.checkMove(null, owner.getState().frountX(), owner.getState().frountY(),
				false, true, true)) {
			Arrow arrow = new Arrow(getDungeon(), owner);
			arrow.setWaitTicks(10);
			getDungeon().addEntityRuntime(arrow);
			arrow.setUpPicture();
		}
		if (getNumItem() <= 0) {
			pack.removeItem(this);
		}
		EntityAnimation bowAttackAnimation = dungeon.entityAnimation().getBowAttack().
				setupEntityAnimation(dungeon, 200, owner.getX(), owner.getY(), owner.getDirection());
		bowAttackAnimation.startTimeline();
		return true;
	}
}

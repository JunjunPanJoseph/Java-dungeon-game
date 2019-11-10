package unsw.dungeon.EntitiesCollection.Pickable.Items.Weapons;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;
import unsw.dungeon.animation.EntityAnimation;
/**
 * Sword.
 * @author ASUS
 *
 */
public class Sword extends Item {
	private int durability;
	private int maxDurability;
	
	
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Sword(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, "Sword", 1, 1);
		maxDurability = 5;
		durability = 5;
		this.imageProperty().set(dungeon.entityImage().getSwordImage());
	}

	/**
	 * @return the durability
	 */
	public int getDurability() {
		return durability;
	}
	@Override
	/**
	 * Use the sword to attack. 
	 */
	public boolean use(Pack pack, String method) {
		Character owner = pack.getOwner();
		/**
		 * Do not allow use the sword too frequently.
		 */
		dungeon.getController().setPlayerWait(8);
		
		EntityAnimation swordAttackAnimation = dungeon.entityAnimation().getSwordAttack().
				setupEntityAnimation(dungeon, 40, owner.getX(), owner.getY(), owner.getDirection());
		swordAttackAnimation.setUpAttack(dungeon.entityImage().getSwordAttackJson(),
				owner, 1);
		swordAttackAnimation.startTimeline();
		//Durability - 1.
		durability--;
		if (durability == 0) {
			pack.removeItem(this);
		}
		return true;
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
	/**
	 * Sword cannot overlap.
	 */
	public boolean overLap() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	/**
	 * 
	 */
	public String toString() {
		return "Sword [durability=" + durability + "]";
	}
	/**
	 * Show the durability.
	 */
	public String getText() {
		return durability+"/" +maxDurability;
	}
}

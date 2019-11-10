package unsw.dungeon.EntitiesCollection.Pickable;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;
/**
 * Invicibility potion
 * @author ASUS
 *
 */
public class InvincibilityPotion extends Item {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public InvincibilityPotion(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, "InvincibilityPotion", 1, 10);
		this.imageProperty().set(dungeon.entityImage().getInvincibilityPotionImage());
	}
	
	@Override
	public boolean use(Pack pack, String method) {
		// TODO Auto-generated method stub
		return false;
	}
	public void picked(Character e) {
		if (e instanceof Player) {
			((Player) e).invincible(10);
		}
		dungeon.deleteEntity(this);
	}
	@Override
	/**
	 * Potion are used when it is picked.
	 */
	public boolean canAddToPack() {
		return false;
	}
	@Override
	public boolean drop(Pack pack, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean overLap() {
		// TODO Auto-generated method stub
		return false;
	}

}

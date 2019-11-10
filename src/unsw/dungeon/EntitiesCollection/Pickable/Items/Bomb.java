package unsw.dungeon.EntitiesCollection.Pickable.Items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.BombLit;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
/**
 * Bomber
 * @author ASUS
 *
 */
public class Bomb extends Item {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Bomb(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, "Bomb", 1, 99);
		this.imageProperty().set(dungeon.entityImage().getBombImage());
	}

	@Override
	/**
	 * Summon bumblit in current location.
	 */
	public boolean use(Pack pack, String method) {
		Character owner = pack.getOwner();
		subOne();
		getDungeon().addEntityRuntime(new BombLit(getDungeon(), owner.getX(), owner.getY()));
		if (getNumItem() <= 0) {
			pack.removeItem(this);
		}
		return false;
	}

	@Override
	public boolean drop(Pack pack, int x, int y) {
		return false;
	}

	@Override
	public boolean overLap() {
		return true;
	}

}

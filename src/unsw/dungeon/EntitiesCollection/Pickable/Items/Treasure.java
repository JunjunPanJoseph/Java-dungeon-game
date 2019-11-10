package unsw.dungeon.EntitiesCollection.Pickable.Items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
/**
 * Treasure
 * @author ASUS
 *
 */
public class Treasure extends Item {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Treasure(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y, "Treasure", 1, 99);
		this.imageProperty().set(dungeon.entityImage().getTreasureImage());
	}
	@Override
	public boolean use(Pack pack, String method) {
		return false;
	}
	@Override
	public void picked(Character character) {
		dungeon.deleteEntity(this);
		getDungeon().getGoals().treasureSub();
	}
	@Override
	public boolean drop(Pack pack, int x, int y) {
		return false;
	}
	/**
	 * Can overlap
	 */
	@Override
	public boolean overLap() {
		// TODO Auto-generated method stub
		return true;
	}

}

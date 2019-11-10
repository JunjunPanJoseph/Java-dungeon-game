package unsw.dungeon.EntitiesCollection.Pickable.Items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
/**
 * EstusFlask, for add Hp.
 * @author ASUS
 *
 */
public class EstusFlask extends Item {
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param n
	 */
	public EstusFlask(Dungeon dungeon, int x, int y,int n) {
		super(dungeon, x, y, "EstusFlask", n, 14);
		this.imageProperty().set(dungeon.entityImage().getEstusFlaskImage());
	}
	@Override
	public boolean use(Pack pack, String method) {
		Character owner = pack.getOwner();
		subOne();
		owner.Hp().set(owner.getHp() + 10);
		dungeon.getController().setPlayerWait(8);
		return true;
	}

	@Override
	public boolean drop(Pack pack, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	/**
	 * Can overlap. (Maximum: 14)
	 */
	public boolean overLap() {
		// TODO Auto-generated method stub
		return true;
	}

}

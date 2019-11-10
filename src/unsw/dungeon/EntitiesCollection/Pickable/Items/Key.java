package unsw.dungeon.EntitiesCollection.Pickable.Items;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.GroundScene.Door;
/**
 * Key
 * @author ASUS
 *
 */
public class Key extends Item {
	private int id;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param id
	 */
	public Key(Dungeon dungeon, int x, int y, int id) {
		super(dungeon, x, y, "Key", 1, 99);
		this.id = id;
		this.imageProperty().set(dungeon.entityImage().getKeyImage());
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	@Override
	/**
	 * Open the door which have the same id.
	 */
	public boolean use(Pack pack, String method) {
		System.out.println("use key: " + id);
		Character owner = pack.getOwner();
		int coordinate[] = {0, 1};
		coordinate = owner.getState().coordinateTransferX(coordinate);
		List<Entity> frontEntities = dungeon.getEntityLocation(coordinate[0] + owner.getX(), coordinate[1] + owner.getY());
		for (Entity e: frontEntities) {
			if (e instanceof Door) {
				//Open the door use this key.
				boolean openResult = ((Door) e).open(id);
				if (openResult == true) {
					//success open the door - drop this item
					pack.removeItem(this);
					return true;
				} 
			}
		}
		return false;
	}

	@Override
	public boolean drop(Pack pack, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Key cannot overlap
	 */
	@Override
	public boolean overLap() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String toString() {
		return "Key [id=" + id + "]";
	}
}

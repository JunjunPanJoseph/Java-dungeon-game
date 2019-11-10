package unsw.dungeon.EntitiesCollection.Pickable.Items;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Character.Character;
public abstract class Item extends Entity{
	private String name;
	private int numItem;
	private int numMax;
	/**
	 * 
	 * @param dungeon
	 * @param x
	 * @param y
	 * @param name
	 * @param numItem
	 * @param numMax
	 */
	public Item(Dungeon dungeon, int x, int y, String name, int numItem, int numMax){
		super(dungeon, x, y);
		this.name = name;
		this.numItem = numItem;
		this.numMax = numMax;
		this.setLayer(1);
	}
	public abstract boolean use(Pack pack, String method);
	public abstract boolean drop(Pack pack, int x, int y);
	public abstract boolean overLap();
	/**
	 * Pick the item from ground of dungeon.
	 * @param character
	 */
	public void picked(Character character) {
		dungeon.deleteEntity(this);
	}
	/**
	 * 
	 * @return
	 */
	public boolean canAddToPack() {
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return
	 */
	public int getNumItem() {
		return numItem;
	}
	/**
	 * get maximum number of the item
	 * @return
	 */
	public int getNumMax() {
		return numMax;
	}
	/**
	 * Add one more this item.
	 */
	public void addOne() {
		numItem++;
	}
	/** 
	 * Item number subtract one.
	 */
	public void subOne() {
		numItem--;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", numItem=" + numItem + ", numMax=" + numMax + "]";
	}
	public String getText() {
		return numItem+"/" +numMax;
	}
}

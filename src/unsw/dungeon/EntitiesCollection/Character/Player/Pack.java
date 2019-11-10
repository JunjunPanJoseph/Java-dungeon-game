package unsw.dungeon.EntitiesCollection.Character.Player;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.EntitiesCollection.Character.Character;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;

import java.util.ArrayList;
/**
 * Player's pack.
 * @author ASUS
 *
 */
public class Pack {
	private List <Item> backPack;
	private IntegerProperty curr;
	private IntegerProperty change;
	private Character owner;
	/**
	 * 
	 * @param owner
	 */
	public Pack(Character owner){
		backPack = new ArrayList<>();
		curr = new SimpleIntegerProperty(0);
		change = new SimpleIntegerProperty(backPack.size());
		this.owner = owner;
	}
	/**
	 * Get current item.
	 * @return
	 */
	public IntegerProperty Curr() {
		return curr;
	}
	/**
	 * Get pack owner (player)
	 * @return
	 */
	public Character getOwner() {
		return owner;
	}
	/**
	 * Check whether this item can be add to pack.
	 * @param e
	 * @return
	 */
	public boolean checkAddToPack(Item e) {
		int n = 0;
		if (e.overLap()) {
			for(Item packItem: backPack) {
				if (packItem.getClass() ==  e.getClass()) {
					if (packItem.getNumMax() >= 1 + packItem.getNumItem()) {
						return true;
					} else {
						return false;
					}
				}
			}
			//if not found - can add new one
			return true;
		} else {
			//cannot overlap
			for(Item packItem: backPack) {
				if (packItem.getClass() ==  e.getClass()) {
					n++;
				}
			}
			return n < e.getNumMax();
		}
	}
	/**
	 * Reflash the displayer pack in the view.
	 */
	private void makeChange() {
		change.set(change.get() + 1);;
	}
	/**
	 * Add the item to pack
	 * @param item
	 */
	public void addToPack(Item item) {
		if (item.canAddToPack()) {
			if (item.overLap()) {
				for(Item packItem: backPack) {
					if (packItem.getClass() ==  item.getClass()) {
						packItem.addOne();
						makeChange();
						return;
					}
				} 
				backPack.add(item);
			} else {
				backPack.add(item);
				
			}
			makeChange();
			//setCurr(curr.getValue());
		}
	}
	/**
	 * @return the change
	 */
	public IntegerProperty Change() {
		return change;
	}
	/**
	 * Drop current item.
	 */
	public void dropItem() {
		if (backPack.size() > 0 && backPack.get(curr.getValue()).drop(this, owner.getX(), owner.getY())) {
			setCurr(curr.getValue());
		}
		return;
	}
	/**
	 * Remove current item from pack.
	 * @param e
	 */
	public void removeItem(Item e) {
		backPack.remove(e);
		setCurr(curr.getValue());
		makeChange();
		return;
	}
	/**
	 * Use current item to attack.
	 * @param method
	 * @return
	 */
	public boolean useCurrItem(String method) {
		if (backPack.size() > 0) {
			
			boolean retVal =  backPack.get(curr.getValue()).use(this, method);
			makeChange();
			return retVal;
		} else {
			return false;
		}
		
	}
	/**
	 * Get the current item.
	 * @return
	 */
	public Item getCurrItem() {
		if (backPack.size() > 0) {
			return backPack.get(curr.getValue());
		} else {
			return null;
		}
	}
	/**
	 * @return the backPack
	 */
	public List<Item> getBackPack() {
		return backPack;
	}
	/**
	 * Reset the current item counter to valid value.
	 * @param currVal
	 */
	private void setCurr(int currVal) {
		System.out.println("Reset curr index");
		if (currVal < 0) {
			curr.setValue((backPack.size() - 1 >= 0)?backPack.size() - 1:0);
		} else if (currVal >= backPack.size()) {
			curr.setValue(0);
		} else {
			curr.setValue(currVal);
		}
		System.out.println("New curr Val: "+ curr.getValue());
	}
	/**
	 * Current item counter move to left.
	 */
	public void toLeft() {
		setCurr(curr.getValue() - 1);
	}
	/**
	 * Current item counter move to right.
	 */
	public void toRight() {
		setCurr(curr.getValue() + 1);
	}
	/**
	 * 
	 */
	public String toString() {
		String retVal = "curr: " + curr + "  Item: [";
		for (Item i: backPack) {
			retVal += i.toString() + ", ";
		}
		retVal += "]";
		return retVal;
	}
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return backPack.size();
	}
}

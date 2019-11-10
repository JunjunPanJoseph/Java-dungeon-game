package unsw.dungeon.Interfaces;

import unsw.dungeon.EntitiesCollection.Entity;
/**
 * The interface for entity that not allow other entity move in / out.
 * Or need do some checking / trigger some event when move in / out. 
 * @author ASUS
 *
 */
public interface Enterable {
	boolean checkEnter(Entity e);
	boolean checkLeave(Entity e);
	void enter(Entity e);
	void leave(Entity e);
}

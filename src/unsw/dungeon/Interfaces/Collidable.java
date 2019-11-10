package unsw.dungeon.Interfaces;

import unsw.dungeon.EntitiesCollection.Entity;
/**
 * Interface for all collidable entity.
 * @author ASUS
 *
 */
public interface Collidable {
	void collide(Entity e);
}

/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import unsw.dungeon.Controller.GameDungeonController;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
import unsw.dungeon.EntitiesCollection.Character.Enemy.Enemy;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.GroundScene.Boulder;
import unsw.dungeon.EntitiesCollection.GroundScene.Exit;
import unsw.dungeon.EntitiesCollection.GroundScene.Switch;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Treasure;
import unsw.dungeon.Goals.Goals;
import unsw.dungeon.Interfaces.Collidable;
import unsw.dungeon.Interfaces.Enterable;
import unsw.dungeon.animation.AnimationManager;
import unsw.dungeon.animation.EntityImageManager;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
	private IntegerProperty frame;
	private DungeonLoader dungeonLoader;
	private Goals goals;
	private EntityImageManager entityImage;
	private AnimationManager entityAnimation;
	private GameDungeonController controller;

	/**
	 * 
	 * @param width
	 * @param height
	 * @param dungeonLoader
	 */
	public Dungeon(int width, int height, DungeonLoader dungeonLoader) {
    	this.entityImage = new EntityImageManager();
    	this.entityAnimation = new AnimationManager(this);
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
		this.frame = new SimpleIntegerProperty(0);
		this.dungeonLoader = dungeonLoader;
		this.goals = new Goals(this);
    }
	/**
	 * @return the controller
	 */
	public GameDungeonController getController() {
		return controller;
	}
	/**
	 * Add animation node (i.e. image view).
	 * The animation node can move in any direction.
	 * Animation layer is always on the top of other entity layer.
	 * @param node The node want to add to the animation layer.
	 */
	public void addToAnimationLayer(Node node) {
		controller.addToAnimationLayer(node);
	}
	/**
	 * Delete the node from animation layer.
	 * @param node The node that we want to delete/
	 */
	public void deleteToAnimationLayer(Node node) {
		controller.deleteToAnimationLayer(node);
	}
	/**
	 * Set up the controller for dungeon.
	 * @param gameDungeonController
	 */
	public void setController(GameDungeonController gameDungeonController) {
		this.controller = gameDungeonController;
	}
	/**
	 * Get the entityImageManager, which store and load all png pictures and attack JSON.)
	 * @return EntityImageManager
	 */
	public EntityImageManager entityImage() {
		return entityImage;
	}
	
	/**
	 * Get the AnimationManager, which store all the entity animation.
	 * @return AnimationManager
	 */
	public AnimationManager entityAnimation() {
		return entityAnimation;
	}
	/**
	 * Delete entity from dungeon and view.
	 * Also clean up its time frame listener.
	 * @param e
	 */
	public void deleteEntity(Entity e) {
		entities.remove(e);
		controller.removeViewEntity(e.getView());
		e.setView(null);
		if (e instanceof CharacterWithTimeframeBehavior) {
			frame.removeListener(((CharacterWithTimeframeBehavior) e).getListener());
		}
	}
    /**
	 * @return the dungeonLoader
	 */
	public DungeonLoader getDungeonLoader() {
		return dungeonLoader;
	}
	/**
	 * @return the goals
	 */
	public Goals getGoals() {
		return goals;
	}
	/**
	 * Load goals from JSON object.
	 * @param jsonGoals
	 * @return
	 */
	public boolean loadGoals(JSONObject jsonGoals) {
		return goals.loadGoals(jsonGoals);
	}
	/**
	 * Get current frame integer property.
	 * @return IntegerProperty frame
	 */
	public IntegerProperty Frame() {
        return frame;
    }
	/**
	 * Get current frame no.
	 * @return
	 */
    public int getFrame() {
        return Frame().get();
    }
    /**
     * Move to the next frame.
     */
    public void nextFrame() {
    	frame.set(getFrame() + 1);
    }
    /**
     * Get the width of dungeon.
     * @return
     */
    public int getWidth() {
        return width;
    }
	/**
	 * Get the height of dungeon.
	 * @return
	 */
    public int getHeight() {
        return height;
    }
    /**
     * Get the current player.
     * @return
     */
    public Player getPlayer() {
        return player;
    } 
    /**
     * Set up current player.
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Add Entity to the entity list of dungeon. 
     * @param entity
     */
    public void addEntity(Entity entity) {
    	if (entity instanceof Exit) {
    		goals.exitAdd();
    	} else if (entity instanceof Enemy) {
    		goals.enemiesAdd();
    	} else if (entity instanceof Switch) {
    		goals.switchesAdd();
    		for (Entity e: getEntityLocation(entity.getX(), entity.getY())) {
    			if (e instanceof Boulder) {
    				goals.switchesSub();
    			}
    		}
    		
    	} else if (entity instanceof Boulder) {
    		for (Entity e: getEntityLocation(entity.getX(), entity.getY())) {
    			if (e instanceof Switch) {
    				goals.switchesSub();
    			}
    		}
    	} else if (entity instanceof Treasure) {
    		goals.treasureAdd();
    	}
        entities.add(entity);
    }
    /**
     * Add entity runtime. That is, not only add it in dungeon but also load its animation.
     * @param entity
     */
    public void addEntityRuntime(Entity entity) {
		dungeonLoader.runtimeOnLoad(entity);   	
    	addEntity(entity);
    }
    /**
     * Return a list of entities in given location.
     * @param x X location
     * @param y Y location
     * @return List of Entities in the given location.
     */
    public List <Entity> getEntityLocation(int x, int y){
    	List <Entity> retList = new ArrayList <>();
    	for (Entity entity: entities) {
    		if (entity != null && entity.getX() == x && entity.getY() == y) {
    			retList.add(entity);
    		}
    	}
    	return retList;
    }
    /**
     * Check the given x, y coordinate in the range of dungeon size.
     * @param realX x-coordinate
     * @param realY y-coordinate
     * @return True - in the range of dungeon  False - not in the range of dungeon.
     */
	public boolean check(int realX, int realY) {
		if (realX < 0 || realX >= this.getWidth())
			return false;
		if (realY < 0 || realY >= this.getHeight())
			return false;
		return true;
	}
    /**
     * Check whether entity e can move to the given coordinate.
     * @param e The entity.
     * @param x Target X coordinate.
     * @param y Target Y coordinate.
     * @param checkLeaveCurrBlock True - Check whether entity can leave current block.
     * @param checkEnterTargetBlock True - Check whether entity can enter target block.
     * @param checkPlayer True - when checking the target block, do not skip player entity.
     * @return checked result.
     */
    public boolean checkMove(Entity e, int x, int y, 
    		boolean checkLeaveCurrBlock,
    		boolean checkEnterTargetBlock, 
    		boolean checkPlayer){
    	//check target location
		if (!check(x, y)) {
			return false;
		}
		if (checkLeaveCurrBlock) {
			List <Entity> currBlockEntities = getEntityLocation(e.getX(), e.getY());
			for (Entity entity: currBlockEntities) {
				if (entity == e) {
					continue;
				}
				if (entity instanceof Enterable) {
					if (!((Enterable) entity).checkLeave(e)) {
						return false;
					}
				}
			}
		}
		//Block in the location may change - get new entity list in that location
		if (checkEnterTargetBlock) {
			List <Entity> targetBlockEntities = getEntityLocation(x, y);
			for (Entity entity: targetBlockEntities) {
				if (entity instanceof Enterable) {
					if(!checkPlayer) {
						if (entity instanceof Player) {
							continue;
						}
					}
					if (!((Enterable) entity).checkEnter(e)) {
						return false;
					}
				}
			}
		}
    	return true;
    }
    /**
     * Let entity e move to the given location.
     * @param e The entity.
     * @param x Target coordinate x.
     * @param y Target coordinate y.
     * @return True - move to given coordinate successfully.  False - Cannot move the the coordinate.
     */
    public boolean entityMove(Entity e, int x, int y) {
    	checkMove(e, x, y, true, false, true);
		List <Entity> currBlockEntities = getEntityLocation(e.getX(), e.getY());
		List <Entity> targetBlockEntities = getEntityLocation(x, y);
		//try to collide 
		for (Entity entity: targetBlockEntities) {
			if (entity instanceof Collidable) {
				((Collidable) entity).collide(e);
			}
		}
		//Block in the location may change - get new entity list in that location
		targetBlockEntities = getEntityLocation(x, y);
		for (Entity entity: targetBlockEntities) {
			if (entity instanceof Enterable) {
				if (!((Enterable) entity).checkEnter(e)) {
					return false;
				}
			}
		}
		//Leave current block
		for (Entity entity: currBlockEntities) {
			if (entity == e) {
				continue;
			}
			if (entity instanceof Enterable) {
				//If cannot move to target location - return false and don't move
				((Enterable) entity).leave(e);
			}
		}
		//Enter target block
		e.setLoc(x,y);
		//Check
		for (Entity entity: targetBlockEntities) {
			if (entity instanceof Enterable) {
				//If cannot move to target location - return false and don't move
				((Enterable) entity).enter(e);
			}
		}
		return true;
    }
    /**
     * Setup player die message. 
     * Since player die, so the game is finish.
     * @return
     */
	public boolean playerDie() {
		boolean retVal = player.getHp() <= 0;
		if (retVal) {
			goals.setLoseMsgDisplay(controller);
		}
		return retVal;
		
	}
}


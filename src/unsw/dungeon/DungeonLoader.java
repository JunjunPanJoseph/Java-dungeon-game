package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.Enemy.EnemyElf;
import unsw.dungeon.EntitiesCollection.Character.Enemy.EnemyGnome;
import unsw.dungeon.EntitiesCollection.Character.Enemy.EnemyHound;
import unsw.dungeon.EntitiesCollection.Character.Enemy.RedGnome;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.GroundScene.Boulder;
import unsw.dungeon.EntitiesCollection.GroundScene.Door;
import unsw.dungeon.EntitiesCollection.GroundScene.Exit;
import unsw.dungeon.EntitiesCollection.GroundScene.Switch;
import unsw.dungeon.EntitiesCollection.GroundScene.Wall;
import unsw.dungeon.EntitiesCollection.Pickable.InvincibilityPotion;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Bomb;
import unsw.dungeon.EntitiesCollection.Pickable.Items.EstusFlask;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Key;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Treasure;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Weapons.Bow;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Weapons.Sword;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    /**
     * 
     * @param filename
     * @throws FileNotFoundException
     * @throws JSONException
     */
    public DungeonLoader(String filename) throws FileNotFoundException, JSONException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height, this);
        System.out.println(dungeon.loadGoals(json.getJSONObject("goal-condition")));
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }
    /**
     * Load the entity.
     * @param dungeon
     * @param json
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;
        Entity entity = null;
        switch (type) {
        case "player":
        	int hp = 1;
        	if(json.has("hp")) {
        		hp = json.getInt("hp");
        	}
        	Player player = new Player(dungeon, x, y, hp);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(dungeon, x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
    		break;
        case "switch":
        	Switch s = new Switch(dungeon, x, y);
        	onLoad(s);
        	entity = s;
    		break;
        case "treasure":
    		Treasure treasure = new Treasure(dungeon, x, y);
    		onLoad(treasure);
    		entity = treasure;
    		break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(dungeon, x, y, id);
    		onLoad(door);
    		entity = door;
        	break;
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(dungeon, x, y, id);
    		onLoad(key);
    		entity = key;
        	break;
        case "enemy":
        	EnemyElf enemyElf = new EnemyElf(dungeon, x, y);
    		onLoad(enemyElf);
            entity = enemyElf;
        	break;
        case "invincibility":
        	InvincibilityPotion invincibilityPotion = new InvincibilityPotion(dungeon, x, y);
    		onLoad(invincibilityPotion);
            entity = invincibilityPotion;
        	break;
        case "sword":
        	Sword sword = new Sword(dungeon, x, y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "bow":
        	Bow bow = new Bow(dungeon, x, y, json.getInt("n"));
        	onLoad(bow);
        	entity = bow;
        	break;
	    case "exit":
	    	Exit exit = new Exit(dungeon, x, y);
	    	onLoad(exit);
	    	entity = exit;
	    	break;
	    case "bomb":
	    	Bomb bomb = new Bomb(dungeon, x, y);
	    	onLoad(bomb);
	    	entity = bomb;
	    	break;
	    case "genome":
	    	EnemyGnome enemyGenome = new EnemyGnome(dungeon, x, y);
    		onLoad(enemyGenome);
            entity = enemyGenome;
	    	break;
	    case "hound":
	    	EnemyHound enemyHound = new EnemyHound(dungeon, x, y);
	    	onLoad(enemyHound);
	    	entity = enemyHound;
	    	break;
	    case "redGenome":
	    	RedGnome redGnome = new RedGnome(dungeon, x, y);
	    	onLoad(redGnome);
	    	entity = redGnome;
	    	break;
	    case "estusFlask":
	    	EstusFlask estusFlask = new EstusFlask(dungeon, x, y, json.getInt("n"));
	    	onLoad(estusFlask);
	    	entity = estusFlask;
	    	break;
	    }
        dungeon.addEntity(entity);
    }
    /**
     * 
     * @param e
     */
    public abstract void onLoad(Entity e);
    // TODO Create additional abstract methods for the other entities
    /**
     * 
     * @param entity
     */
	public void runtimeOnLoad(Entity entity) {
		// TODO Auto-generated method stub
		onLoad(entity);
	}

}

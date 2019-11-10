package unsw.dungeon.animation;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import javafx.scene.image.Image;

/**
 * Store all the entity image resources,  and the raw picture for animation with its attack JSON file.
 * @param width
 * @param height
 * @param dungeonLoader
 */

public class EntityImageManager {
	//Images
    private Image yesImage;
    private Image noImage;
    private Image playerImage;
    private Image selectImage;
    private Image wallImage;
    private Image boulderImage;
    private Image switchImage;
    private Image treasureImage;
    private Image closedDoorImage;
    private Image openedDoorImage;
    private Image keyImage;
    private Image enemyElfImage;
    private Image enemyGenomeImage;
    private Image enemyHoundImage;
    private Image enemyHoundImageR;
	private Image enemyRedGnomeImage;
    private Image invincibilityPotionImage;
    private Image swordImage;
    private Image arrowImage;
    private Image bowImage;
    private Image estusFlaskImage;
    
    
	private Image exitImage;
    private Image bombImage;
    private Image BombExploding;
    private Image SwordAttack;
    private Image FireBoulder;
    private Image BowAttack;
    private Image SickleAttack;
	private Image CastFireBoulder;
	private Image CastSummonHound;
	
    private JSONArray SwordAttackJson;
    private JSONArray FireBoulderJson;
    private JSONArray SickleAttackJson;

    /**
     * 
     */
	public EntityImageManager() {
		yesImage = new Image("file:/../images/yes.png");
		noImage = new Image("file:/../images/no.png");
		playerImage = new Image("file:/../images/human_new.png");
        //Problem: image not transparent
        selectImage = new Image("file:/../images/selectitem.png");
        wallImage = new Image("file:/../images/brick_brown_0.png");
        boulderImage = new Image("file:/../images/boulder.png");
        switchImage = new Image("file:/../images/pressure_plate.png");
        treasureImage = new Image("file:/../images/gold_pile.png");
        closedDoorImage = new Image("file:/../images/closed_door.png");
        openedDoorImage = new Image("file:/../images/open_door.png");
        keyImage = new Image("file:/../images/key.png");
        enemyElfImage = new Image("file:/../images/deep_elf_master_archer.png");
        enemyGenomeImage = new Image("file:/../images/gnome.png");
        enemyHoundImage = new Image("file:/../images/hound.png");
        enemyHoundImageR = new Image("file:/../images/houndR.png");
        enemyRedGnomeImage = new Image("file:/../images/redGnome.png");
        invincibilityPotionImage = new Image("file:/../images/brilliant_blue_new.png");
        swordImage = new Image("file:/../images/greatsword_1_new.png");
        arrowImage = new Image("file:/../images/arrow.png");
        bowImage = new Image("file:/../images/bow.png");
        exitImage = new Image("file:/../images/exit.png");
        bombImage = new Image("file:/../images/bomb_unlit.png");
        estusFlaskImage = new Image("file:/../images/estusFlask.png");
        
        BombExploding = new Image("file:/../images/BombExploding.png");
        SwordAttack = new Image("file:/../images/SwordAttack.png");
        BowAttack = new Image("file:/../images/BowAttack.png");
        SickleAttack = new Image("file:/../images/SickleAttack.png");
        FireBoulder = new Image("file:/../images/FireBoulder.png");
        CastFireBoulder = new Image("file:/../images/CastFireBoulder.png");
        CastSummonHound = new Image("file:/../images/CastSummonHound.png");
        try {
        	SwordAttackJson = new JSONArray(new JSONTokener(new FileReader("file:/../images/SwordAttack.json")));
        	FireBoulderJson = new JSONArray(new JSONTokener(new FileReader("file:/../images/FireBoulderJson.json")));
        	SickleAttackJson = new JSONArray(new JSONTokener(new FileReader("file:/../images/SickleAttack.json")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
	 * @return the yesImage
	 */
	public Image getYesImage() {
		return yesImage;
	}

	/**
	 * @return the noImage
	 */
	public Image getNoImage() {
		return noImage;
	}

	/**
	 * @return the arrowImage
	 */
	public Image getArrowImage() {
		return arrowImage;
	}
    /**
	 * @return the enemyHoundImage
	 */
	public Image getEnemyHoundImage() {
		return enemyHoundImage;
	}
	public Image getEnemyHoundImageR() {
		return enemyHoundImageR;
	}
	/**
	 * @return the bowImage
	 */
	public Image getBowImage() {
		return bowImage;
	}
	/**
	 * @return the bowAttack
	 */
	public Image getBowAttack() {
		return BowAttack;
	}
	/**
	 * @return the fireBoulder
	 */
	public Image getFireBoulder() {
		return FireBoulder;
	}
	/**
	 * @return the fireBoulderJson
	 */
	public JSONArray getFireBoulderJson() {
		return FireBoulderJson;
	}
    /**
	 * @return the enemyGenomeImage
	 */
	public Image getEnemyGenomeImage() {
		return enemyGenomeImage;
	}
	/**
	 * @return the swordAttackJson
	 */
	public JSONArray getSwordAttackJson() {
		return SwordAttackJson;
	}
	/**
	 * @return the bombExploding
	 */
	public Image getBombExploding() {
		return BombExploding;
	}
	public Image getSwordAttack() {
		return SwordAttack;
	}

	/**
	 * @return the selectImage
	 */
	public Image getSelectImage() {
		return selectImage;
	}
	
	/**
	 * @return the playerImage
	 */
	public Image getPlayerImage() {
		return playerImage;
	}
	/**
	 * @return the wallImage
	 */
	public Image getWallImage() {
		return wallImage;
	}
	/**
	 * @return the boulderImage
	 */
	public Image getBoulderImage() {
		return boulderImage;
	}
	/**
	 * @return the switchImage
	 */
	public Image getSwitchImage() {
		return switchImage;
	}
	/**
	 * @return the treasureImage
	 */
	public Image getTreasureImage() {
		return treasureImage;
	}
	/**
	 * @return the closedDoorImage
	 */
	public Image getClosedDoorImage() {
		return closedDoorImage;
	}
	/**
	 * @return the openedDoorImage
	 */
	public Image getOpenedDoorImage() {
		return openedDoorImage;
	}
	/**
	 * @return the keyImage
	 */
	public Image getKeyImage() {
		return keyImage;
	}
	/**
	 * @return the enemyElfImage
	 */
	public Image getEnemyElfImage() {
		return enemyElfImage;
	}
	/**
	 * @return the invincibilityPotionImage
	 */
	public Image getInvincibilityPotionImage() {
		return invincibilityPotionImage;
	}
	/**
	 * @return the swordImage
	 */
	public Image getSwordImage() {
		return swordImage;
	}
	/**
	 * @return the exitImage
	 */
	public Image getExitImage() {
		return exitImage;
	}
	/**
	 * @return the bombImage
	 */
	public Image getBombImage() {
		return bombImage;
	}
	public Image getEstusFlaskImage() {
		// TODO Auto-generated method stub
		return estusFlaskImage;
	}
	public Image getSickleAttack() {
		// TODO Auto-generated method stub
		return SickleAttack;
	}
	public JSONArray getSickleAttackJson() {
		// TODO Auto-generated method stub
		return SickleAttackJson;
	}
	public Image getEnemyRedGnomeImage() {
		// TODO Auto-generated method stub
		return enemyRedGnomeImage;
	}
	public Image getCastFireBoulder() {
		// TODO Auto-generated method stub
		return CastFireBoulder;
	}
	public Image getCastSummonHound() {
		// TODO Auto-generated method stub
		return CastSummonHound;
	}
}
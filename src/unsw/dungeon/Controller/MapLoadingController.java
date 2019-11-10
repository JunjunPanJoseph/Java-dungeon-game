package unsw.dungeon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import unsw.dungeon.Model.MapLoadingModel;
import unsw.dungeon.Scene.EntryViewScene;
import unsw.dungeon.Scene.GameDungeonScene;

public class MapLoadingController {
	private EntryViewScene entryViewScene;
	private GameDungeonScene gameDungeonScene;
	private MapLoadingModel mapLoadingModel;
	private String currentMap;
	/**
	 * 
	 * @param mapLoadingScene
	 */
	public MapLoadingController(){
		this.currentMap = "../dungeons/advanced.json"; 
		mapLoadingModel = new MapLoadingModel(this);
	}
	
    /**
	 * @param entryViewScene the entryViewScene to set
	 */
	public void setEntryViewScene(EntryViewScene entryViewScene) {
		this.entryViewScene = entryViewScene;
	}
	public void setGameDungeonScene(GameDungeonScene gameDungeonScene) {
		this.gameDungeonScene = gameDungeonScene;		
	}
	@FXML
    private Button back;
    @FXML
    private Button start;
    /**
	 * @return the currentMap
	 */
	public String getCurrentMap() {
		return currentMap;
	}

	/**
	 * @param currentMap the currentMap to set
	 */
	public void setCurrentMap(String currentMap) {
		this.currentMap = currentMap;
	}

	/**
	 * @return the message
	 */
	public Text getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Text message) {
		this.message = message;
	}

	/**
	 * @return the setting
	 */
	public ScrollPane getSetting() {
		return setting;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting(ScrollPane setting) {
		this.setting = setting;
	}
	@FXML
    private Text message;
    @FXML
    private ScrollPane setting;
    
	@FXML
	/**
	 * initialize thet path
	 */
	public void initialize() {
		String path = "../dungeons/";
		mapLoadingModel.setUpList(path);
	}
    @FXML
    /**
     * Go back
     */
    public void handleBack(ActionEvent event) {
    	entryViewScene.start();
    }
    
    @FXML
    /**
     * Start the game
     * @param event
     */
    public void handleStart(ActionEvent event) {
    	try {
    		boolean start = gameDungeonScene.start(currentMap);
    		if (!start) {
    			mapLoadingModel.setUpList(currentMap);
    		}
    	} catch (Exception e) {
    		mapLoadingModel.setOpenErrorMessage(currentMap);
    	}
    }
    @FXML
    /**
     * Key press
     * @param event
     */
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case ENTER:
        	this.handleStart(null);
		default:
			break;
        }
    }
}

package unsw.dungeon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import unsw.dungeon.Scene.EntryViewScene;
import unsw.dungeon.Scene.MapLoadingScene;
import unsw.dungeon.Scene.SettingScene;
/**
 * 
 * @author ASUS
 *
 */
public class EntryViewController {
	private SettingScene settingScene;
	private EntryViewScene entryViewScene;
	private MapLoadingScene mapLoadingScene;
	public EntryViewController(EntryViewScene entryViewScene){
		this.entryViewScene = entryViewScene;
	}
	
	/**
	 * @param settingScene the settingScene to set
	 */
	public void setSettingScene(SettingScene settingScene) {
		this.settingScene = settingScene;
	}
	public void setMapLoadingScene(MapLoadingScene mapLoadingScene) {
		this.mapLoadingScene = mapLoadingScene;
	}
    @FXML
    private Button startGame;
    @FXML
    private Button exit;
    @FXML
    private Button setting;

    
    @FXML
    public void handleStartGame(ActionEvent event) {
    	mapLoadingScene.start();
    }
    @FXML
    public void handleExit(ActionEvent event) {
    	entryViewScene.close();
    }
    @FXML
    public void handleSetting(ActionEvent event) {
    	settingScene.start();
    }
    @FXML
    public void handleKeyPress(KeyEvent event) {
    	System.out.println("Key Press: "+event.getText());
        switch (event.getCode()) {
        case ENTER:
        	this.handleStartGame(null);
		default:
			break;
        }
    }

}

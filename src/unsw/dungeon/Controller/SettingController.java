package unsw.dungeon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import unsw.dungeon.Model.SettingModel;
import unsw.dungeon.Scene.EntryViewScene;

public class SettingController {
	private EntryViewScene entryViewScene;
	private SettingModel settingModel;
    private boolean goBack;
    /**
     * 
     */
	public SettingController(){
		this.settingModel = new SettingModel(this);
		goBack = false;
		settingModel.initializeKeySetting();
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
	/**
	 * @param entryViewScene the entryViewScene to set
	 */
	public void setEntryViewScene(EntryViewScene entryViewScene) {
		this.entryViewScene = entryViewScene;
	}
	/**
	 * 
	 */
	public SettingModel getModel(){
		return settingModel;
	}
	@FXML
    private Text message;
	@FXML
	private Button back;
    @FXML
    private Button save;
    @FXML
    private ScrollPane setting;

	@FXML
	public void initialize() {
		settingModel.setUpKeySettingContent();
	}
	
    @FXML
    public void handleBack(ActionEvent event) {
    	if(!goBack) {
    		ShowMsg("Are you sure go back? You will lose unsaved setting.");
    		goBack = true;
    	} else {
    		goBack = false;
    		message.setText("");
    		entryViewScene.start();
    	}
    }
    
    @FXML
    public void handleSave(ActionEvent event) {
    	goBack = false;
    	settingModel.saveToFile();
    }
	public void ShowMsg(String msg) {
		message.setText(msg);
	}

}

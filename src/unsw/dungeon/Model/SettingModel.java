package unsw.dungeon.Model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.Controller.SettingController;

public class SettingModel {
	SettingController settingController;
    private JSONObject keySetting;
	public SettingModel(SettingController settingController){
		this.settingController = settingController;
	}
	/**
	 * Generate the key map.
	 * @return
	 */
	public Map<KeyCode, KeyCode> generateKeyMap(){
		Map<KeyCode, KeyCode> keyMap = new HashMap<>();
        for (String key: keySetting.keySet()) {
        	JSONObject currObj = keySetting.getJSONObject(key);
        	String inputString = currObj.getString("input");
        	KeyCode inputCode = KeyCode.valueOf(inputString);
        	String outputString = currObj.getString("mapTo");
        	KeyCode outputCode = KeyCode.valueOf(outputString);
        	keyMap.put(inputCode, outputCode);
        }
		return keyMap;
	}
	/**
	 * Save setting in JSON file.
	 */
	public void writeToJSONFile() {
		String settingStr = keySetting.toString();
		FileWriter writer;
		try {
			writer = new FileWriter(new File(DungeonConfig.KEY_SETTING_FILE_NAME));
			writer.write(settingStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialize the key setting.
	 */
	public void initializeKeySetting() {
		try {
			keySetting = new JSONObject(new JSONTokener(new FileReader(DungeonConfig.KEY_SETTING_FILE_NAME)));
		} catch (Exception e) {
			keySetting = new JSONObject();
			//Initialize json object
			JSONObject pause = new JSONObject();
			pause.put("description", "pause");
			pause.put("input", KeyCode.ESCAPE);
			pause.put("mapTo", KeyCode.ESCAPE);
			keySetting.put("pause", pause);
			
			JSONObject dropItem = new JSONObject();
			dropItem.put("description", "dropItem");
			dropItem.put("input", KeyCode.Q);
			dropItem.put("mapTo", KeyCode.Q);
			keySetting.put("dropItem", dropItem);
			
			JSONObject nextFrame = new JSONObject();
			nextFrame.put("description", "nextFrame");
			nextFrame.put("input", KeyCode.T);
			nextFrame.put("mapTo", KeyCode.T);
			keySetting.put("nextFrame", nextFrame);
			
			JSONObject showGoals = new JSONObject();
			showGoals.put("description", "showGoals");
			showGoals.put("input", KeyCode.C);
			showGoals.put("mapTo", KeyCode.C);
			keySetting.put("showGoals", showGoals);
			
			JSONObject moveUp = new JSONObject();
			moveUp.put("description", "moveUp");
			moveUp.put("input", KeyCode.UP);
			moveUp.put("mapTo", KeyCode.UP);
			keySetting.put("moveUp", moveUp);
			JSONObject moveDown = new JSONObject();
			moveDown.put("description", "moveDown");
			moveDown.put("input", KeyCode.DOWN);
			moveDown.put("mapTo", KeyCode.DOWN);
			keySetting.put("moveDown", moveDown);
			JSONObject moveLeft = new JSONObject();
			moveLeft.put("description", "moveLeft");
			moveLeft.put("input", KeyCode.LEFT);
			moveLeft.put("mapTo", KeyCode.LEFT);
			keySetting.put("moveLeft", moveLeft);
			JSONObject moveRight = new JSONObject();
			moveRight.put("description", "moveRight");
			moveRight.put("input", KeyCode.RIGHT);
			moveRight.put("mapTo", KeyCode.RIGHT);
			keySetting.put("moveRight", moveRight);
			JSONObject printBackPack = new JSONObject();
			printBackPack.put("description", "printBackPack");
			printBackPack.put("input", KeyCode.E);
			printBackPack.put("mapTo", KeyCode.E);
			keySetting.put("printBackPack", printBackPack);
			JSONObject pickUpItem = new JSONObject();
			pickUpItem.put("description", "pickUpItem");
			pickUpItem.put("input", KeyCode.F);
			pickUpItem.put("mapTo", KeyCode.F);
			keySetting.put("pickUpItem", pickUpItem);
			JSONObject useItem = new JSONObject();
			useItem.put("description", "useItem");
			useItem.put("input", KeyCode.R);
			useItem.put("mapTo", KeyCode.R);
			keySetting.put("useItem", useItem);
			JSONObject packToLeft = new JSONObject();
			packToLeft.put("description", "packToLeft");
			packToLeft.put("input", KeyCode.A);
			packToLeft.put("mapTo", KeyCode.A);
			keySetting.put("packToLeft", packToLeft);
			JSONObject packToRight = new JSONObject();
			packToRight.put("description", "packToRight");
			packToRight.put("input", KeyCode.D);
			packToRight.put("mapTo", KeyCode.D);
			keySetting.put("packToRight", packToRight);
			writeToJSONFile();
			initializeKeySetting();
		}
	}
	/**
	 * SetUp keysetting content
	 */
	public void setUpKeySettingContent() {
		
		ListView<HBox> list = new ListView<>();
        ObservableList<HBox> items =FXCollections.observableArrayList ();
        for (String key: keySetting.keySet()) {
        	JSONObject currObj = keySetting.getJSONObject(key);
        	String value = currObj.getString("input");
        	
	        HBox textContainer = new HBox();
	        textContainer.setAlignment(Pos.TOP_RIGHT);
	        textContainer.setSpacing(200);
	        Text description = new Text();
	        TextField textInput = new TextField();
	        description.setText(key);        
	        textInput.setText(value);     
	        textContainer.getChildren().addAll(description, textInput);
	        items.add(textContainer);
        }
        list.setItems(items);
        list.setMinWidth(600);
        settingController.getSetting().setContent(list);
	}
	/**
	 * Save the content from view to JSON.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean saveToJSONObjSetting() {
		ListView<HBox> list = (ListView<HBox>) settingController.getSetting().getContent();
		for (Node node: list.getItems()) {
			HBox pane = (HBox) node;
			String key = "defaultKey";
			String value = "defaultValue";
			for (Node paneChild: pane.getChildren()) {
				if (paneChild instanceof Text) {
					key = ((Text) paneChild).getText();
				}
				if (paneChild instanceof TextField) {
					value = ((TextField) paneChild).getText();
				}
				
			}
			JSONObject modifyObj = keySetting.getJSONObject(key);
			modifyObj.put("input", value);
			keySetting.put(key, modifyObj);
		}
		return true;
	}
	/**
	 * Check keySetting
	 * @return
	 */
	public boolean checkJSONKeySetting() {
		boolean retVal = true;
        for (String key: keySetting.keySet()) {
        	JSONObject currObj = keySetting.getJSONObject(key);
        	String code = currObj.getString("input");
        	try {
        		KeyCode.valueOf(code);
        	} catch (Exception e){
        		settingController.ShowMsg("Invalid Code - " + code + "for key + " + key);
        		retVal = false;
        	}
        }
        return retVal;
	}
	public void saveToFile() {
    	saveToJSONObjSetting();
    	if (checkJSONKeySetting()) {
    		writeToJSONFile();
    		settingController.ShowMsg("Success!");
    		generateKeyMap();
    	} 
		
	}
}

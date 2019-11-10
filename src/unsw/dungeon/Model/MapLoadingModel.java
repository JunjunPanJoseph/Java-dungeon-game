package unsw.dungeon.Model;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import unsw.dungeon.Controller.MapLoadingController;

public class MapLoadingModel {
	MapLoadingController mapLoadingController;
	public MapLoadingModel(MapLoadingController mapLoadingController){
		this.mapLoadingController = mapLoadingController;
	}
	public boolean setUpList(String path) {
		boolean retVal = false;
		File file = new File("file:/" + path);
		if (file != null && file.isDirectory()) {
			ListView<String> list = getDirContent(path);
			mapLoadingController.getSetting().setContent(list);
			retVal = true;
		} else {
			retVal = false;
		}
		
		if (retVal) {
			resetMessage();
		} else {
			setOpenErrorMessage(path);
		}
		return retVal;
	}
	/**
	 * Extract content from directory
	 * @param path
	 * @return
	 */
	public ListView<String> getDirContent(String path) {
		File file = new File("file:/" + path);
		ObservableList<String> items =FXCollections.observableArrayList();
		File[] fs = file.listFiles();
		items.add("../");
		for(File f:fs){
			if (f.isDirectory()) {
				items.add(f.getName() + "/");
			} else {
				items.add(f.getName());
			}
		}
        ListView<String> list = new ListView<>();
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
			String fileName = list.getSelectionModel().getSelectedItem();
			mapLoadingController.setCurrentMap(path + fileName);
	    });
        list.setMinWidth(672);
        return list;
	}
	/**
	 * Set msg of cannot open
	 * @param path
	 * @return 
	 */
	public void setOpenErrorMessage(String path){
		mapLoadingController.getMessage().setText("Cannot open: "+path);
	}
	/**
	 * Set msg of file no found
	 * @param path
	 * @return 
	 */
	public void setNotJSONfileMessage(String path){
		mapLoadingController.getMessage().setText("Not a JSON file or directory: "+path);
	}
	/**
	 * Reset message
	 * @return 
	 */
	public void resetMessage(){
		mapLoadingController.getMessage().setText("");
	}
}

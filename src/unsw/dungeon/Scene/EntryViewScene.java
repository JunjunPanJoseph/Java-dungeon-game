package unsw.dungeon.Scene;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.Controller.EntryViewController;

public class EntryViewScene {

    private Stage stage;
    private String title;
    private EntryViewController controller;
    private Scene scene;
    /**
	 * 
	 * @param stage
	 * @throws IOException
	 */
    public EntryViewScene(Stage stage) throws IOException {
        this.stage = stage;
        title = DungeonConfig.TITLE;

        controller = new EntryViewController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(DungeonConfig.VIEW_PATH + "EntryView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        root.requestFocus();
        scene = new Scene(root);
    }
    /**
     * Start the scene.
     */
    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Get the controller.
     * @return
     */
    public EntryViewController getController() {
        return controller;
    }
    /**
     * 
     * @param settingScene
     */
    public void setSettingScene(SettingScene settingScene){
    	getController().setSettingScene(settingScene);
    }
    /**
     * 
     * @param mapLoadingScene
     */
	public void setMapLoadingScene(MapLoadingScene mapLoadingScene) {
		getController().setMapLoadingScene(mapLoadingScene);
		
	}
	/**
	 * Exit the game.
	 */
    public void close() {
    	stage.close();
    }
}

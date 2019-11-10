package unsw.dungeon.Scene;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.Controller.GameDungeonController;

public class GameDungeonScene {

    private Stage stage;
    private String title;
    private GameDungeonController controller;
    private Scene scene;
    /**
     * 
     * @param stage
     * @throws IOException
     */
    public GameDungeonScene(Stage stage) throws IOException {
        this.stage = stage;
        title = DungeonConfig.TITLE;

        controller = new GameDungeonController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(DungeonConfig.VIEW_PATH + "GameDungeonView.fxml"));
        loader.setController(controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
    }
    /**
     * Start the scene.
     */
	public boolean start(String mapFile) {
		try {
			controller.loadDungeon(mapFile);
			controller.startTimeline();
	        stage.setTitle(title);
	        stage.setScene(scene);
	        stage.show();
	        return true;
		} catch (FileNotFoundException e) {
			System.out.println("Can not open map file");
			return false;
		}

    }
	/**
	 * 
	 * @param mapLoadingScene
	 */
    public void setMapLoadingScene(MapLoadingScene mapLoadingScene) {
    	controller.setMapLoadingScene(mapLoadingScene);
    }
    /**
     * Get the controller.
     * @return
     */
    public GameDungeonController getController() {
        return controller;
    }
    /**
     * 
     * @param settingScene
     */
	public void setSettingScene(SettingScene settingScene) {
		controller.setSettingScene(settingScene);
	}
    
}

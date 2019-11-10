package unsw.dungeon.Scene;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.Controller.MapLoadingController;
public class MapLoadingScene {

    private Stage stage;
    private String title;
    private MapLoadingController controller;
    private Scene scene;
    /**
     * 
     * @param stage
     * @throws IOException
     */
    public MapLoadingScene(Stage stage) throws IOException {
        this.stage = stage;
        title = DungeonConfig.TITLE;

        controller = new MapLoadingController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(DungeonConfig.VIEW_PATH + "MapLoadingView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
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
    public MapLoadingController getController() {
        return controller;
    }
    /**
     * 
     * @param entryViewScene
     */
    public void setEntryViewScene(EntryViewScene entryViewScene){
    	getController().setEntryViewScene(entryViewScene);
    }
    /**
     * 
     * @param gameDungeonScene
     */
    public void setGameDungeonScene(GameDungeonScene gameDungeonScene){
    	getController().setGameDungeonScene(gameDungeonScene);
    }

	/**
	 * @return the scene
	 */
	public Stage getStage() {
		return stage;
	}
    
}

package unsw.dungeon.Scene;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.Controller.SettingController;

public class SettingScene {

    private Stage stage;
    private String title;
    private SettingController controller;
    private Scene scene;
    /**
     * 
     * @param stage
     * @throws IOException
     */
    public SettingScene(Stage stage) throws IOException {
        this.stage = stage;
        title = DungeonConfig.TITLE;

        controller = new SettingController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(DungeonConfig.VIEW_PATH + "SettingView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
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
    public SettingController getController() {
        return controller;
    }
    /**
     * 
     * @param entryViewScene
     */
    public void setEntryViewScene(EntryViewScene entryViewScene){
    	getController().setEntryViewScene(entryViewScene);
    }
}

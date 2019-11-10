package unsw.dungeon;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import unsw.dungeon.Scene.EntryViewScene;
import unsw.dungeon.Scene.GameDungeonScene;
import unsw.dungeon.Scene.MapLoadingScene;
import unsw.dungeon.Scene.SettingScene;
/**
 * The main entry for dungeon game.
 * @author ASUS
 *
 */
public class DungeonApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
    	EntryViewScene entryViewScene = new EntryViewScene(primaryStage);
    	SettingScene settingScene = new SettingScene(primaryStage);
    	MapLoadingScene mapLoadingScene = new MapLoadingScene(primaryStage);
    	GameDungeonScene gameDungeonScene = new GameDungeonScene(primaryStage);
    	
    	entryViewScene.setSettingScene(settingScene);
    	entryViewScene.setMapLoadingScene(mapLoadingScene);
    	
    	settingScene.setEntryViewScene(entryViewScene);
    	
    	mapLoadingScene.setEntryViewScene(entryViewScene);
    	mapLoadingScene.setGameDungeonScene(gameDungeonScene);
    	
    	gameDungeonScene.setMapLoadingScene(mapLoadingScene);
    	gameDungeonScene.setSettingScene(settingScene);
    	
    	entryViewScene.start();
    }
    public static void main(String[] args) {
        launch(args);
    }

}


package unsw.dungeon.Controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonConfig;
import unsw.dungeon.DungeonGameLoader;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;
import unsw.dungeon.Model.GameDungeonModel;
import unsw.dungeon.Scene.MapLoadingScene;
import unsw.dungeon.Scene.SettingScene;

public class GameDungeonController {
	private GameDungeonModel gameDungeonModel;
	private MapLoadingScene mapLoadingScene;
	private SettingScene settingScene;
	private boolean back;

	public void setSettingScene(SettingScene settingScene) {
		this.settingScene = settingScene;
	}
	/**
	 * 
	 */
	public GameDungeonController(){
		this.gameDungeonModel = new GameDungeonModel(this);
	}
	
	/**
	 * Set the scene
	 * @param mapLoadingScene
	 */
	public void setMapLoadingScene(MapLoadingScene mapLoadingScene) {
		this.mapLoadingScene = mapLoadingScene;
	}
	
	/**
	 * Add the node to animation layer.
	 * @param node
	 */
	public void addToAnimationLayer(Node node) {
		gameDungeonModel.addToAnimationLayer(node);	
	}
	/**
	 * Delete the node from animation layer
	 * @param node
	 */
	public void deleteToAnimationLayer(Node node) {
		gameDungeonModel.deleteToAnimationLayer(node);	
	}

	
	/**
	 * Remove the entity from view.
	 * @param imageView
	 */
	public void removeViewEntity(ImageView imageView) {
		squares.getChildren().remove(imageView);
	}
	/**
	 * Return whether the game is end.
	 * @return
	 */
	public boolean gameEnd() {
		return gameDungeonModel.gameEnd();
	}
	/**
	 * Set the game is end.
	 * @param gameEnd
	 */
	public void setGameEnd(boolean gameEnd) {
		gameDungeonModel.setGameEnd(gameEnd);
	}
    /**
     * for unit test
	 * @return the dungeon
	 */
	public Dungeon getDungeon() {
		return gameDungeonModel.getDungeon();
	}

	/**
	 * @return the playerWait
	 */
	public int getPlayerWait() {
		return gameDungeonModel.getPlayerWait();
	}
	/**
	 * @param playerWait the playerWait to set
	 */
	public void setPlayerWait(int playerWait) {
		gameDungeonModel.setPlayerWait(playerWait);
	}
	
	
	
	@FXML
	private GridPane squares;
	@FXML
	private ImageView playerImage;
	@FXML
	private BorderPane  animationLayer;

	@FXML
	private ProgressBar playerHpBar;
	@FXML
	private Text playerHpValue;
	@FXML
	private GridPane playerPack;
	@FXML
	private GridPane playerPackCurrentItem;
	
	@FXML
	private Rectangle blackBackground;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Pane goals;
	@FXML
	private Pane gamePane;
	@FXML
	private Pane msgPlane;
	@FXML
	private Rectangle backGround;
	@FXML
	private Text message;
	@FXML
	private Text backMessage;
	@FXML
	private Button bottonBack;
	@FXML
	private Button bottonContinue;

	@FXML
	public void handleBottonBack(ActionEvent event) {	
		if (back || gameDungeonModel.getGameEnd()) {
			mapLoadingScene.start();
		} else {
			back = true;
			backMessage.setText("Are you sure? You cannot continue the game if you go back.");
		}
	}
	@FXML
	public void handleBottonContinue(ActionEvent event) {	
		gameDungeonModel.gameContinue();
	}
    @FXML
    public void handleKeyPress(KeyEvent event) {
    	gameDungeonModel.handleKeyPress(event);
    }
	public Node getMsgPlane() {
		// TODO Auto-generated method stub
		return msgPlane;
	}
	public Pane getGoals() {
		return goals;
	}
	public void setBack(boolean back) {
		this.back = back;
	}
	/**
	 * @return the backMessage
	 */
	public Text getBackMessage() {
		return backMessage;
	}
	public boolean getBack() {
		return back;
	}
	/**
	 * @return the gamePane
	 */
	public Pane getGamePane() {
		return gamePane;
	}
	/**
	 * @return the blackBackground
	 */
	public Rectangle getBlackBackground() {
		return blackBackground;
	}
    public ScrollPane getScrollPane() {
    	return scrollPane;
    }
    public void setBackMessageContent(String text) {
    	backMessage.setText(text);
    }
    public void setMessageContent(String text) {
    	message.setText(text);
    }
	/**
	 * @return the mapLoadingScene
	 */
	public MapLoadingScene getMapLoadingScene() {
		return mapLoadingScene;
	}
	/**
	 * @return the settingScene
	 */
	public SettingScene getSettingScene() {
		return settingScene;
	}
	/**
	 * @return the squares
	 */
	public GridPane getSquares() {
		return squares;
	}
	/**
	 * @return the playerImage
	 */
	public ImageView getPlayerImage() {
		return playerImage;
	}
	/**
	 * @return the animationLayer
	 */
	public BorderPane getAnimationLayer() {
		return animationLayer;
	}
	/**
	 * @return the playerHpBar
	 */
	public ProgressBar getPlayerHpBar() {
		return playerHpBar;
	}
	/**
	 * @return the playerHpValue
	 */
	public Text getPlayerHpValue() {
		return playerHpValue;
	}
	/**
	 * @return the playerPack
	 */
	public GridPane getPlayerPack() {
		return playerPack;
	}
	/**
	 * @return the playerPackCurrentItem
	 */
	public GridPane getPlayerPackCurrentItem() {
		return playerPackCurrentItem;
	}
	/**
	 * @return the backGround
	 */
	public Rectangle getBackGround() {
		return backGround;
	}
	/**
	 * @return the message
	 */
	public Text getMessage() {
		return message;
	}
	public void loadDungeon(String mapFile) throws FileNotFoundException, JSONException {
		gameDungeonModel.loadDungeon(mapFile);
		
	}
	public void startTimeline() {
		gameDungeonModel.startTimeline();		
	}
	public void stopTimeline() {
		gameDungeonModel.stopTimeline();		
	}
	public void pause() {
		gameDungeonModel.pause();
	}
    
}

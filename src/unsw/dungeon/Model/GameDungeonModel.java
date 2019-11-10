package unsw.dungeon.Model;

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
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
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
import unsw.dungeon.Controller.GameDungeonController;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Item;

public class GameDungeonModel {
	GameDungeonController gameDungeonController;
	private Dungeon dungeon;
    private Player player;
	private int playerWait;
	//Timeline for keyboard event (player)
	private Timeline timeline;
	//Timeline for dungeon
	private Timeline dungeonTimeline;
	ImageView packImage;
	private KeyEvent keyboardBuffer = null;
	private boolean gameEnd;
	private boolean testTimeline;
	private Map<KeyCode, KeyCode> keyMap;
	
	public GameDungeonModel(GameDungeonController gameDungeonController){
		this.gameDungeonController = gameDungeonController;
		this.playerWait = 0;
		this.timeline = new Timeline();		
		this.dungeonTimeline = new Timeline();		
		this.testTimeline = false;
		this.gameEnd = false;
		this.keyMap = null;
		EventHandler<ActionEvent> dungeonKeyFrame = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
    	    	dungeon.nextFrame();
            }
        };
        EventHandler<ActionEvent> keyboardTimeLine = new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent t) {

            	if (playerWait > 0) {
            		playerWait --;
            	} else {
            		handleKeyEvent(keyboardBuffer);
        			keyboardBuffer = null;
            	}
        	}
        };
        dungeonTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), dungeonKeyFrame));
        dungeonTimeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), keyboardTimeLine));
        timeline.setCycleCount(Animation.INDEFINITE);
	}
	/**
	 * Pause the game.
	 */
	public void pause() {
		gameDungeonController.getMsgPlane().setDisable(false);
		gameDungeonController.getMsgPlane().setVisible(true);
		Pane goals = gameDungeonController.getGoals();
		goals.getChildren().removeAll(goals.getChildren());
        goals.getChildren().removeAll(goals.getChildren());       
        goals.getChildren().add(dungeon.getGoals().toListView(dungeon.entityImage()));
        stopTimeline();
	}
	public void gameContinue() {
		gameDungeonController.setBack(false);
		gameDungeonController.getBackMessage().setText("");
		gameDungeonController.getMsgPlane().setDisable(true);
		gameDungeonController.getMsgPlane().setVisible(false);
        //give back forcus to game controller
		gameDungeonController.getGamePane().requestFocus();
        startTimeline();
	}
	/**
	 * 
	 * Return whether the game is end.
	 * @return
	 */
	public boolean gameEnd() {
		return gameEnd;
	}
	/**
	 * Set the game is end.
	 * @param gameEnd
	 */
	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}
    /**
     * for unit test
	 * @return the dungeon
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}

	/**
	 * @return the playerWait
	 */
	public int getPlayerWait() {
		return playerWait;
	}
	/**
	 * @param playerWait the playerWait to set
	 */
	public void setPlayerWait(int playerWait) {
		this.playerWait = playerWait;
	}
	
	/**
	 * Map key input to output.
	 * @param input
	 * @return
	 */
	public KeyCode mapedCode(KeyCode input) {
		if (keyMap == null) {
			return input;
		} else {
			if(keyMap.containsKey(input)) {
				return keyMap.get(input);
			} else {
				return KeyCode.UNDEFINED;
			}
		}
	}
	/**
	 * Reset curr state.
	 */
	public void resetControllerState() {
		Rectangle blackBackground = gameDungeonController.getBlackBackground();
		ScrollPane scrollPane = gameDungeonController.getScrollPane();
		Pane goals = gameDungeonController.getGoals();
		GridPane squares = gameDungeonController.getSquares();
		GridPane playerPack = gameDungeonController.getPlayerPack();
		BorderPane animationLayer = gameDungeonController.getAnimationLayer();
		GridPane playerPackCurrentItem = gameDungeonController.getPlayerPackCurrentItem();
		blackBackground.setWidth(700 < dungeon.getWidth() * 32? dungeon.getWidth() * 32: 700);
		blackBackground.setHeight(400 < dungeon.getHeight() * 32? dungeon.getHeight() * 32: 400);
        goals.getChildren().removeAll(goals.getChildren());
		if(scrollPane != null) {
			scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
			scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
			scrollPane.setVvalue(getVRatio(dungeon.getWidth(), player.getY()));
			scrollPane.setHvalue(getHRatio(dungeon.getWidth(), player.getX()));
		}
		gameDungeonController.setBack(false);
		gameDungeonController.setBackMessageContent("");
		playerWait = 0;
		gameEnd = false;
		gameDungeonController.setMessageContent("Pause");
		
		squares.getChildren().removeAll(squares.getChildren());
		playerPack.getChildren().removeAll(playerPack.getChildren());
		animationLayer.getChildren().removeAll(animationLayer.getChildren());
		playerPackCurrentItem.getChildren().removeAll(playerPackCurrentItem.getChildren());
		
		
		keyMap = gameDungeonController.getSettingScene().getController().getModel().generateKeyMap();
        gameContinue();
	}
	/**
	 * Start the timeline.
	 */
	public void startTimeline() {
		if (!testTimeline && !gameEnd) {
			timeline.play();
			dungeonTimeline.play();
		}
	}
	/**
	 * Stop thie timeline
	 */
	public void stopTimeline() {
		timeline.stop();
		dungeonTimeline.stop();
	}
	/**
	 * Add the node to animation layer.
	 * @param node
	 */
	public void addToAnimationLayer(Node node) {
		gameDungeonController.getAnimationLayer().getChildren().add(node);
	}
	/**
	 * Delete the node from animation layer
	 * @param node
	 */
	public void deleteToAnimationLayer(Node node) {
		gameDungeonController.getAnimationLayer().getChildren().remove(node);
	}
	/**
	 * Get v ratio of scroll bar
	 * @param dungeon_height
	 * @param player_y
	 * @return
	 */
	private double getVRatio(int dungeon_height, int player_y) {
		double removeEdge = dungeon_height - 2 * DungeonConfig.SCROLL_HEIGHT;
		double retVal = 0;
		if (player_y < DungeonConfig.SCROLL_HEIGHT) {
			retVal = 0;
		} else if (player_y < dungeon_height - DungeonConfig.SCROLL_HEIGHT) {
			retVal = ((double) (player_y - DungeonConfig.SCROLL_HEIGHT)) / removeEdge;
		} else {
			retVal = 1;
		}
		return retVal;
	}
	/**
	 * Get H ratio of scroll bar
	 * @param dungeon_width
	 * @param player_x
	 * @return
	 */
	private double getHRatio(int dungeon_width, int player_x) {
		double removeEdge = dungeon_width - 2 * DungeonConfig.SCROLL_WIDTH;
		double retVal = 0;
		if (player_x < DungeonConfig.SCROLL_WIDTH) {
			retVal = 0;
		} else if (player_x < dungeon_width - DungeonConfig.SCROLL_WIDTH) {
			retVal = ((double) (player_x - DungeonConfig.SCROLL_WIDTH)) / removeEdge;
		} else {
			retVal = 1;
		}
		return retVal;
	}
	/**
	 * Load the dungeon from map file to view
	 * @param mapFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws JSONException
	 */
	public boolean loadDungeon(String mapFile) throws FileNotFoundException, JSONException {
		DungeonGameLoader loader = new DungeonGameLoader(mapFile, gameDungeonController.getSquares());
		dungeon = loader.load();
		dungeon.setController(gameDungeonController);
		player = dungeon.getPlayer();
		//Reset the text of message - it will be modified by win/loss.
		resetControllerState();
		//setup player image
		gameDungeonController.getPlayerImage().setImage(dungeon.entityImage().getPlayerImage());
		//setup player hp
		Text playerHpValue = gameDungeonController.getPlayerHpValue();
		ProgressBar playerHpBar = gameDungeonController.getPlayerHpBar();
		ChangeListener<Number> hpListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	playerHpValue.setText(player.Hp().getValue()+ "/" + player.getMaxHp() );
            	playerHpBar.setProgress(((double)player.getHp()) / ((double) player.getMaxHp()));
            }
        };
		player.Hp().addListener(hpListener);
		//initialize player hp
		playerHpValue.setText(player.Hp().getValue()+ "/" + player.getMaxHp() );
		playerHpBar.setProgress(((double)player.getHp()) / ((double) player.getMaxHp()));
		//initialize backpack item
		
		packImage =makePackImage(dungeon.entityImage().getSelectImage());
	    GridPane.setColumnIndex(packImage, 0);
	    GridPane.setRowIndex(packImage, 0);
		//add listener to pack current item location
		player.getPack().Curr().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	        	GridPane.setColumnIndex(packImage, player.getPack().Curr().getValue());
	        }
	    });
		gameDungeonController.getPlayerPackCurrentItem().getChildren().add(packImage);
		//add listener to pack item 
		GridPane playerPack = gameDungeonController.getPlayerPack();
		player.getPack().Change().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				playerPack.getChildren().remove(0, playerPack.getChildren().size());
				List <Item> itemList = player.getPack().getBackPack();
				for (int i = 0; i < itemList.size(); i++) {
					Item currItem = itemList.get(i);
					//Image
					ImageView currItemImageView = makePackImage(currItem.imageProperty().get());
					playerPack.add(currItemImageView, i, 0);
					Text currItemText =makePackText(currItem.getText());
					playerPack.add(currItemText, i, 0);
					GridPane.setHalignment(currItemText, HPos.RIGHT);
					GridPane.setValignment(currItemText, VPos.BOTTOM);
				}
			}
		});
		//Scroll bar width listener
		ScrollPane scrollPane = gameDungeonController.getScrollPane();
		if (dungeon.getWidth() > 2 * DungeonConfig.SCROLL_WIDTH) {
	    player.x().addListener(new ChangeListener<Number>() {
		        @Override
		        public void changed(ObservableValue<? extends Number> observable,
		                Number oldValue, Number newValue) {
	    			scrollPane.setHvalue(getHRatio(dungeon.getWidth(), newValue.intValue()));
		        }
		    });
		}
		//Scroll bar height listener
		if (dungeon.getHeight() > 2 * DungeonConfig.SCROLL_HEIGHT) {
		    player.y().addListener(new ChangeListener<Number>() {
		    	@Override
		        public void changed(ObservableValue<? extends Number> observable,
		                Number oldValue, Number newValue) {
	    			scrollPane.setVvalue(getVRatio(dungeon.getHeight(), newValue.intValue()));
		        }
		    });
		}
		
		
		gameContinue();
		loader.loadToGrid(dungeon);
		return true;
	}/**
	 * Make the pack image in correct format
	 * @param packImage
	 * @return
	 */
	private ImageView makePackImage(Image packImage) {
		ImageView temp = new ImageView(packImage);
		temp.setFitHeight(80);
		temp.setFitWidth(80);
		return temp;
	}
	/**
	 * Make the pack display text.
	 * @param packText
	 * @return
	 */
	private Text makePackText(String packText) {
		Text temp = new Text(packText);
		temp.setFont(Font.font(null, FontWeight.BOLD, 14));
		temp.setFill(Color.rgb(255, 255, 255, .99));
		temp.setStroke(Color.rgb(0, 0, 0, .99));
		return temp;
	}
	public void handleKeyEvent(KeyEvent event) {
		if (event == null) {
			return;
		}
		switch (mapedCode(event.getCode())) {
		case ESCAPE:
			pause();
            break;
		case ENTER:
			gameContinue();
			break;
        case Q:
        	player.dropItem();
        	break;
        case T:
            dungeon.nextFrame();
            break;
        case C:
        	System.out.println(dungeon.getGoals().toString());
        	break;
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case E: 
        	//show pack
        	player.printBackPack();
        	break;
        case F: 
        	//pick up item
        	player.pickUpItem();
        	break;
        case R: 
        	//use item
        	player.useItem("default");
        	break;
        case A:
        	player.getPack().toLeft();
        	break;
        case D:
        	player.getPack().toRight();
    		break;
        default:
            break;
        }
	}
	public void setKeyboardBuffer(KeyEvent event) {
		keyboardBuffer = event;
	}
	public boolean getTestTimeline() {
		return testTimeline;
	}
	public boolean getGameEnd() {
		// TODO Auto-generated method stub
		return gameEnd;
	}
    public void handleKeyPress(KeyEvent event) {
    	if (testTimeline
		|| mapedCode(event.getCode()) == KeyCode.ESCAPE) {
    		handleKeyEvent(event);
    	} else {
        	keyboardBuffer = event;
    	}
    }
}

package unsw.dungeon.Goals;

import org.json.JSONObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Controller.GameDungeonController;
import unsw.dungeon.Interfaces.GoalComponent;
import unsw.dungeon.animation.EntityImageManager;
/**
 * Composite goal and goal's property manager.
 * @author ASUS
 *
 */
public class Goals {
	private Dungeon dungeon;
	private GoalComponent goals;
	
	private IntegerProperty exit;
	private IntegerProperty enemies;
	private IntegerProperty switches;
	private IntegerProperty treasure;
	
	/**
	 * 
	 * @param dungeon
	 */
	public Goals (Dungeon dungeon){
		this.dungeon = dungeon;
		exit = new SimpleIntegerProperty(0);
		enemies = new SimpleIntegerProperty(0);
		switches = new SimpleIntegerProperty(0);
		treasure = new SimpleIntegerProperty(0);
		goals = null;
	}
	/**
	 * Load the goal from jsonGoals.
	 * @param jsonGoals
	 * @return
	 */
	public boolean loadGoals(JSONObject jsonGoals) {
		System.out.println(jsonGoals);
		try {
			goals = createGoal(jsonGoals, this);
			return true;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	/**
	 * Create composite goals from JSON goal object.
	 * @param jsonGoals
	 * @param goals
	 * @return
	 * @throws Exception
	 */
	public static GoalComponent createGoal(JSONObject jsonGoals, Goals goals) throws Exception {
		try {
			String goal = jsonGoals.getString("goal");
			switch (goal) {
			case "AND":
			case "OR":
				return new GoalComposite(jsonGoals, goals);
			case "exit":
			case "enemies":
			case "boulders":
			case "treasure":
				return new GoalLeaf(jsonGoals, goals);
			default:
				throw  new Exception("Invalid goal: " + goal);
			}
		} catch (Exception e){
			throw e;
		}
	}
	public void printWinMsg() {
		System.out.println("======================");
		System.out.println("=======YOU  WIN=======");
		System.out.println("======================");
	}
	public void setWinMsgDisplay(GameDungeonController controller) {
		controller.setGameEnd(true);
		controller.getMessage().setText("You Win!!!");
		controller.pause();
	}
	public void setLoseMsgDisplay(GameDungeonController controller) {
		controller.setGameEnd(true);
		controller.getMessage().setText("You Die!!!");
		controller.pause();
	}
	/**
	 * Check whether player win the game.
	 * If so, printout the win message.
	 * @return
	 */
	public boolean checkWin() {
		boolean retVal = checkGoals();
		if (retVal) {
			if (dungeon != null && dungeon.getController() != null) {
				setWinMsgDisplay(dungeon.getController());
				//printWinMsg();
			} else {
				printWinMsg();
			}
		}
		return retVal;
	}
	
	/**
	 * @return the goals
	 */
	public GoalComponent getGoals() {
		return goals;
	}
	/**
	 * Check whether the composite goals is meted.
	 * @return
	 */
	public boolean checkGoals() {
		return goals.checkGoals();
	}
	@Override
	public String toString() {
		return "Goals [exit=" + exit + ", enemies=" + enemies + ", switches=" + switches + ", treasure=" + treasure
				+ ", goals=" + goals + ", result = " + checkGoals() + "]";
	}
	/**
	 * Add current goal to list view, which can be displayed in fount end.
	 */
	public VBox toListView(EntityImageManager entityImage){
		return goals.toListView(entityImage);
	}
	/**
	 * @return the treasure
	 */
	public IntegerProperty treasure() {
		return treasure;
	}
	/**
	 * @return the exit
	 */
	public IntegerProperty exit() {
		return exit;
	}
	/**
	 * @return the enemies
	 */
	public IntegerProperty enemies() {
		return enemies;
	}
	/**
	 * @return the switches
	 */
	public IntegerProperty switches() {
		return switches;
	}
	public void exitAdd() {
		exit.setValue(1);
	}
	public void exitSub() {
		exit.setValue(0);
		checkWin();
	}
	public void enemiesAdd() {
		enemies.setValue(enemies.getValue() + 1);
	}
	public void enemiesSub() {
		enemies.setValue(enemies.getValue() - 1);
		checkWin();
	}
	public void switchesAdd() {
		switches.setValue(switches.getValue() + 1);
	}
	public void switchesSub() {
		switches.setValue(switches.getValue() - 1);
		checkWin();
	}
	public void treasureAdd() {
		treasure.setValue(treasure.getValue() + 1);
	}
	public void treasureSub() {
		treasure.setValue(treasure.getValue() - 1);
		checkWin();
	}

}

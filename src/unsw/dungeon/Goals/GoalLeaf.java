package unsw.dungeon.Goals;

import org.json.JSONObject;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import unsw.dungeon.Interfaces.GoalComponent;
import unsw.dungeon.animation.EntityImageManager;
/**
 * The leaf of composite goal.
 * That is, only contain single goal condition, but do not contain any logic combination.
 * @author ASUS
 *
 */
public class GoalLeaf  implements GoalComponent{
	private String goal;
	private IntegerProperty reference;
	/**
	 * 
	 * @param jsonGoals
	 * @param goals
	 * @throws Exception
	 */
	public GoalLeaf(JSONObject jsonGoals,  Goals goals) throws Exception {
		this.goal = jsonGoals.getString("goal");
		switch (this.goal) {
		case "exit":
			reference = goals.exit();
			break;
		case "enemies":
			reference = goals.enemies();
			break;
		case "boulders":
			reference = goals.switches();
			break;
		case "treasure":
			reference = goals.treasure();
			break;
		default:
			throw  new Exception("Invalid goal: " + goal);
		}
	}

	/**
	 * Check whether the goal is finished.
	 */
	@Override
	public boolean checkGoals() {
		return reference.intValue() <= 0;
	}
	
	/**
	 * Get current goal string.
	 */
	@Override
	public String getGoal() {
		// TODO Auto-generated method stub
		return goal;
	}

	/**
	 * To String method.
	 */
	@Override
	public String toString() {
		return goal;
	}
	
	/**
	 * Add current goal to list view, which can be displayed in fount end.
	 */
	@Override
	public VBox toListView(EntityImageManager entityImage) {
		VBox list = new VBox();
        HBox container = new HBox();
        list.getChildren().add(container);
        ImageView graph = new ImageView();
		switch (goal) {
		case "exit":
			graph.setImage(entityImage.getExitImage());
			break;
		case "enemies":
			graph.setImage(entityImage.getEnemyElfImage());
			break;
		case "boulders":
			graph.setImage(entityImage.getBoulderImage());
			break;
		case "treasure":
			graph.setImage(entityImage.getTreasureImage());
			break;
		}
		Text description = new Text();
		String outputText = "";
		int refInt = reference.intValue();
		if(refInt == 0) {
			outputText = "Finished!";
			description.setFill(Color.GREEN);
		} else {
			outputText = "Haven't finished. ";
			description.setFill(Color.RED);
			switch (this.goal) {
			case "exit":
				outputText += "Find the exit!";
				break;
			case "enemies":
				outputText += "Kill " + refInt + " more enemies.";
				break;
			case "boulders":
				outputText += "Push " + refInt + " more boulders to the switches.";
				break;
			case "treasure":
				outputText += "Collect " + refInt + " more treasures!";
				break;
			}
		}
		description.setText(outputText);
		//description.setStroke(Color.rgb(0, 0, 0, .99));
		description.setStyle("-fx-font-size: 16");
		description.setStyle("-fx-font-weight:bold");
		container.getChildren().add(graph);
		container.getChildren().add(description);
		return list;
	}
	
}

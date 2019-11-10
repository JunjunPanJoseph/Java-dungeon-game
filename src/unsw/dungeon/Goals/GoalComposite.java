package unsw.dungeon.Goals;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import unsw.dungeon.Interfaces.GoalComponent;
import unsw.dungeon.animation.EntityImageManager;
/**
 * The composite part of composite goal.
 * That is, contain and / or logic statement, and a list of subgoals.
 * The evaluation of this goal is the logical combination of all subgoals.
 * @author ASUS
 *
 */
public class GoalComposite implements GoalComponent{
	private String goal;
	private List<GoalComponent> subgoals;
	public GoalComposite(JSONObject jsonGoals, Goals goals) throws Exception {
		goal = jsonGoals.getString("goal");
		subgoals = new ArrayList<>();
		JSONArray subs = jsonGoals.getJSONArray("subgoals");
		for (Object sub: subs) {
			if (sub instanceof JSONObject) {
				subgoals.add(Goals.createGoal((JSONObject) sub, goals));
			} else {
				throw  new Exception("Invalid goal: " + sub);
			}
		}
	}
	/**
	 * Check whether current goal is meeted.
	 */
	@Override
	public boolean checkGoals() {
		boolean retVal = false;
		switch (goal) {
		case "AND":
			retVal = true;
			break;
		case "OR":
			retVal = false;
			break;
		}
		for (GoalComponent g: subgoals) {
			switch (goal) {
			case "AND":
				retVal = retVal && g.checkGoals();
				break;
			case "OR":
				retVal = retVal || g.checkGoals();
				break;
			}
		}
		return retVal;
	}
	/**
	 * Get current goal string
	 */
	@Override
	public String getGoal() {
		// TODO Auto-generated method stub
		return goal;
	}
	/**
	 * To string method.
	 */
	@Override
	public String toString() {
		String retStr =  goal + " [";
		for (GoalComponent g: subgoals) {
			retStr = retStr + g.toString() + ", ";
		}
		retStr = retStr + "]";
		return retStr;
	}
	/**
	 * Add current goal to list view, which can be displayed in fount end.
	 */
	@Override
	public VBox toListView(EntityImageManager entityImage) {
		VBox list = new VBox();
		Text logic = null;
		for (GoalComponent goal: subgoals) {
	        HBox container = new HBox();
	        if (goal.checkGoals()) {
	        	container.getChildren().add(new ImageView(entityImage.getYesImage()));
	        } else {
	        	container.getChildren().add(new ImageView(entityImage.getNoImage()));	        	
	        }
	        container.getChildren().add(goal.toListView(entityImage));
	        logic = new Text(this.goal);
	        logic.setStroke(Color.rgb(255, 255, 255, .99));
	        logic.setStyle("-fx-font-size: 16");
	        logic.setStyle("-fx-font-weight:bold");
	        logic.setFill(Color.RED);
	        list.getChildren().add(container);
	        list.getChildren().add(logic);
		}
		//remove the last one
		list.getChildren().remove(logic);
		return list;
	}

}

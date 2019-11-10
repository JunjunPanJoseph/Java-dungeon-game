package unsw.dungeon.Interfaces;

import javafx.scene.layout.VBox;
import unsw.dungeon.animation.EntityImageManager;
/**
 * Component pattern for goals.
 * @author ASUS
 *
 */
public interface GoalComponent {
	boolean checkGoals();
	String getGoal();
	public String toString();
	VBox toListView(EntityImageManager entityImage);
}

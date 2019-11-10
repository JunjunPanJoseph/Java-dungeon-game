package unsw.dungeon.animation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
import unsw.dungeon.EntitiesCollection.Character.Character;
/**
 * Class for Entity animation. 
 * The picture of image view will change with timetick.
 * @author ASUS
 *
 */
public class EntityAnimation {
	private Dungeon dungeon;
	private TimeframeAnimation tfAnimation;
	private int x;
	private int y;
	private Direction direction;
	//The life count for animation entity.
	private int count;
	private List<Entity> animationEntities;
	private Timeline timeline;
	//Whether the animation is in attack mode.
	private boolean setUpAttack;
	private Character owner;
	private int damage;
	/**
	 * 
	 * @param dungeon
	 * @param tfAnimation
	 * @param tick_ms
	 * @param x
	 * @param y
	 * @param direction
	 */
	public EntityAnimation (Dungeon dungeon, TimeframeAnimation tfAnimation, 
			int tick_ms, int x, int y,  Direction direction){
		this.dungeon = dungeon;
		this.tfAnimation = tfAnimation;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.count = 1;
		this.setUpAttack = false;
		this.animationEntities = new ArrayList<Entity>();
		for (int imageAbsX = 0; imageAbsX < tfAnimation.widthIndex(); imageAbsX++) {
			for (int imageAbsY = 0; imageAbsY < tfAnimation.heightIndex(); imageAbsY++) {
				//get image first
				int relX = imageAbsX - tfAnimation.centerX();
				int relY = imageAbsY - tfAnimation.centerY();
				Image currImage = tfAnimation.getImageBlock(0, relX, relY);
				int rotatedRelX = 0;
				int rotatedRelY = 0;
				//rotate
				switch (direction) {
				case Up:
					rotatedRelX = -relX;
					rotatedRelY = -relY;
					break;
				case Down:
					rotatedRelX = relX;
					rotatedRelY = relY;
					break;
				case Left:
					rotatedRelX = -relY;
					rotatedRelY = relX;
					break;
				case Right:
					rotatedRelX = relY;
					rotatedRelY = -relX;
					break;
				}
				int realX = rotatedRelX + x;
				int realY = rotatedRelY + y;
				
				if (dungeon.check(realX, realY)) {
					Entity entity = new Entity(dungeon, realX, realY);
					dungeon.addEntityRuntime(entity);
					this.animationEntities.add(entity);
					entity.imageProperty().set(currImage);
					entity.getView().setRotate(getRotationDegree());

				}
				

			}
		}
		
		timeline = new Timeline();
        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
        		//System.out.println("Count: " + count);
        		if (count >= tfAnimation.getMaxFrameNo()) {
        			stopTimeline();
        			die();
        			timeline.getKeyFrames().remove(0, timeline.getKeyFrames().size());
        		} else {
	        		updateImage();
	        		count ++;
        		}
            }
        };
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tick_ms), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	/**
	 * Update the image of entities, Which is use for keyframe animation.)
	 */
	private void updateImage() {
		int frameNo = count;
		for (Entity entity: animationEntities) {
			int relX = entity.getX() - x;
			int relY = entity.getY() - y;
			int rotatedRelX = 0;
			int rotatedRelY = 0;
			//rotate
			switch (direction) {
			case Up:
				rotatedRelX = -relX;
				rotatedRelY = -relY;
				break;
			case Down:
				rotatedRelX = relX;
				rotatedRelY = relY;
				break;
			case Left:
				rotatedRelX = relY;
				rotatedRelY = -relX;
				break;
			case Right:
				rotatedRelX = -relY;
				rotatedRelY = relX;
				break;
			}
			Image newImage = tfAnimation.getImageBlock(frameNo, rotatedRelX, rotatedRelY);
			if (setUpAttack) {
				//Load the attack location from JSON. 
				if (tfAnimation.checkAttackLoc(frameNo, rotatedRelX, rotatedRelY)) {
					List <Entity> entities= dungeon.
							getEntityLocation(entity.getX(), entity.getY());
					for(Entity e: entities) {
						if (e instanceof Character) {
							((Character) e).damage(owner, damage);
						}
					}
				}
			}
			//Change picture
			entity.imageProperty().set(newImage);
			//Rotate
			entity.getView().setRotate(getRotationDegree());
			
		}
	}
	/**
	 * Set up attack mode.
	 * @param AttackJson
	 * @param owner
	 * @param damage
	 */
	public void setUpAttack(JSONArray AttackJson, Character owner, int damage) {
		this.setUpAttack = true;
		this.tfAnimation.setAttackJson(AttackJson);
		this.owner = owner;
		this.damage = damage;
	}
	/**
	 * Set up the initial count, which enable playing the animation from other frame.
	 * @param count
	 */
	public void setStartCount(int count) {
		this.count = count;
		updateImage();
	}
	/**
	 * Calculate the rotation degree.
	 * Clockwise = Positive, Down = 0.
	 * @return
	 */
	
	private double getRotationDegree() {
		double degree = 0;
		switch (direction) {
		case Up:
			degree = 180;
			break;
		case Down:
			degree = 0;
			break;
		case Left:
			degree = 90;
			break;
		case Right:
			degree = -90;
			break;
		}
		return degree + tfAnimation.getDegree();
	}
	/**
	 * Start the animation
	 */
	public void startTimeline() {
		timeline.play();
	}
	/**
	 * Pause the animation
	 */
	public void stopTimeline() {
		timeline.stop();
	}
	/**
	 * Stop the animation and clean the entity from dungeon and view. 
	 */
	public void die() {
		timeline.stop();
		for (Entity e: animationEntities) {
			e.getDungeon().deleteEntity(e);
		}
		this.animationEntities.clear();
	}
}

package unsw.dungeon.animation;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
/**
 * Generator of TimeframeAnimation. It is a template for runtime entity animation.
 * @author ASUS
 *
 */
public class TimeframeAnimation {
	private PixelReader imageReader;
	private int W;
	private int H;
	private int maxFrameNo;
	private int centerX;
	private int centerY;
	private double degree;
	private JSONArray AttackJson;
	/**
	 * 
	 * @param rawPicture 
	 * @param maxFrameNo Max frame number for this animation. It will be used to calculate the size for single frame picture. 
	 * @param centerX The center x coordinate for display animation. 
	 * @param centerY The center y coordinate for display animation.
	 * @param degree The rotation degree of picture.
	 */
	public TimeframeAnimation(Image rawPicture, int maxFrameNo, int centerX, int centerY, double degree){
		this.imageReader = rawPicture.getPixelReader();
		this.W = (int) (rawPicture.getWidth() / maxFrameNo);
		this.H = (int) (rawPicture.getHeight());
		this.maxFrameNo = maxFrameNo;
		this.centerX = centerX;
		this.centerY = centerY;
		this.AttackJson = null;
		this.degree = degree;
	}
	/**
	 * Get the degree
	 * @return the degree
	 */
	public double getDegree() {
		return degree;
	}
	/**
	 * Set the degree
	 * @param degree the degree to set
	 */
	public void setDegree(double degree) {
		this.degree = degree;
	}
	/**
	 * Set the Attack JSON
	 * @param AttackJson
	 */
	public void setAttackJson(JSONArray AttackJson) {
		this.AttackJson = AttackJson;
	}
	/**
	 * Check whether the given frame number and x, y value in the range of PNG file and JSON file.
	 * @param currFrameNo
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkCoordinate(int currFrameNo, int x, int y) {
		if (currFrameNo < 0 || currFrameNo >= maxFrameNo) {
			return false;
		}
		if (x < 0 || x + 1 > W / 32) {
			return false;
		}
		if (y < 0 || y + 1 > H / 32) {
			return false;
		}
		return true;
	} 
	/**
	 * Get the corresponding image for entity in given frame and relevant location.
	 * @param currFrameNo
	 * @param x
	 * @param y
	 * @return
	 */
	public Image getImageBlock(int currFrameNo, int x, int y) {
		int absoluteX = x + centerX;
		int absoluteY = y + centerY;
		boolean retVal = checkCoordinate(currFrameNo, absoluteX, absoluteY);
		if (!retVal) {
			return null;
		}
		int xStart = W * currFrameNo + absoluteX * 32;
		int yStart = absoluteY * 32;
		return new WritableImage(imageReader, xStart, yStart, 32, 32);
	}
	/**
	 * Check whether the given location is a valid attack location.
	 * @param currFrameNo
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkAttackLoc(int currFrameNo, int x, int y) {
		if (AttackJson == null) {
			return false;
		}
		int absoluteX = x + centerX;
		int absoluteY = y + centerY;
		boolean retVal = checkCoordinate(currFrameNo, absoluteX, absoluteY);
		if (!retVal) {
			return false;
		}
		JSONArray currFrameJSON = (JSONArray) AttackJson.get(currFrameNo);
        for (int i = 0; i < currFrameJSON.length(); i++) {
        	 JSONObject currObj = (JSONObject) currFrameJSON.get(i);
        	 if (x == currObj.getInt("x") && y == currObj.getInt("y")) {
        		 return true;
        	 }
        }
		return false;
	}
	/**
	 * Set up the entity animation in the given location. 
	 * The center of animation (set in constructor) will be displayed on the provided x, y location.
	 * @param dungeon
	 * @param tick_ms The tick for single timeframe.
	 * @param x
	 * @param y
	 * @param direction Display direction of animation.
	 * @return
	 */
	public EntityAnimation setupEntityAnimation(Dungeon dungeon, int tick_ms, 
			int x, int y, Direction direction) {
		EntityAnimation newEntityAnimation = new EntityAnimation
				(dungeon, this,	tick_ms, x, y, direction);
		return newEntityAnimation;
	}
	/**
	 * change the pixel width to index
	 * @return
	 */
	public int widthIndex() {
		return W / 32;
	}
	/**
	 * change the pixel height to index
	 * @return
	 */
	public int heightIndex() {
		return H / 32;
	}
	/**
	 * 
	 * @return
	 */
	public int centerX() {
		return centerX;
	}
	/**
	 * 
	 * @return
	 */
	public int centerY() {
		return centerY;
	}
	/**
	 * 
	 * @return
	 */
	public int getMaxFrameNo() {
		return maxFrameNo;
	}
}

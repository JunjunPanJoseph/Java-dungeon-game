package unsw.dungeon.EntitiesCollection;
/**
 * 
 * @author ASUS
 *
 */
public class MoveState {
	//Default direction is down for all direction
	public static enum Direction{Up, Down, Left, Right};
	private Entity entity;
	boolean rotateBeforeMove;
	private Direction direction;
	/**
	 * 
	 * @param entity
	 * @param rotateBeforeMove
	 * @param initDirection
	 */
	public MoveState(Entity entity, Boolean rotateBeforeMove, Direction initDirection){
		this.direction = initDirection;
		this.rotateBeforeMove = rotateBeforeMove;
		this.entity = entity;
	}
	/**
	 * Get the fount x coordinate
	 * @return
	 */
	public int frountX() {
		int x = entity.getX();
		switch(direction) {
		case Left:
			x -= 1;
			break;
		case Right:
			x += 1;
			break;
		default:
			break;
		}
		return x;
	}
	/**
	 * Get the fount y coordinate
	 * @return
	 */
	public int frountY() {
		int y = entity.getY();
		switch(direction) {
		case Up:
			y -= 1;
			break;
		case Down:
			y += 1;
			break;
		default:
			break;
		}
		return y;
	}
	/**
	 * Move toward
	 * @return
	 */
	public boolean moveToward() {
		int x = frountX();
		int y = frountY();
		return entity.getDungeon().entityMove(entity, x, y);
	}
	/**
	 * Move to the direction.
	 * @param initDirection
	 * @return
	 */
	public boolean move(Direction initDirection) {
		//If the object don't need change direction or move in correct direction
		if (direction == initDirection || !rotateBeforeMove) {
			direction = initDirection;
			return moveToward();
		} else {
			direction = initDirection;
			return true;
		}
	}
	/**
	 * Transfer the corrdinate x
	 * @param pair
	 * @return
	 */
	public int [] coordinateTransferX(int pair[]) {
		int x = pair[0];
		int y = pair[1];
		int newX = 0;
		int newY = 0;
		switch(direction) {
		case Up:
			newX = -x;
			newY = -y;
			break;
		case Down:
			newX = x;
			newY = y;
			break;
		case Left:
			newX = -y;
			newY = x;
			break;
		case Right:
			newX = y;
			newY = -x;
			break;
		}
		int result[] = {newX, newY};
		return result;
	}
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return the rotateBeforeMove
	 */
	public boolean isRotateBeforeMove() {
		return rotateBeforeMove;
	}

	/**
	 * @param rotateBeforeMove the rotateBeforeMove to set
	 */
	public void setRotateBeforeMove(boolean rotateBeforeMove) {
		this.rotateBeforeMove = rotateBeforeMove;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	/**
	 * Rotation
	 * @return
	 */
	public double getRotationDegrees() {
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
		return degree;
	}
}

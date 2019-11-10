package unsw.dungeon.EntitiesCollection.GroundScene;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.Interfaces.Enterable;

public class Door extends Entity implements Enterable {
	private int id;
    private IntegerProperty openState;
    /**
     * 
     * @param dungeon
     * @param x
     * @param y
     * @param id
     */
	public Door(Dungeon dungeon, int x, int y, int id) {
		super(dungeon, x, y);
		this.id = id;
		this.setLayer(3);
		this.imageProperty().set(dungeon.entityImage().getClosedDoorImage());
		//0 = closed, 1 = opened
		this.openState = new SimpleIntegerProperty(0);
		this.openState.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() == 0) {
                	imageProperty().set(dungeon.entityImage().getClosedDoorImage());
                } else {
                	imageProperty().set(dungeon.entityImage().getOpenedDoorImage());
                }
            }

        });
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Get the open state.
	 * @return
	 */
	public IntegerProperty openState() {
        return openState;
    }
	/**
	 * 
	 * @return
	 */
    public int getOpenState() {
        return openState().get();
    }
    /**
     * Open the door using the key.
     * @param id
     * @return
     */
    public boolean open(int id) {
    	if (id == this.id) {
    		openState.set(1);
    		return true;
    	} else {
    		return false;
    	}
    }
    /*
    public boolean close() {
		openState.set(0);
		return true;
    }
    */
	@Override
	/**
	 * If door is closed, not allow entity enter it.
	 */
	public boolean checkEnter(Entity e) {
		return getOpenState() != 0;
	}
	@Override
	public boolean checkLeave(Entity e) {
		return true;
	}
	@Override
	public void enter(Entity e) {}
	@Override
	public void leave(Entity e) {}
}

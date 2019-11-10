package unsw.dungeon.EntitiesCollection;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonConfig;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
	private int layer;
	private IntegerProperty x, y;
	private ObjectProperty<Image> imageProperty;
	protected Dungeon dungeon;
	private ImageView view;
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(Dungeon dungeon, int x, int y) {
    	this.layer = 0;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.imageProperty = new SimpleObjectProperty<Image>();
        this.dungeon = dungeon;
    }
    
    /**
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer) {
		if (layer <= 0 || layer >= DungeonConfig.MAXIMUM_LAYER) {
			System.out.println("Invalid layer size - " + layer);
		} else {
			this.layer = layer;
		}
	}

	public ObjectProperty<Image> imageProperty() {
    	return imageProperty;
    }
    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }
    public void setLoc(int x, int y) {
    	
    	this.x.set(x);
    	this.y.set(y);
    }
    public int getX() {
        return x().get();
    }
    public int getY() {
        return y().get();
    }
    public Dungeon getDungeon() {
    	return dungeon;
    }
	public void setView(ImageView view) {
		this.view = view;
	}
	public ImageView getView() {
		return view;
	}
}

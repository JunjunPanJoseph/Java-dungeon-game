package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import unsw.dungeon.EntitiesCollection.Entity;

public class DungeonGameLoader extends DungeonLoader {
	private GridPane squares;
	private List<List <ImageView>> initialLayer;
	/**
	 * Load the game from JSON map file.
	 * @param filename
	 * @param squares
	 * @throws FileNotFoundException
	 */
	public DungeonGameLoader(String filename, GridPane squares) throws FileNotFoundException {
		super(filename);
		// TODO Auto-generated constructor stub
		this.squares = squares;
		this.initialLayer = new ArrayList<>();
		for (int i = 0; i < DungeonConfig.MAXIMUM_LAYER; i++) {
			this.initialLayer.add(new ArrayList<ImageView>());
		}
	}
	/**
	 * Load the entity e.
	 */
	@Override
	public void onLoad(Entity e) {
		ImageView view = new ImageView();
		//Bind the picture to imageProperty for easier runtime changing picture.
		view.imageProperty().bind(e.imageProperty());
		addEntity(e, view);
		return;
	}
	/**
	 * Load the entity in run time.
	 */
	public void runtimeOnLoad(Entity e) {
		ImageView view = new ImageView();
		view.imageProperty().bind(e.imageProperty());
        trackPosition(e, view);
        e.setView(view);
		squares.add(view, e.getX(), e.getY());
		return;
	}
	/**
	 * Add view to entity and the initialLayer.
	 * @param entity
	 * @param view
	 */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entity.setView(view);
        initialLayer.get(entity.getLayer()).add(view);
    }
    /**
     * Load the entity in initialLayer to GridPane. 
     * @param dungeon
     */
    public void loadToGrid(Dungeon dungeon) {
        // Add the ground first so it is below all other entities
        Image ground = new Image("/dirt_0_new.png");
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y <  dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
		for (int i = 0; i < DungeonConfig.MAXIMUM_LAYER; i++) {
			for (ImageView imageView: initialLayer.get(i)) {
				squares.getChildren().add(imageView);
			}
		}
    }
	/**
	 * Set a node in a GridPane to have its position track the position of an
	 * entity in the dungeon.
	 *
	 * By connecting the model with the view in this way, the model requires no
	 * knowledge of the view and changes to the position of entities in the
	 * model will automatically be reflected in the view.
	 * @param entity
	 * @param node
	 */
	private void trackPosition(Entity entity, Node node) {
	    GridPane.setColumnIndex(node, entity.getX());
	    GridPane.setRowIndex(node, entity.getY());
	    try {
	    entity.x().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	            GridPane.setColumnIndex(node, newValue.intValue());
	        }
	    });
	    entity.y().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	            GridPane.setRowIndex(node, newValue.intValue());
	        }
	    });
	    } catch (Exception e) {
	    	
	    }
	}

}

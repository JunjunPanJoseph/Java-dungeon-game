package unsw.dungeon.EntitiesCollection.Character;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import unsw.dungeon.Dungeon;
/**
 * Character which will do something if timeframe changed.
 * @author ASUS
 *
 */
public abstract class CharacterWithTimeframeBehavior extends Character {
	private ChangeListener<Number> listener;
	private int actionPeriod;
	public CharacterWithTimeframeBehavior(Dungeon dungeon, int x, int y, int hp) {
		super(dungeon, x, y, hp);
		actionPeriod = 1;
		this.setWaitTicks(0);
		CharacterWithTimeframeBehavior myself = this;
		listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() % actionPeriod == 0) {
            		if (getWaitTicks() > 0) {
            			setWaitTicks(getWaitTicks() - 1);
            		} else if (getWaitTicks() == 0) {
            			myself.getView().setVisible(true);
            			setWaitTicks(getWaitTicks() - 1);
            		} else {
            			behavior(newValue.intValue());
            		}
                }
            }
        };
		dungeon.Frame().addListener(listener);
	}
	/**
	 * @return the actionPeriod
	 */
	public int getActionPeriod() {
		return actionPeriod;
	}
	/**
	 * @param actionPeriod the actionPeriod to set
	 */
	public void setActionPeriod(int actionPeriod) {
		this.actionPeriod = actionPeriod;
	}
	/**
	 * 
	 * @param frameNo
	 */
	public abstract void behavior(int frameNo);
	/**
	 * @return the listener
	 */
	public ChangeListener<Number> getListener() {
		return listener;
	}
}

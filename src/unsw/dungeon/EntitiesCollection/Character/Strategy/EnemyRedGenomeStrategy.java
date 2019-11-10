package unsw.dungeon.EntitiesCollection.Character.Strategy;

import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.BombLit;
import unsw.dungeon.EntitiesCollection.Character.Enemy.Enemy;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.Interfaces.BehaviorStrategy;
/**
 * 
 * @author ASUS
 *
 */
public class EnemyRedGenomeStrategy implements BehaviorStrategy {
	private Enemy enemy;
	private MoveStrategy moveStrategy;
	private boolean activated;
	/**
	 * Strategy for red genome
	 * @param enemy
	 */
	public EnemyRedGenomeStrategy(Enemy enemy) {
		this.enemy = enemy;
		moveStrategy = new MoveStrategy(enemy.getDungeon(), enemy);
		this.activated = false;
	}
	@Override
	/**
	 * Throw bomber
	 */
	public void behavior(int frameNo) {
		int distToPlayer = moveStrategy.toPlayer(0, 0);
		if (distToPlayer < 10) {
			activated = true;
		}
		if (!activated) {
			return;
		}
		Player player = enemy.getDungeon().getPlayer();
		Random random = new Random();
		switch (random.nextInt(3)) {
		case 0:
			throwBomberBehavior(player.getX(), player.getY());
			break;
		default:
			moveStrategy.moveToPlayerBehavior();
			break;
		}
	}
	/**
	 * Throw bomber
	 * @param x
	 * @param y
	 */
	private void throwBomberBehavior(int x, int y) {
		Dungeon dungeon = enemy.getDungeon();
		ImageView bomber = new ImageView();
		bomber.setX(32 * enemy.getX());
		bomber.setY(32 * enemy.getY());
		bomber.setImage(dungeon.entityImage().getBombImage());
		dungeon.addToAnimationLayer(bomber);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		class xLocInterpolator extends Interpolator {
		    @Override
		    protected double curve(double t) {
		    	return t ;
		    }
		}
		class yLocInterpolator extends Interpolator {
			@Override
			protected double curve(double t) {
				return t;
			}
			@Override
			public double interpolate(double startValue, double endValue, double fraction){
				double block = 1 * 32;
				double b = 4 * (endValue - block - startValue) - (endValue - startValue);
				double a = endValue - startValue - b;
				double c = startValue;
				double f = fraction;
				double result = a * f*f + b * f + c; 
				return result;
			}
		}
		KeyValue keyValueX = new KeyValue(bomber.xProperty(), x * 32, new xLocInterpolator());
		KeyValue keyValueY = new KeyValue(bomber.yProperty(), y * 32, new yLocInterpolator());
		KeyFrame kf = new KeyFrame(Duration.millis(1500), keyValueX, keyValueY);
		
        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
					dungeon.deleteToAnimationLayer(bomber);
					BombLit bombLit = new BombLit(dungeon, x, y);
					bombLit.setDamage(2);
					dungeon.addEntityRuntime(bombLit);
            }
        };
		KeyFrame kf2 = new KeyFrame(Duration.millis(1500), onFinished);
		timeline.getKeyFrames().add(kf);
		timeline.getKeyFrames().add(kf2);
		timeline.play();
	}

}

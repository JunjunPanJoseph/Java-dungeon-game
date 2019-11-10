package unsw.dungeon.EntitiesCollection.Character.Strategy;

import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Entity;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
import unsw.dungeon.EntitiesCollection.Character.AttackAnimate.BombLit;
import unsw.dungeon.EntitiesCollection.Character.Enemy.EnemyHound;
import unsw.dungeon.EntitiesCollection.MoveState.Direction;
import unsw.dungeon.animation.EntityAnimation;
/**
 * Attack strategy
 * @author ASUS
 *
 */
public class AttackStrategy {
	private Dungeon dungeon;
	private CharacterWithTimeframeBehavior character;
	/**
	 * 
	 * @param dungeon
	 * @param character
	 */
	public AttackStrategy(Dungeon dungeon, CharacterWithTimeframeBehavior character) {
		this.dungeon = dungeon;
		this.character = character;
	}
	/**
	 * summon the entity in dungeon.
	 * @param e
	 */
	private void summon(Entity e) {
		dungeon.addEntityRuntime(e);
	}
	/**
	 * Return a list of pairs that are randomly picked in given area.
	 * @param loc
	 * @param nPoints
	 * @param radius
	 * @param center
	 * @return
	 */
	private List<CoordinatePair> randLocAround(CoordinatePair loc, int nPoints, int radius, boolean center) {
		List <CoordinatePair> resultPoints = new ArrayList<>();
		Random random = new Random();
		int i = 0;
		while (i < nPoints) {
			int randX = random.nextInt(radius);
			int randY = random.nextInt(radius);
			if (!center) {
				if (randX == 0 && randY == 0) {
					continue;
				}
			}
			if (random.nextBoolean()) {
				randX *= -1;
			}
			if (random.nextBoolean()) {
				randY *= -1;
			}
			CoordinatePair newPair = new CoordinatePair(loc.x + randX, loc.y + randY);
			if (newPair.x < 0 || newPair.x >= dungeon.getWidth()
				|| newPair.y < 0 || newPair.y >= dungeon.getHeight()) {
				continue;
			} else {
				resultPoints.add(newPair);
				i++;
			}
		}
		return resultPoints;
	}
	/**
	 * Abs function
	 * @param n
	 * @return
	 */
	private int abs(int n) {
		return n < 0? -n:n;
	}
	/**
	 * Summon bomberlit.
	 */
	public void summonExplosion() {
		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				if (abs(x) == abs(y) && abs(x) == 2){
					continue;
				}
				if (abs(x) < 2 && abs(y) < 2 && !(abs(x) == 1 && abs(y) == 1)) {
					continue;
				}

				if (dungeon.check( x + character.getX(), y + character.getY())) {
					BombLit newBombLit = new BombLit(dungeon, x + character.getX(), y + character.getY() );
					newBombLit.setDamage(2);
					newBombLit.explodeNow();
					summon(newBombLit);
				}
			}
		}
	}
	
	/**
	 * Summon hound in the area.
	 * @param x
	 * @param y
	 * @param n
	 * @param radius
	 */
	public void summonHoundRand(int x, int y, int n, int radius) {
		List<CoordinatePair> summonLoc = randLocAround(new CoordinatePair(x, y), 
				n, radius, false);
		for (CoordinatePair pair: summonLoc) {
			if (dungeon.check(pair.x, pair.y)) {
				EnemyHound hound = new EnemyHound(dungeon, pair.x, pair.y);
				summon(hound);
			}
		}
	}
	/**
	 * summon fire boulder in given area.
	 * @param x
	 * @param y
	 * @param n
	 * @param radius
	 */
	public void summonFireBouderRand(int x, int y, int n, int radius) {
		List<CoordinatePair> summonLoc = randLocAround(new CoordinatePair(x, y), 
				n, radius, false);
		for (CoordinatePair pair: summonLoc) {
			if (dungeon.checkMove(null, pair.x, pair.y, false, true, false)) {
				fireBouder(pair.x, pair.y);
			}
		}
	}
	/**
	 * summon one fire boulder attack in (x,y).
	 * @param x
	 * @param y
	 */
	private void fireBouder(int x, int y){
		EntityAnimation fireBoulderAnimation = dungeon.entityAnimation().getFireBoulder().
				setupEntityAnimation(dungeon, 144, x, y, Direction.Down);
		fireBoulderAnimation.setUpAttack(dungeon.entityImage().getFireBoulderJson(),
				character, 999);
		fireBoulderAnimation.startTimeline();
	}
	
	/**
	 * Sickle attack.
	 * @param x
	 * @param y
	 * @param frame_ms
	 */
	public void sickleAttack(int x, int y, int frame_ms){
		EntityAnimation sickleAttackAnimation = dungeon.entityAnimation().getSickleAttack().
				setupEntityAnimation(dungeon, 200, x, y, Direction.Down);
		sickleAttackAnimation.setUpAttack(dungeon.entityImage().getSickleAttackJson(),
				character, 1);
		sickleAttackAnimation.startTimeline();
		character.setWaitTicks(4);
	}
	/**
	 * Sickle attack, then summon bomber around it.
	 * @param cd
	 * @param frame_ms
	 */
	public void bomberSickleAttack(int cd, int frame_ms) {
		sickleAttack(character.getX(), character.getY(), frame_ms);
        EventHandler<ActionEvent> bomberSickleAttack = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
        		summonExplosion();
            }
        };
        cast(cd, frame_ms * 32, bomberSickleAttack);
	}
	/**
	 * Set up cast event. During the cast, entity can not move or do other thing.
	 * @param cd
	 * @param castTime_ms
	 * @param onFinished
	 */
	private void cast(int cd, int castTime_ms, EventHandler<ActionEvent> onFinished) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		KeyFrame kf = new KeyFrame(Duration.millis(castTime_ms), onFinished);
		timeline.getKeyFrames().add(kf);
		timeline.play();
		character.setWaitTicks(cd);
		
	}
	/**
	 * Summon Hound around entity.
	 * If entity be attacked during casting, the summon will be canceled.
	 * @param cd
	 * @param frame_ms
	 */
	public void summonHoundStrategy(int cd, int frame_ms) {
		EntityAnimation castSummonHound = dungeon.entityAnimation().getCastSummonHound().
				setupEntityAnimation(dungeon, frame_ms, character.getX(), character.getY(), Direction.Down);
		castSummonHound.startTimeline();
		character.setLastDamageTarget(null);
        EventHandler<ActionEvent> summonHound = new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
        		// not been attacked during the period
				if (character.getLastDamageTarget() == null) {
					summonHoundRand(character.getX(), character.getY(), 3, 3);
				}
            }
        };
        cast(cd, frame_ms * 18, summonHound);
	}
	/**
	 * Summon random fire boulder attack in given area.
	 * @param cd
	 * @param cast
	 * @param frame_ms
	 * @param n
	 * @param radius
	 */
	public void summonFireBouderAttack(int cd, boolean cast, int frame_ms, int n, int radius) {		
		if (cast) {
			EntityAnimation castFireBoulder = dungeon.entityAnimation().getCastFireBoulder().
					setupEntityAnimation(dungeon, frame_ms, character.getX(), character.getY(), Direction.Down);
			castFireBoulder.startTimeline();
	        EventHandler<ActionEvent> fireBouderAttack = new EventHandler<ActionEvent>() {
	        	@Override
	            public void handle(ActionEvent t) {
	        		summonFireBouderRand(dungeon.getPlayer().getX(), dungeon.getPlayer().getY(), n, radius);
	            }
	        };
	        cast(cd, frame_ms * 18, fireBouderAttack);
		} else {
			summonFireBouderRand(dungeon.getPlayer().getX(), dungeon.getPlayer().getY(), n, radius);
			character.setWaitTicks(cd);
		}
	}
	
	
}

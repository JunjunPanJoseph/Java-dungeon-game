package unsw.dungeon.EntitiesCollection.Character.Strategy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.CharacterWithTimeframeBehavior;
/**
 * Move strategy.
 * @author ASUS
 *
 */
public class MoveStrategy {
	private Dungeon dungeon;
	private CharacterWithTimeframeBehavior character;
	private Deque<Pair> path;	
	/**
	 * 
	 * @param dungeon
	 * @param character
	 */
	public MoveStrategy(Dungeon dungeon, CharacterWithTimeframeBehavior character) {
		this.dungeon = dungeon;
		this.character = character;
		this.path = new ArrayDeque<>();
	}
	/**
	 * Pair with distance. For local use of A* graph search algorithm.
	 * @author ASUS
	 *
	 */
	class Pair extends CoordinatePair{
		public Pair(int x, int y, double currDist, double distToPlayer, Pair pre){
			super(x, y);
			this.currDist = currDist;
			this.distToPlayer = distToPlayer;
			this.pre = pre;
		}
		public double currDist;
		public double distToPlayer;
		public Pair pre;
	}
	/**
	 * Pick a location to move.
	 */
	public void moveRandomly() {
		resetPath();
		Random random = new Random();
		int randomNum = random.nextInt();
		boolean retVal = false;
		for(int i = 0; i < 4; i++) {
			switch ((i + randomNum) % 4) {
				case 0:
					retVal = character.moveUp();
					break;
				case 1:
					retVal = character.moveDown();
					break;
				case 2:
					retVal = character.moveLeft();
					break;
				case 3:
					retVal = character.moveRight();
					break;
			}
			if (retVal) {
				break;
			}
		}
	}
	/**
	 * Calculate the distance to player. x, y are offset.
	 * @param x
	 * @param y
	 * @return
	 */
	public int toPlayer(int x, int y) {
		return Math.abs(x + character.getX() - dungeon.getPlayer().getX()) 
				+ Math.abs(y + character.getY() - dungeon.getPlayer().getY()) ;
	}
	/**
	 * A* search: search the path to player.
	 * @param maxPathLen
	 * @param wPlayer
	 * @param wCurr
	 */
	public void astarSearch(double maxPathLen, double wPlayer, double wCurr) {
		int radius = 5;
		int size = radius * 2 + 1;
		int characterX = character.getX();
		int characterY = character.getY();
		List<List<Pair>> pairArray = new ArrayList<>();
		for (int y = -1 * radius; y <= radius; y++) {
			List<Pair> newColumn = new ArrayList<>();
			for (int x = -1 * radius; x <= radius; x++) {
				newColumn.add(new Pair(x + characterX, y + characterY, 
						999999, (double) toPlayer(x, y) , null));
			}
			pairArray.add(newColumn);
		}
		
		class ComparetorPair implements Comparator<Pair> {
			 
			public int compare(Pair p1, Pair p2) {
				int result = 0;
				if (wCurr * p1.currDist + wPlayer * p1.distToPlayer == 
						wCurr * p1.currDist + wPlayer * p2.distToPlayer) {
					result = 0;
				} else if (wCurr * p1.currDist + wPlayer * p1.distToPlayer > 
						wCurr * p1.currDist + wPlayer * p2.distToPlayer) {
					result = 1;
				} else {
					result = -1;
				}
				return result;
			}
		}
		
		PriorityQueue <Pair> pq = new PriorityQueue<>(new ComparetorPair());
		PriorityQueue <Pair> pqSecResult = new PriorityQueue<>(new ComparetorPair());
		//get center element
		Pair currentPair = pairArray.get(radius).get(radius);
		currentPair.currDist = 0;
		currentPair.pre = null;
		double dist = 0;
		pq.add(currentPair);
		Pair result = null;
		while (pq.size() > 0) {
			currentPair = pq.poll();
			if (currentPair.x == dungeon.getPlayer().getX() && currentPair.y == dungeon.getPlayer().getY()) {
				result = currentPair;
				break;
			}
			
			int indexX = currentPair.x - characterX + radius;
			int indexY = currentPair.y - characterY + radius;
			addPairToPq(pairArray, pq, pqSecResult, currentPair, dist, indexX - 1, indexY, size, radius);
			dist = addPairToPq(pairArray, pq, pqSecResult, currentPair, dist, indexX + 1, indexY, size, radius);
			dist = addPairToPq(pairArray, pq, pqSecResult, currentPair, dist, indexX, indexY - 1, size, radius);
			dist = addPairToPq(pairArray, pq, pqSecResult, currentPair, dist, indexX, indexY + 1, size, radius);
		}
		if(result == null) {
			result = pqSecResult.poll();
		}
		path.removeAll(path);
		while (result != null) {
			path.push(result);
			result = result.pre;
		}
		while(path.size() > maxPathLen) {
			path.removeLast();
		}
		path.poll();
	}
	/**
	 * Add the current pair to priority queue and reset distance.
	 * @param pairArray
	 * @param pq
	 * @param pqSecResult
	 * @param currentPair
	 * @param dist
	 * @param indexX
	 * @param indexY
	 * @param size
	 * @param radius
	 * @return
	 */
	private double addPairToPq(List<List<Pair>> pairArray, PriorityQueue<Pair> pq, PriorityQueue<Pair> pqSecResult,
			Pair currentPair, double dist, int indexX, int indexY, int size, int radius) {
		if (indexX >= 0 && indexY >= 0 && indexX < size && indexY < size &&
				dungeon.checkMove(character, indexX - radius + character.getX(),
						indexY - radius + character.getY(), false, true, false)) {
			if (pairArray.get(indexY).get(indexX).currDist > currentPair.currDist + 1) {
				pairArray.get(indexY).get(indexX).currDist = currentPair.currDist + 1;
				pairArray.get(indexY).get(indexX).pre = currentPair;
				pq.add(pairArray.get(indexY).get(indexX));
				if (currentPair.currDist + 1 == dist) {
					pqSecResult.add(pairArray.get(indexY).get(indexX));
				} else if (currentPair.currDist + 1 > dist){
					dist = currentPair.currDist + 1;
					pqSecResult.removeAll(pqSecResult);
					pqSecResult.add(pairArray.get(indexY).get(indexX));
				}
			}
		}
		return dist;
	}
	/**
	 * Move along with pre-set path.
	 * @return
	 */
	public boolean moveWithPath() {
		if (path == null) {
			return false;
		} else {
			Pair nextStep = path.pop();
			return dungeon.entityMove(character, nextStep.x, nextStep.y);
		}
	}
	/**
	 * Behavior of move to player.
	 */
	public void moveToPlayerBehavior() {
		if (path == null || path.size() == 0) {
			this.astarSearch(50, 0.4, 0.6);
		}
		if (path == null || path.size() == 0) {
			moveRandomly();
		} else {
			if (moveWithPath() == false) {
				this.astarSearch(50, 0.4, 0.6);
			}
		}
	}
	/**
	 * Behavior of move away from player.
	 */
	public void moveAwayFromPlayerBehavior() {
		resetPath();
		class ComparetorPair implements Comparator<Pair> {
			public int compare(Pair p1, Pair p2) {
				int result = 0;
				if (p1.distToPlayer == p2.distToPlayer) {
					result = 0;
				} else if (p1.distToPlayer < p2.distToPlayer) {
					result = 1;
				} else {
					result = -1;
				}
				return result;
			}
		}
		PriorityQueue <Pair> pq = new PriorityQueue<>(new ComparetorPair());
		pq.add(new Pair(character.getX() + 1, character.getY(), 0, toPlayer(0, 0), null));
		pq.add(new Pair(character.getX() - 1, character.getY(), 0, toPlayer(0, 0), null));
		pq.add(new Pair(character.getX(), character.getY() + 1, 0, toPlayer(0, 0), null));
		pq.add(new Pair(character.getX(), character.getY() - 1, 0, toPlayer(0, 0), null));
		
		while (pq.size() > 0) {
			Pair curr = pq.poll();
			if (curr != null && dungeon.entityMove(character, curr.x, curr.y)) {
				return;
			}
		}
		return;
	}
	/**
	 * Reset current path.
	 */
	private void resetPath() {
		path.removeAll(path);
	}
}

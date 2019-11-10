package unsw.dungeon.EntitiesCollection;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonGameLoader;
import unsw.dungeon.Controller.GameDungeonController;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.Goals.Goals;

class RunningDungeonTest {
	private DungeonGameLoader dungeonLoader;
	private GameDungeonController controller;
	private Dungeon dungeon;
	private Player player;
	private Pack pack;
	private Goals goals;
	private void Loading(String fileName){
		try {
			dungeonLoader = new DungeonGameLoader(fileName, null);
			controller = null;
			dungeon = controller.getDungeon();
			player = dungeon.getPlayer();
			pack = player.getPack();
			goals = dungeon.getGoals();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void checkPlayerLoc(int x, int y) {
		assertEquals(player.getX(), x);
		assertEquals(player.getY(), y);
	}
	@Test
	void testBomberEnemyExit() {
		Loading("JUnitTest/bomberEnemyExit.json");
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		player.pickUpItem();
		player.moveDown();
		player.pickUpItem();
		player.moveDown();
		player.pickUpItem();
		assertEquals(pack.getSize(), 2);
		checkPlayerLoc(0, 2);
		//Can not push boulder - because there is an enemy behind it
		player.moveDown();
		checkPlayerLoc(0, 2);
		//try my best to use sword to break boulder
		player.useItem("");
		player.moveDown();
		assertEquals(pack.getSize(), 2);
		//useless
		checkPlayerLoc(0, 2);
		pack.toLeft();
		System.out.println(pack.getCurrItem().getName());
		player.useItem("");
		//leave!!!
		player.moveUp();
		player.moveUp();
		player.moveUp();
		for(int i = 0; i < 3; i++) {
			assertEquals(player.getHp(), 1);
			dungeon.nextFrame();
		}
		assertEquals(player.getHp(), 1);
		checkPlayerLoc(0, 0);
		player.moveDown();
		checkPlayerLoc(0, 0);
		player.moveDown();
		checkPlayerLoc(0, 1);
		player.moveDown();
		checkPlayerLoc(0, 2);
		player.moveDown();
		checkPlayerLoc(0, 3);
		
		player.useItem("");
		//leave!!!
		player.moveUp();
		player.moveUp();
		player.moveUp();
		for(int i = 0; i < 3; i++) {
			assertEquals(player.getHp(), 1);
			dungeon.nextFrame();
		}
		assertEquals(player.getHp(), 1);
		assertEquals(pack.getSize(), 1);
		checkPlayerLoc(0, 1);
		
		player.moveDown();
		
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		checkPlayerLoc(0, 4);
		assertEquals(goals.checkWin(), false);
		player.moveDown();
		checkPlayerLoc(0, 5);
		assertEquals(goals.checkWin(), true);
		player.moveDown();
		assertEquals(goals.checkWin(), false);
		player.moveUp();
		player.moveUp();
		assertEquals(goals.checkWin(), true);
	}
	
	void testBomberKillMyself() {
		Loading("JUnitTest/bomberEnemyExit.json");
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		assertEquals(player.getHp(), 1);
		player.moveDown();
		assertEquals(pack.getSize(), 0);
		player.pickUpItem();
		assertEquals(pack.getSize(), 1);
		player.useItem("");
		assertEquals(pack.getSize(), 0);
		for(int i = 0; i < 3; i++) {
			assertEquals(player.getHp(), 1);
			dungeon.nextFrame();
		}
		assertEquals(player.getHp(), 0);
	}
	@Test
	void testTreasureKeyDoor() {
		Loading("JUnitTest/treasureKeyDoor.json");
		//push boulder
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		player.pickUpItem();
		player.moveDown();
		player.pickUpItem();
		player.moveDown();
		player.pickUpItem();
		player.moveDown();
		assertEquals(pack.getSize(), 3);
		//door is locked
		player.moveDown();
		checkPlayerLoc(0, 2);
		player.moveDown();
		checkPlayerLoc(0, 2);
		//Wrong key
		player.useItem("");
		player.moveDown();
		checkPlayerLoc(0, 2);
		//not key
		pack.toLeft();
		player.useItem("");
		player.moveDown();
		checkPlayerLoc(0, 2);
		//correct key
		pack.toLeft();
		player.useItem("");
		player.moveDown();
		checkPlayerLoc(0, 3);
		//used key
		assertEquals(pack.getSize(), 2);
		//not key
		player.useItem("");
		player.moveDown();
		checkPlayerLoc(0, 3);
		//correct key
		pack.toRight();
		player.useItem("");
		player.moveDown();
		checkPlayerLoc(0, 4);
		player.moveDown();
		checkPlayerLoc(0, 5);
		assertEquals(goals.checkWin(), false);
		assertEquals(goals.treasure().intValue(), 1);
		player.pickUpItem();
		assertEquals(goals.treasure().intValue(), 0);
		assertEquals(goals.checkWin(), true);
	}
	@Test
	void testInvincibility() {
		Loading("JUnitTest/Invincibility.json");
		//push boulder
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		assertEquals(goals.enemies().intValue(), 4);
		assertEquals(player.getInvincibleTime(), 0);
		player.pickUpItem();
		assertEquals(pack.getSize(), 0);
		assertEquals(player.getInvincibleTime(), 10);
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		assertEquals(goals.enemies().intValue(), 0);
		assertEquals(goals.checkWin(), true);
		assertEquals(player.getHp(), 1);
	}
	@Test
	void testInvincibilityTime() {
		Loading("JUnitTest/Invincibility.json");
		//push boulder
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		assertEquals(goals.enemies().intValue(), 4);
		assertEquals(player.getInvincibleTime(), 0);
		player.pickUpItem();
		assertEquals(pack.getSize(), 0);
		assertEquals(player.getInvincibleTime(), 10);
		for (int i = 1; i <= 10; i++) {
			dungeon.nextFrame();
			assertEquals(player.getInvincibleTime(), 10 - i);
		}
	}
	
	@Test
	void testSword() {
		Loading("JUnitTest/sword.json");
		//push boulder
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		assertEquals(goals.enemies().intValue(), 4);
		player.pickUpItem();
		assertEquals(pack.getSize(), 1);
		assertEquals(pack.getCurrItem().getName(), "Sword");
		player.useItem("");
		assertEquals(goals.enemies().intValue(), 3);
		player.moveDown();
		player.useItem("");
		assertEquals(goals.enemies().intValue(), 2);
		player.moveDown();
		player.useItem("");
		assertEquals(goals.enemies().intValue(), 1);
		player.moveDown();
		assertEquals(goals.checkWin(), false);
		player.useItem("");
		assertEquals(goals.enemies().intValue(), 0);
		assertEquals(goals.checkWin(), true);
	}
	@Test
	void testSwordBreak() {
		Loading("JUnitTest/sword.json");
		//push boulder
		assertEquals(goals.checkWin(), false);
		checkPlayerLoc(0, 0);
		assertEquals(goals.enemies().intValue(), 4);
		player.pickUpItem();
		assertEquals(pack.getSize(), 1);
		assertEquals(pack.getCurrItem().getName(), "Sword");
		player.useItem("");
		assertEquals(goals.enemies().intValue(), 3);
		player.useItem("");
		player.useItem("");
		player.dropItem();
		player.pickUpItem();
		player.useItem("");
		player.useItem("");
		assertEquals(pack.getSize(), 0);
		player.moveDown();
		player.useItem("");
		player.moveDown();
		assertEquals(goals.enemies().intValue(), 3);
		assertEquals(player.getHp(), 0);
	}
	@Test
	void testBouldersWall() {
		Loading("JUnitTest/boulders.json");
		//push boulder
		checkPlayerLoc(0, 0);
		player.moveDown();
		checkPlayerLoc(0, 0);
		player.moveDown();
		checkPlayerLoc(0, 1);
		//Acceptance criteria: cannot push more then 1 boundle
		player.moveRight();
		checkPlayerLoc(0, 1);
		player.moveRight();
		checkPlayerLoc(0, 1);
		player.moveRight();
		checkPlayerLoc(0, 1);
		
		//trigger Switch
		player.moveDown();
		assertEquals(goals.switches().intValue(), 3);
		player.moveDown();
		checkPlayerLoc(0, 1);
		assertEquals(goals.switches().intValue(), 2);
		player.moveDown();
		checkPlayerLoc(0, 2);
		player.moveUp();
		player.moveUp();
		player.moveUp();
		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		checkPlayerLoc(2, 0);
		player.moveDown();
		checkPlayerLoc(2, 1);
		//Can not push Wall
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		checkPlayerLoc(3, 1);
		player.moveLeft();
		player.moveLeft();
		player.moveDown();
		player.moveDown();
		assertEquals(goals.switches().intValue(), 1);
		player.moveUp();
		player.moveUp();
		player.moveUp();
		player.moveUp();
		player.moveLeft();
		player.moveLeft();
		checkPlayerLoc(1, 0);
		player.moveDown();
		player.moveDown();
		player.moveDown();
		assertEquals(goals.checkWin(), false);
		player.moveDown();
		player.moveDown();
		assertEquals(goals.switches().intValue(), 0);
		assertEquals(goals.checkWin(), true);
	}

}

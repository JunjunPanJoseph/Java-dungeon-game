package unsw.dungeon.EntitiesCollection.Pickable;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.EntitiesCollection.Character.Player.Pack;
import unsw.dungeon.EntitiesCollection.Character.Player.Player;
import unsw.dungeon.EntitiesCollection.Pickable.Items.*;
import unsw.dungeon.EntitiesCollection.Pickable.Items.Weapons.Sword;

class PickableEntityTest {
	Dungeon dungeon;
	Pack pack;
	@BeforeEach
	void setUp() throws Exception {
		dungeon = new Dungeon(1, 1, null);
		pack = new Pack(new Player (dungeon, 0, 0, 1));
	}

	@Test
	void testTreasure() {
		pack.addToPack(new Treasure(dungeon, 0, 0));
		Item currItem = pack.getCurrItem();
		//Add one
		assertEquals(currItem.getNumItem(), 1);
		assertEquals(pack.getSize(), 1);
		//Use
		assertEquals(pack.useCurrItem(""), false);
		
		//Add more
		for (int i = 2; i < 10; i++) {
			pack.addToPack(new Treasure(dungeon, 0, 0));
			assertEquals(currItem.getNumItem(), i);
		}
	}
	@Test
	void testBomb() {
		pack.addToPack(new Bomb(dungeon, 0, 0));
		Item currItem = pack.getCurrItem();
		//Add one
		assertEquals(currItem.getNumItem(), 1);		
		assertEquals(pack.getSize(), 1);
		//Add more
		for (int i = 2; i < 10; i++) {
			pack.addToPack(new Bomb(dungeon, 0, 0));
			assertEquals(pack.getSize(), 1);
			assertEquals(currItem.getNumItem(), i);
		}
	}

	@Test
	void testKey() {
		pack.addToPack(new Key(dungeon, 0, 0, 0));
		Item currItem = pack.getCurrItem();
		//Add one
		assertEquals(currItem.getNumItem(), 1);		
		assertEquals(pack.getSize(), 1);
		//Add more
		for (int i = 2; i < 10; i++) {
			pack.addToPack(new Key(dungeon, 0, 0, i));
			assertEquals(pack.getSize(), i);
			assertEquals(currItem.getNumItem(), 1);
		}
	}
	@Test
	void testSword() {
		pack.addToPack(new Sword(dungeon, 0, 0));
		Sword currItem = (Sword) pack.getCurrItem();
		//Add one
		assertEquals(currItem.getNumItem(), 1);
		assertEquals(currItem.getDurability(), 5);
		assertEquals(pack.getSize(), 1);
		currItem.use(pack, "");
		assertEquals(currItem.getDurability(), 4);
		//Add more
		for (int i = 2; i < 10; i++) {
			assertEquals(pack.checkAddToPack(new Sword(dungeon, 0, 0)), false);
			assertEquals(pack.getSize(), 1);
			assertEquals(currItem.getNumItem(), 1);
			assertEquals(currItem.getDurability(), 4);
		}
		currItem.use(pack, "");
		assertEquals(currItem.getDurability(), 3);
		currItem.use(pack, "");
		assertEquals(currItem.getDurability(), 2);
		currItem.use(pack, "");
		assertEquals(currItem.getDurability(), 1);
		currItem.use(pack, "");
		assertEquals(pack.getSize(), 0);
		assertEquals(pack.checkAddToPack(new Sword(dungeon, 0, 0)), true);
		
	}
	
}
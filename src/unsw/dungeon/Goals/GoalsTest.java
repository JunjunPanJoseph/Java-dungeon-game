package unsw.dungeon.Goals;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoalsTest {
	Goals goals;
	@BeforeEach
	void setUp() throws Exception {
		goals = new Goals(null);
	}
 
	@Test
	void testexit() {
		String goalText = 
				"{\"goal\": \"exit\" }";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "exit");
		//Condition check
		goals.exitAdd();
		assertEquals(goals.checkGoals(), false);
		goals.exitSub();
		assertEquals(goals.checkGoals(), true);
		goals.exitAdd();
		assertEquals(goals.checkGoals(), false);
		goals.exitAdd();
		assertEquals(goals.checkGoals(), false);
		goals.exitSub();
		assertEquals(goals.checkGoals(), true);
	}
	@Test
	void testenemies() {
		String goalText = 
				"{\"goal\": \"enemies\" }";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "enemies");
		//Condition check
		goals.enemiesAdd();
		goals.enemiesAdd();
		assertEquals(goals.checkGoals(), false);
		goals.enemiesSub();
		assertEquals(goals.checkGoals(), false);
		goals.enemiesSub();
		assertEquals(goals.checkGoals(), true);
	}
	@Test
	void testExit() {
		String goalText = 
				"{\"goal\": \"boulders\" }";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "boulders");
		//Condition check
		goals.switchesAdd();
		goals.switchesAdd();
		assertEquals(goals.checkGoals(), false);
		goals.switchesSub();
		assertEquals(goals.checkGoals(), false);
		goals.switchesSub();
		assertEquals(goals.checkGoals(), true);
	}
	@Test
	void testtreasure() {
		String goalText = 
				"{\"goal\": \"treasure\" }";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "treasure");
		//Condition check
		goals.treasureAdd();
		goals.treasureAdd();
		assertEquals(goals.checkGoals(), false);
		goals.treasureSub();
		assertEquals(goals.checkGoals(), false);
		goals.treasureSub();
		assertEquals(goals.checkGoals(), true);
	}
	@Test
	void testor() {
		String goalText = 
				"{\"goal\": \"OR\", \"subgoals\": [ {\"goal\": \"enemies\" },{\"goal\": \"treasure\" }]}";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "OR");
		//Condition check
		goals.enemiesAdd();
		goals.treasureAdd();
		assertEquals(goals.checkGoals(), false);
		goals.enemiesSub();
		assertEquals(goals.checkGoals(), true);
		goals.treasureSub();
		assertEquals(goals.checkGoals(), true);
		goals.treasureAdd();
		assertEquals(goals.checkGoals(), true);
	}
	@Test
	void testand() {
		String goalText = 
				"{\"goal\": \"AND\", \"subgoals\": [ {\"goal\": \"enemies\" },{\"goal\": \"treasure\" }]}";
		JSONObject jsonGoals = new JSONObject(goalText);
		goals.loadGoals(jsonGoals);
		//Loading check
		assertEquals(goals.getGoals().getGoal(), "AND");
		//Condition check
		goals.enemiesAdd();
		goals.treasureAdd();
		assertEquals(goals.checkGoals(), false);
		goals.enemiesSub();
		assertEquals(goals.checkGoals(), false);
		goals.treasureSub();
		assertEquals(goals.checkGoals(), true);
		goals.treasureAdd();
		assertEquals(goals.checkGoals(), false);
	}
}
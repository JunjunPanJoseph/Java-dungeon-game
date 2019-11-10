package unsw.dungeon.animation;

import unsw.dungeon.Dungeon;
/**
 * Storing all timeframe animation of the game.
 * @author ASUS
 *
 */
public class AnimationManager {
	private TimeframeAnimation BombExploding;
	private TimeframeAnimation SwordAttack;
	private TimeframeAnimation FireBoulder;
	private TimeframeAnimation BowAttack;
	private TimeframeAnimation SickleAttack;
	private TimeframeAnimation CastFireBoulder;
	private TimeframeAnimation CastSummonHound;
	/**
	 * 
	 * @param dungeon
	 */
	public AnimationManager(Dungeon dungeon){
		 BombExploding = new TimeframeAnimation(dungeon.entityImage().getBombExploding(), 13, 0, 1, 0);
		 SickleAttack = new TimeframeAnimation(dungeon.entityImage().getSickleAttack(), 17, 1, 1, 0);
		 SwordAttack = new TimeframeAnimation(dungeon.entityImage().getSwordAttack(), 14, 1, 0, 0);
		 FireBoulder = new TimeframeAnimation(dungeon.entityImage().getFireBoulder(), 20, 1, 8, 0);
		 BowAttack = new TimeframeAnimation(dungeon.entityImage().getBowAttack(), 5, 0, 0, -135);
		 CastFireBoulder = new TimeframeAnimation(dungeon.entityImage().getCastFireBoulder(), 18, 1, 1, 0);
		 CastSummonHound = new TimeframeAnimation(dungeon.entityImage().getCastSummonHound(), 18, 1, 1, 0);
	}
	/**
	 * @return the fireBoulder
	 */
	public TimeframeAnimation getFireBoulder() {
		return FireBoulder;
	}
	/**
	 * @return the swordAttack
	 */
	public TimeframeAnimation getSwordAttack() {
		return SwordAttack;
	}
	/**
	 * @return the bombExploding
	 */
	public TimeframeAnimation getBombExploding() {
		return BombExploding;
	}
	/**
	 * @return the BowAttack
	 */
	public TimeframeAnimation getBowAttack() {
		return BowAttack;
	}
	/**
	 * @return the SickleAttack
	 */
	public TimeframeAnimation getSickleAttack() {
		return SickleAttack;
	}
	/**
	 * @return the castFireBoulder
	 */
	public TimeframeAnimation getCastFireBoulder() {
		return CastFireBoulder;
	}
	/**
	 * @return the castSummonHound
	 */
	public TimeframeAnimation getCastSummonHound() {
		return CastSummonHound;
	}
	
}

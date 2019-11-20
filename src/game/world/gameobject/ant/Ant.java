package game.world.gameobject.ant;

import engine.world.GameSystem;
import engine.world.gameobject.ComponentAIBehaviorTree;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;

public abstract class Ant extends GameObject {

	private int antId;
	private boolean alive;

	public Ant(GameSystem system, String antType, int antId) {
		super(system, createName(antType, antId));

		this.antId = antId;
		this.alive = false;
	}

	public abstract Drawable getBound();

	public abstract ComponentAIBehaviorTree getBehaviorTree();
	
	public abstract int getMaxHealth();
	
	public abstract int getCurrentHealth();
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		
		if (getCurrentHealth() <= 0) {
			kill();
		}
	}

	public void onSpawn() {
		alive = true;
	}

	public void kill() {
		this.alive = false;
	}

	/**
	 * Called when the ant successfully reaches the anthill with sugar.
	 */
	public void anthillDespawn() {
		this.alive = false;
		
		// TODO Affect score, etc.

	}

	public boolean isAlive() {
		return this.alive;
	}

	public int getId() {
		return this.antId;
	}

	private static String createName(String antType, int antId) {
		return antType + antId;
	}

}

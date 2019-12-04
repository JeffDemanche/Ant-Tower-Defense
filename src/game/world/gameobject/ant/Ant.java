package game.world.gameobject.ant;

import engine.world.gameobject.ComponentAIBehaviorTree;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.system.SystemAnts;

public abstract class Ant extends GameObject {

	protected static final int ANT_DAMAGE_ANIMATION_TIMER = 200;
	
	private int antId;
	private boolean alive;

	private SystemAnts system;

	private int maxHealth;
	private int currentHealth;

	public Ant(SystemAnts system, String antType, int antId, int maxHealth) {
		super(system, createName(antType, antId));

		this.system = system;

		this.antId = antId;
		this.alive = false;

		this.currentHealth = maxHealth;
		this.maxHealth = maxHealth;
	}

	public abstract Drawable getBound();

	public abstract ComponentAIBehaviorTree getBehaviorTree();

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public int getCurrentHealth() {
		return this.currentHealth;
	}

	public abstract int getSugarCap();
	
	public void damage(int amount) {
		if (currentHealth - amount <= 0) {
			this.kill();
		} else {
			currentHealth -= amount;
		}
	}

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

		((ATDWorld) this.system.getWorld()).onAntReachedAnthill(this);
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

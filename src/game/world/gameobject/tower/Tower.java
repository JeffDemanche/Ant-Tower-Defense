package game.world.gameobject.tower;

import engine.world.GameSystem;
import engine.world.gameobject.GameObject;

public abstract class Tower extends GameObject {
	
	private boolean canAttack ;
	
	private long attackTimer = 0;
	
	private double attackTimerMilliSeconds;
	
	protected double attackTime;
	
	protected boolean enabled;
	
	
	
	public Tower(GameSystem system, String towerType, int towerId) {
		super(system, createName(towerType, towerId));
		canAttack = false;
	}
	
	public abstract int getCost();
	
	private static String createName(String towerType, int towerId) {
		return towerType + towerId;
	}
	
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		
		if(enabled)
		{
			if(!canAttack)
			{
				
				attackTimer+=nanosSincePreviousTick;
				
				attackTimerMilliSeconds = attackTimer/1000000;
				
				if(attackTimerMilliSeconds >= attackTime)
				{
					canAttack = true;
				}
			}
			else
			{
				shot();
				canAttack = false;
				attackTimer = 0;
				attackTimerMilliSeconds = 0;
			}
		}
		
		super.onTick(nanosSincePreviousTick);
	}

	protected abstract void shot(); 

	public boolean isCanAttack() {
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public long getAttackTimer() {
		return attackTimer;
	}

	public void setAttackTimer(long attackTimer) {
		this.attackTimer = attackTimer;
	}

	public double getAttackTimerMilliSeconds() {
		return attackTimerMilliSeconds;
	}

	public void setAttackTimerMilliSeconds(double attackTimerMilliSeconds) {
		this.attackTimerMilliSeconds = attackTimerMilliSeconds;
	}

	public double getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(double attackTime) {
		this.attackTime = attackTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

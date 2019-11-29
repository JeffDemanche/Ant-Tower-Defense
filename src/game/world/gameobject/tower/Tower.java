package game.world.gameobject.tower;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.GameObject;
import game.world.gameobject.tower.lineofsight.LineOfSight;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;
import javafx.scene.input.MouseEvent;

public abstract class Tower extends GameObject {
	
	private boolean canAttack ;
	
	private long attackTimer = 0;
	
	private double attackTimerMilliSeconds;
	
	protected double attackTime;
	
	protected boolean enabled;
	
	protected Vec2d direction;
	
	protected LineOfSight lineOfSight;
	
	protected ComponentCircle bound;
	protected ComponentRegisteredSprite sprite;
	
	protected HexCoordinates hex;
	
	public Tower(GameSystem system, String towerType, int towerId,HexCoordinates hexCoordinates,
			double range) {
		super(system, createName(towerType, towerId));
		

		this.hex = hexCoordinates;
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
	

		this.addComponent(bound);
		
		this.direction = new Vec2d(0,-1);
		canAttack = false;
		lineOfSight = new LineOfSight(system, "lineOfSight"+towerId, hex.toGameSpaceCentered(),
				this.direction,range);
		
		system.addGameObject(SystemTowers.TOWERS_Z+3, lineOfSight);
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
	
	@Override
	public void onMousePressed(MouseEvent e) 
	{
		System.out.println("CLICK on TOWER");
		super.onMousePressed(e);
	}

	@Override
	public void onMouseDragged(MouseEvent e) 
	{
		
	}

	@Override
	public void onMouseReleased(MouseEvent e) 
	{
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

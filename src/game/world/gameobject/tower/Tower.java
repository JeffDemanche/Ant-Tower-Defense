package game.world.gameobject.tower;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.gameobject.tower.lineofsight.LineOfSight;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;
import javafx.scene.input.MouseEvent;

public abstract class Tower extends GameObject {

	private boolean canAttack;

	private long attackTimer = 0;
	private double attackTimerMilliSeconds;
	protected double cooldownDurationMillis;

	protected boolean enabled;

	protected Vec2d direction;

	protected LineOfSight lineOfSight;

	protected ComponentCircle bound;
	protected ComponentRegisteredSprite sprite;

	protected HexCoordinates hex;

	private Vec2d mousePos;

	private boolean selected;

	private int fireCooldownTimer;

	private static double FortyFivedegreesToRadians = 45 * Math.PI / 180;
	
	protected double projectileSpeed;

	public Tower(GameSystem system, TowerInfo towerType, int towerId,
			HexCoordinates hexCoordinates, double range) {
		super(system, createName(towerType.name, towerId));

		this.hex = hexCoordinates;
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);

		this.addComponent(bound);

		this.direction = new Vec2d(0, -1);
		canAttack = false;
		lineOfSight = new LineOfSight(system, "lineOfSight" + towerId,
				hex.toGameSpaceCentered(), this.direction, range);

		// One minute in milliseconds divided by the rounds per minute.
		this.cooldownDurationMillis = (60.0 * 1000) / towerType.rateOfFire;
		
		system.addGameObject(SystemTowers.TOWERS_Z + 3, lineOfSight);
	}

	public abstract int getCost();

	private static String createName(String towerType, int towerId) {
		return towerType + towerId;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {

		if(((ATDWorld)getSystem().getWorld()).isWaveActive())
		{
			if (enabled) {
				if (!canAttack) {

					attackTimer += nanosSincePreviousTick;

					attackTimerMilliSeconds = attackTimer / 1000000;
					
					if (attackTimerMilliSeconds >= cooldownDurationMillis) {
						canAttack = true;
					}
				} else {
					shot();
					canAttack = false;
					attackTimer = 0;
					attackTimerMilliSeconds = 0;
				}
			}

		}
		
		super.onTick(nanosSincePreviousTick);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		Vec2d clickPos = new Vec2d(e.getX(), e.getY());
		if (getDrawable().insideBB(clickPos)) {
			// System.out.println("CLICK on TOWER");
			this.selected = true;
			this.mousePos = clickPos;
		}

		// super.onMousePressed(e);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		/*
		 * Vec2d currentMousePos = new Vec2d(e.getX(),e.getY());
		 * if(this.selected) { //Vec2d delta =
		 * currentMousePos.minus(this.mousePos);
		 * 
		 * this.mousePos = currentMousePos; }
		 */
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		if (this.selected) {
			// double rX = Math.cos(FortyFivedegreesToRadians)*direction.x -
			// Math.sin(FortyFivedegreesToRadians)*direction.y;
			// double rY = Math.sin(FortyFivedegreesToRadians)*direction.x +
			// Math.cos(FortyFivedegreesToRadians)*direction.y;

			this.direction = this.direction.rotate(FortyFivedegreesToRadians);
			lineOfSight.updateEndPoint(this.direction);
			this.selected = false;
		}
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
		return cooldownDurationMillis;
	}

	public void setAttackTime(double attackTime) {
		this.cooldownDurationMillis = attackTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public double getProjectileSpeed() {
		return projectileSpeed;
	}

	public void setProjectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}
}

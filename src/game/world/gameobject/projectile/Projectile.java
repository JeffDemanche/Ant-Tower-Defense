package game.world.gameobject.projectile;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.GameObject;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public abstract class Projectile extends GameObject
{
	
    protected ComponentCircle bound;
    protected ComponentRegisteredSprite sprite;
    protected Vec2d direction;
    protected double speed;
    protected Vec2d target;
    protected boolean hit ;
	
	public Projectile(SystemTowers towerSystem, HexCoordinates hex,
			ProjectileInfo projectileInfo) {
		
		super(towerSystem, createName(projectileInfo.parentTower,towerSystem.nextProjectileId()));
		
		this.direction = projectileInfo.direction;
		this.speed = projectileInfo.speed;
		
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY_PROJECTILE, bound);

		this.target = projectileInfo.target;
		this.hit = false;
		
		
		this.addComponent(bound);
		this.addComponent(sprite);
	}

	
	private static String createName(String towerType, int towerId) {
		return towerType + towerId;
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		if(bound.getPosition().minus(this.target).mag() < 0.001)
		{
			this.hit = true;
		}
			
		bound.adjustPosition(this.direction.smult(this.speed));
		super.onTick(nanosSincePreviousTick);
	}

	
}

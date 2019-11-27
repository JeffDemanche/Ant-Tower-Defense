package game.world.gameobject.projectile;

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
	
	public Projectile(SystemTowers towerSystem, HexCoordinates hex, ProjectileInfo projectileInfo) {
		
		super(towerSystem, createName(projectileInfo.parentTower,towerSystem.nextProjectileId()));
		
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY_PROJECTILE, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	
	private static String createName(String towerType, int towerId) {
		return towerType + towerId;
	}
	
}

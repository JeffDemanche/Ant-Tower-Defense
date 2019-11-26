package game.world.gameobject.projectile;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.GameObject;
import game.world.system.SystemTowers;

public abstract class Projectile extends GameObject
{
	
    private ComponentCircle bound;
	private ComponentRegisteredSprite sprite;
	
	public Projectile(SystemTowers towerSystem, String id) {
		
		super(towerSystem, name);
		
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

}

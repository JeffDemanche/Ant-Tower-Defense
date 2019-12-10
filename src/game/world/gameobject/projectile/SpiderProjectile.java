package game.world.gameobject.projectile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCollidable;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.ComponentCollidable.CollisionHandler;
import game.world.ATDWorld;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.WaterParticlesEmitter;
import game.world.gameobject.ant.Ant;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class SpiderProjectile extends Projectile{

	

	public SpiderProjectile(SystemTowers towerSystem, HexCoordinates hex, ProjectileInfo projectileInfo) {
		super(towerSystem, hex, projectileInfo);
		// TODO Auto-generated constructor stub
		
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SPIDER_PROJECTILE, bound);
		
		
		this.addComponent(sprite);
	
		
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		if(this.hit)
		{
		   this.remove();
		}
		super.onTick(nanosSincePreviousTick);
	}
}

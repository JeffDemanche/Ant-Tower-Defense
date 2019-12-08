package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCircle;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.projectile.HoneyProjectile;
import game.world.gameobject.projectile.Projectile;
import game.world.gameobject.projectile.ProjectileConstants;
import game.world.gameobject.projectile.ProjectileFactory;
import game.world.gameobject.projectile.ProjectileInfo;
import game.world.gameobject.tower.lineofsight.LineOfSightComponent;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class HoneyTower extends Tower {

		
	
	
	public HoneyTower(SystemTowers system, HexCoordinates hexCoordinates) {
		super(system, TowerInfo.HONEY, system.nextTowerId(), hexCoordinates,3);
		

        this.projectileSpeed = 0.05;
		
    	this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY, bound);
		
    	this.addComponent(sprite);
    	
		cooldownDurationMillis = 3000;
		enabled = true;
		
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return TowerInfo.HONEY.cost;
	}

	@Override
	protected void shot() {
		
		Vec2d target = lineOfSight.getEndPoint();
		// TODO Auto-generated method stub
		ProjectileInfo pjInfo =  new ProjectileInfo(getName(),ProjectileConstants.HONEY,
				this.direction,this.projectileSpeed,target);
		
		Projectile hProjectile = ProjectileFactory.getInstance().
				createProjectile((SystemTowers)getSystem(), hex, pjInfo);
		
		getSystem().addGameObject(SystemTowers.PROJECTILE_Z,hProjectile);
	}

	public double getProjectileSpeed() {
		return projectileSpeed;
	}

	public void setProjectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}

	
	
}

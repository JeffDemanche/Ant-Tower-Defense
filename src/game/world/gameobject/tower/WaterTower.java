package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.projectile.Projectile;
import game.world.gameobject.projectile.ProjectileConstants;
import game.world.gameobject.projectile.ProjectileFactory;
import game.world.gameobject.projectile.ProjectileInfo;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class WaterTower extends Tower {

	public WaterTower(SystemTowers system, HexCoordinates hexCoordinates) {
		super(system, TowerInfo.WATER, system.nextTowerId(), hexCoordinates);
		// TODO Auto-generated constructor stub

		this.projectileSpeed = 0.1;

		this.sprite = new ComponentRegisteredSprite(this, SpriteRegistry.WATER,
				bound);

		this.addComponent(sprite);

		cooldownDurationMillis = 6000;
		enabled = true;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void shot() {
		// TODO Auto-generated method stub
		Vec2d target = lineOfSight.getEndPoint();
		// TODO Auto-generated method stub
		ProjectileInfo pjInfo = new ProjectileInfo(getName(),
				ProjectileConstants.WATER, this.direction, this.projectileSpeed,
				target);

		Projectile wProjectile = ProjectileFactory.getInstance()
				.createProjectile((SystemTowers) getSystem(), hex, pjInfo);

		getSystem().addGameObject(SystemTowers.PROJECTILE_Z, wProjectile);
	}

	@Override
	public boolean traversable() {
		return false;
	}

}

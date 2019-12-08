package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;

public class WaterTower extends Tower {

	public WaterTower(GameSystem system, TowerInfo towerType, int towerId, HexCoordinates hexCoordinates,
			double range) {
		super(system, towerType, towerId, hexCoordinates, range);
		// TODO Auto-generated constructor stub
		
		this.projectileSpeed = 0.05;

		this.sprite = new ComponentRegisteredSprite(this, SpriteRegistry.WATER, bound);

		this.addComponent(sprite);

		cooldownDurationMillis = 3000;
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
		
	}
   
}

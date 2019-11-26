package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentCircle;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class HoneyTower extends Tower {

	private SystemTowers towers;
	private HexCoordinates hex;
     
	
	private ComponentCircle bound;
	private ComponentRegisteredSprite sprite;
	
	public HoneyTower(SystemTowers system, HexCoordinates hexCoordinates) {
		super(system, TowerInfo.HONEY.name, system.nextTowerId());
		// TODO Auto-generated constructor stub

		this.towers = system;
		this.hex = hexCoordinates;

		// this.bound = new ComponentAABB(this, hex.toGameSpace(), new
		// Vec2d(1));
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
		
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return TowerInfo.HONEY.cost;
	}

	
	
}

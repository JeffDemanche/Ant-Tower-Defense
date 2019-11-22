package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentAABB;
import engine.world.gameobject.ComponentCircle;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class TowerCinnamon extends Tower {

	private SystemTowers towers;
	private HexCoordinates hex;

	private ComponentCircle bound;
	private ComponentRegisteredSprite sprite;

	public TowerCinnamon(SystemTowers system, HexCoordinates hex) {
		super(system, TowerInfo.CINNAMON.name, system.nextTowerId());

		this.towers = system;
		this.hex = hex;

		// this.bound = new ComponentAABB(this, hex.toGameSpace(), new
		// Vec2d(1));
		this.bound = new ComponentCircle(this, hex.toGameSpaceCentered(),
				HexCoordinates.HEX_WIDTH / 2);
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.CINNAMON, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public int getCost() {
		return TowerInfo.CINNAMON.cost;
	}

}

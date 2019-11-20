package game.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.ComponentDynamicSprite;
import engine.world.gameobject.GameObject;
import game.world.system.HexCoordinates;

public class SugarPile extends GameObject {
	
	private final double SPRITE_RADIUS = (Math.sqrt(3) / 2) * 1.5;

	public SugarPile(GameSystem system, HexCoordinates centerHex) {
		super(system, "Sugar Pile");
		
		Vec2d gamePos = centerHex.toGameSpace();
		
		ComponentCircle bound = new ComponentCircle(this, gamePos, SPRITE_RADIUS);
		ComponentDynamicSprite sprite = new ComponentDynamicSprite(this, "file:src/img/sugar/sugar_1.png", bound, 8, 0);
		sprite.addPhaseToAnimation("Null", 0, 0, 64, 64);
		sprite.setAnimation("Null");
		
		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

}

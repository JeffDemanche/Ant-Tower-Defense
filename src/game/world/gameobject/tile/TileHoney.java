package game.world.gameobject.tile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentPolygon;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import game.world.system.SystemLevel;
import javafx.scene.canvas.GraphicsContext;

public class TileHoney extends Tile{

	private ComponentPolygon bound;
	
	public TileHoney(SystemLevel system, HexCoordinates offsetCoordinates) {
		super(system, createName("Honey", offsetCoordinates),
				offsetCoordinates, Type.Honey);

		bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public boolean traversableByDefault() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean suitableForTower() {
		// TODO Auto-generated method stub
		return false;
	}

}

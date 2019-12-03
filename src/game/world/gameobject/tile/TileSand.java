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

public class TileSand extends Tile {

	private ComponentPolygon bound;
	
	public TileSand(SystemLevel system, HexCoordinates offsetCoordinates) {
		super(system, createName("Sand", offsetCoordinates), offsetCoordinates, Type.Sand);

		bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SAND_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	public TileSand(Element element, SystemLevel system) {
		this(system, new HexCoordinates((Element) element
				.getElementsByTagName("HexCoordinates").item(0)));
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element TileSand = doc.createElement("TileSand");
		TileSand.setAttribute("name", this.getName());
		TileSand.appendChild(bound.writeXML(doc));
		TileSand.appendChild(this.getCoordinates().writeXML(doc));
		return TileSand;
	}

	@Override
	public boolean traversableByDefault() {
		return true;
	}

	@Override
	public boolean suitableForTower() {
		return true;
	}

}

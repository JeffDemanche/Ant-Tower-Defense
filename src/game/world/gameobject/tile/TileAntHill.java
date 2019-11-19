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

public class TileAntHill extends Tile {

	private ComponentPolygon bound;

	public TileAntHill(SystemLevel system, HexCoordinates offsetCoordinates) {
		super(system, createName("AntHill", offsetCoordinates),
				offsetCoordinates);

		bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.ANTHILL_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	public TileAntHill(Element element, SystemLevel system) {
		this(system, new HexCoordinates((Element) element
				.getElementsByTagName("HexCoordinates").item(0)));
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element TileAntHill = doc.createElement("TileAntHill");
		TileAntHill.setAttribute("name", this.getName());
		TileAntHill.appendChild(bound.writeXML(doc));
		TileAntHill.appendChild(this.getCoordinates().writeXML(doc));
		return TileAntHill;
	}

	@Override
	public boolean traversableByDefault() {
		return true;
	}

}

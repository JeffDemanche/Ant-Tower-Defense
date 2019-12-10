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

public class TileWater extends Tile {

	private ComponentPolygon bound;

	public TileWater(SystemLevel system, HexCoordinates offsetCoordinates) {
		super(system, createName("Water", offsetCoordinates),
				offsetCoordinates, Type.Water);

		bound = offsetCoordinates.createPolygon(this);
		this.setSprite(new ComponentRegisteredSprite(this,
				SpriteRegistry.WATER_MASKED, bound, 200, 3));

		this.addComponent(bound);
		this.addComponent(this.getSprite());
	}

	public TileWater(Element element, SystemLevel system) {
		this(system, new HexCoordinates((Element) element
				.getElementsByTagName("HexCoordinates").item(0)));
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element TileWater = doc.createElement("TileWater");
		TileWater.setAttribute("name", this.getName());
		TileWater.appendChild(bound.writeXML(doc));
		TileWater.appendChild(this.getCoordinates().writeXML(doc));
		return TileWater;
	}

	@Override
	public boolean traversableByDefault() {
		return false;
	}

	@Override
	public boolean suitableForTower() {
		return false;
	}

}

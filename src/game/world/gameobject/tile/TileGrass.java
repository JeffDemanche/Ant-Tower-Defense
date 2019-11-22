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

public class TileGrass extends Tile {

	private ComponentPolygon bound;

	public TileGrass(SystemLevel system, HexCoordinates offsetCoordinates) {
		super(system, createName("Grass", offsetCoordinates),
				offsetCoordinates);

		bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.GRASS_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	public TileGrass(Element element, SystemLevel system) {
		this(system, new HexCoordinates((Element) element
				.getElementsByTagName("HexCoordinates").item(0)));
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element TileGrass = doc.createElement("TileGrass");
		TileGrass.setAttribute("name", this.getName());
		TileGrass.appendChild(bound.writeXML(doc));
		TileGrass.appendChild(this.getCoordinates().writeXML(doc));
		return TileGrass;
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

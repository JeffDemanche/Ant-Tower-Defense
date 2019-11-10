package game.world.gameobject.tile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.GameSystem;
import engine.world.gameobject.GameObject;
import game.world.system.HexCoordinates;

/**
 * Tiles are elements of the terrain (i.e. sand/grass) that are rendered below
 * characters, towers, etc. They're part of the SystemLevel
 */
public abstract class Tile extends GameObject {

	private HexCoordinates offsetCoordinates;

	public Tile(GameSystem system, String name,
			HexCoordinates offsetCoordinates) {
		super(system, name);

		this.offsetCoordinates = offsetCoordinates;
	}

	/**
	 * Creates a unique tile name based on coordinates.
	 */
	public static String createName(String tileName,
			HexCoordinates coordinates) {
		return tileName + coordinates.getOffsetCoordinates().x + ","
				+ coordinates.getOffsetCoordinates().y;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

}

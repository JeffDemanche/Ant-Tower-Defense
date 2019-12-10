package game.world.gameobject.ant;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.serialization.XMLSerializable;
import game.world.system.HexCoordinates;
import game.world.system.SystemLevel;

/**
 * Used to inform squad dynamics. Ants will tend to avoid tiles where ants have
 * died in the past, probabilistically. A single object of this should be
 * initialized in SystemLevel.
 * 
 * @author jdemanch
 */
public class TileMemory implements XMLSerializable {

	/**
	 * All tiles in the level have an avoidance value. If it's not explicitly
	 * defined, assume it's zero.
	 */
	private HashMap<HexCoordinates, Double> avoidance;
	
	private SystemLevel level;

	public TileMemory(SystemLevel level) {
		this.level = level;
		this.avoidance = new HashMap<>();
	}

	public double getAvoidance(HexCoordinates coord) {
		return this.avoidance.getOrDefault(coord, 0.0);
	}

	public void setAvoidance(HexCoordinates coord, double value) {
		this.avoidance.put(coord, value);
		level.onTileMemoryUpdated(coord);
	}

	/**
	 * Called when an ant is killed. Calculations about avoidance value should
	 * happen here.
	 */
	public void onAntDeath(HexCoordinates location) {
		double val = getAvoidance(location);
		// TODO
		val += (1.0 - val) * 0.125;
		setAvoidance(location, val);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}

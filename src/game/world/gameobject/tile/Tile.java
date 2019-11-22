package game.world.gameobject.tile;

import java.util.HashSet;
import java.util.Set;

import engine.world.gameobject.GameObject;
import game.world.system.HexCoordinates;
import game.world.system.SystemLevel;

/**
 * Tiles are elements of the terrain (i.e. sand/grass) that are rendered below
 * characters, towers, etc. They're part of the SystemLevel
 */
public abstract class Tile extends GameObject {

	private HexCoordinates offsetCoordinates;

	private SystemLevel level;

	public Tile(SystemLevel system, String name,
			HexCoordinates offsetCoordinates) {
		super(system, name);
		this.level = system;
		this.offsetCoordinates = offsetCoordinates;
	}

	public HexCoordinates getCoordinates() {
		return this.offsetCoordinates;
	}

	public Set<HexCoordinates> getTraversableNeighborCoords() {
		HashSet<HexCoordinates> traversableNeighbors = new HashSet<>();
		for (HexCoordinates h : offsetCoordinates.getNeighbors()) {
			if (level.tileAt(h) != null
					&& level.tileAt(h).traversableByDefault()) {
				traversableNeighbors.add(h);
			}
		}
		return traversableNeighbors;
	}

	/**
	 * Creates a unique tile name based on coordinates.
	 */
	public static String createName(String tileName,
			HexCoordinates coordinates) {
		return tileName + coordinates.getOffsetCoordinates().x + ","
				+ coordinates.getOffsetCoordinates().y;
	}

	public abstract boolean traversableByDefault();
	
	public abstract boolean suitableForTower();

}

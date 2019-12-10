package game.world.gameobject.tile;

import java.util.HashSet;
import java.util.Set;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.GameObject;
import game.world.system.HexCoordinates;
import game.world.system.SystemLevel;

/**
 * Tiles are elements of the terrain (i.e. sand/grass) that are rendered below
 * characters, towers, etc. They're part of the SystemLevel
 */
public abstract class Tile extends GameObject {

	public enum Type {
		AntHill, Grass, Honey, Sand, Water
	}

	private HexCoordinates offsetCoordinates;
	private SystemLevel level;
	private ComponentRegisteredSprite sprite;
	private Type type;

	public Tile(SystemLevel system, String name,
			HexCoordinates offsetCoordinates, Type type) {
		super(system, name);
		this.level = system;
		this.offsetCoordinates = offsetCoordinates;
		this.type = type;
	}

	public HexCoordinates getCoordinates() {
		return this.offsetCoordinates;
	}

	public void setSprite(ComponentRegisteredSprite sprite) {
		this.sprite = sprite;
	}

	public ComponentRegisteredSprite getSprite() {
		return this.sprite;
	}
	
	public void setSpriteOpacity(double opacity) {
		this.sprite.setOpacity(opacity);
	}

	/**
	 * @return The avoidance value for this tile, which is (sort of) how likely
	 *         an ant is to not pathfind over it.
	 */
	public double getAvoidance() {
		return this.level.getTileMemory().getAvoidance(this.offsetCoordinates);
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}

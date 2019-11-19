package game.world.system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2i;
import engine.world.GameSystem;
import engine.world.TileCoordinates;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.gameobject.SugarPile;
import game.world.gameobject.tile.Tile;
import game.world.gameobject.tile.TileAntHill;
import javafx.scene.canvas.GraphicsContext;

public class SystemLevel extends GameSystem
		implements TileCoordinates<HexCoordinates> {

	private static final int TILES_Z = 1;

	private ATDWorld atdWorld;
	private LevelGenerator levelGenerator;

	private Vec2i size;

	private HexCoordinates antHill;
	private HexCoordinates sugarPile;

	private SugarPile sugarPileGameObject;

	/**
	 * Note that tiles are stored in this hash map as well as in the system game
	 * object list.
	 */
	private HashMap<HexCoordinates, Tile> tiles;

	public SystemLevel(ATDWorld world) {
		super(world);
		this.atdWorld = world;
		this.tiles = new HashMap<>();
		this.size = new Vec2i(64, 64);
		this.levelGenerator = new LevelGenerator(this, size, 20);
	}

	/**
	 * Returns the tile at a given hex coordinate.
	 * 
	 * @param coordinate
	 * @return The tile object in the world.
	 */
	public Tile tileAt(HexCoordinates coordinate) {
		return tiles.get(coordinate);
	}

	public HashSet<Tile> getNeighbors(HexCoordinates coordinate) {
		HashSet<Tile> neighbs = new HashSet<>();
		HashSet<HexCoordinates> tileNeighbors = coordinate.getNeighbors();
		for (HexCoordinates n : tileNeighbors) {
			if (tileAt(n) != null) {
				neighbs.add(tileAt(n));
			}
		}
		return neighbs;
	}

	/**
	 * Sets a coordinate in world space to be a given tile. If there's already a
	 * tile there, it replaces the game object belonging to this system.
	 * 
	 * @param coordinate
	 * @param tile
	 *            The new tile.
	 */
	public void setTile(HexCoordinates coordinate, Tile tile) {
		Tile existingTile = tileAt(coordinate);
		if (existingTile != null) {
			this.removeGameObject(existingTile);
		}
		tiles.put(coordinate, tile);
		this.addGameObject(TILES_Z, tile);
	}

	public void setAntHill(HexCoordinates coords) {
		this.antHill = coords;
	}

	public HexCoordinates getAntHill() {
		return this.antHill;
	}

	public void setSugarPile(HexCoordinates coords) {
		this.sugarPile = coords;
	}

	public HexCoordinates getSugarPile() {
		return this.sugarPile;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element SystemLevel = doc.createElement("SystemLevel");
		// Writes all system gameobjects.
		this.writeGameObjectsXML(doc);
		return SystemLevel;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		this.drawGameObjects(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		this.tickGameObjects(nanosSincePreviousTick);
	}

	@Override
	public void onStartup() {
		levelGenerator.generateHeightIsland(atdWorld.getRandom());

		this.sugarPileGameObject = new SugarPile(this, sugarPile);
		this.addGameObject(2, sugarPileGameObject);
		this.setTile(antHill, new TileAntHill(this, antHill));
	}

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWorldLoaded() {
	}

	@Override
	public GameObject getTileAt(int x, int y) {
		return tileAt(new HexCoordinates(new Vec2i(x, y)));
	}

	@Override
	public Set<HexCoordinates> getAllTiles() {
		return null;
	}

	@Override
	public Set<HexCoordinates> getTraversableNeighbors(int x, int y) {
		Set<HexCoordinates> hexNeighbors = tileAt(
				new HexCoordinates(new Vec2i(x, y)))
						.getTraversableNeighborCoords();

		HashSet<HexCoordinates> neighborsGameSpace = new HashSet<>();
		for (HexCoordinates neighbor : hexNeighbors) {
			neighborsGameSpace.add(neighbor);
		}
		return neighborsGameSpace;
	}

}

package game.world.system;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2i;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.gameobject.EmitterGameObject;
import game.world.gameobject.tile.Tile;
import javafx.scene.canvas.GraphicsContext;

public class SystemLevel extends GameSystem {

	private static final int TILES_Z = 1;

	private ATDWorld atdWorld;
	private LevelGenerator levelGenerator;
	private GameObject emptyGameObject;
	private Vec2i size;

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
		this.levelGenerator = new LevelGenerator(this, size);
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
		emptyGameObject = new EmitterGameObject(this, "emittergo");
		this.addGameObject(1, emptyGameObject);
		levelGenerator.generateHeightIsland(atdWorld.getRandom());
	}

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWorldLoaded() {
	}

}

package game.world.system;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import game.world.ATDWorld;
import game.world.gameobject.CostBillboard;
import game.world.gameobject.tile.Tile;
import game.world.gameobject.tower.Tower;
import game.world.gameobject.tower.TowerInfo;
import javafx.scene.canvas.GraphicsContext;

public class SystemTowers extends GameSystem {

	public static final int TOWERS_Z = 4;
	public static final int PROJECTILE_Z = 5;

	private ATDWorld atdWorld;
	private SystemLevel level;

	private int towerCounter;
	private int projectileCounter;

	private HashMap<HexCoordinates, Tower> towers;

	public SystemTowers(ATDWorld world, SystemLevel level) {
		super(world);
		this.atdWorld = world;
		this.level = level;
		this.towerCounter = 0;
		this.towers = new HashMap<>();
	}

	public SystemLevel getLevel() {
		return this.level;
	}

	public int nextTowerId() {
		int id = towerCounter;
		towerCounter++;
		return id;
	}

	public int nextProjectileId() {
		int id = projectileCounter;
		projectileCounter++;
		return id;
	}

	/**
	 * This is called before any checks or updates to world state, so all that
	 * should be handled within it.
	 * 
	 * @param gameCoords
	 *            Raw game space coords of drop location.
	 * @param tower
	 *            The tower info.
	 */
	public void addTowerToWorld(Vec2d gameCoords, TowerInfo tower) {
		HexCoordinates hex = HexCoordinates.fromGameSpace(gameCoords);

		if (canPlaceTower(hex, tower)) {
			Tower t = TowerInfo.createTower(this, hex, tower);
			CostBillboard costbillboard = new CostBillboard(this, "costbillboard", tower.cost, gameCoords);
			this.addGameObject(TOWERS_Z, t);
			this.addGameObject(TOWERS_Z+1, costbillboard);
			towers.put(hex, t);
			atdWorld.onTowerPlaced(tower);
		} else {
			// TODO
			System.out.println("Can't place tower.");
		}
	}

	/**
	 * Validation of whether a new tower can be placed at a given world
	 * location.
	 */
	public boolean canPlaceTower(HexCoordinates hex, TowerInfo tower) {
		Tile tile = level.tileAt(hex);

		boolean validTileType = tile.suitableForTower();
		boolean enoughCash = atdWorld.getCash() >= tower.cost;
		boolean noCurrentWave = !atdWorld.isWaveActive();

		return validTileType && enoughCash && noCurrentWave;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
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
	}

	@Override
	public void onShutdown() {
	}

	@Override
	public void onWorldLoaded() {
	}

}

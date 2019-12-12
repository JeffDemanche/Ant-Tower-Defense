package game.world;

import java.util.Random;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.World;
import game.viewport.ATDViewport;
import game.world.gameobject.ant.Ant;
import game.world.gameobject.tower.TowerInfo;
import game.world.system.HexCoordinates;
import game.world.system.SystemAnts;
import game.world.system.SystemGUI;
import game.world.system.SystemLevel;
import game.world.system.SystemProjectiles;
import game.world.system.SystemTowers;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ATDWorld extends World {

	private static final int STARTING_SUGAR = 30;
	private static final int STARTING_CASH = 50;

	private ATDViewport viewport;
	private String name;

	private SystemLevel level;
	private SystemAnts ants;
	private SystemGUI gui;
	private SystemTowers towers;
	private SystemProjectiles projectiles;

	private long worldSeed;
	private Random worldRandom;

	private int remainingSugar;
	private int cash;

	public ATDWorld(ATDViewport viewport, String name) {
		super(viewport);
		this.viewport = viewport;
		this.name = name;

		this.level = new SystemLevel(this);
		this.ants = new SystemAnts(this, level);
		this.towers = new SystemTowers(this, level);
		this.gui = new SystemGUI(this, towers);
		this.projectiles = new SystemProjectiles(this, level);

		this.worldSeed = System.currentTimeMillis();
		this.worldRandom = new Random(this.worldSeed);

		this.remainingSugar = STARTING_SUGAR;
		this.cash = STARTING_CASH;

		this.addSystem(level);
		this.addSystem(towers);
		this.addSystem(ants);
		this.addSystem(gui);
	}

	/**
	 * Construct from XML element.
	 */
	public ATDWorld(Element element, Vec2d size) {
		super(new ATDViewport(
				(Element) element.getElementsByTagName("ATDViewport").item(0),
				size));
		this.name = element.getAttribute("name");
		this.worldSeed = Long.parseLong(element.getAttribute("seed"));
		this.worldRandom = new Random(this.worldSeed);
		this.remainingSugar = Integer
				.parseInt(element.getAttribute("remainingSugar"));
		this.cash = Integer.parseInt(element.getAttribute("cash"));
	}

	public Random getRandom() {
		return this.worldRandom;
	}

	public int getRemainingSugar() {
		return this.remainingSugar;
	}

	public int getCash() {
		return this.cash;
	}

	public void addCash(int amount) {
		this.cash += amount;
	}
	
	public SystemAnts getAntsSystem() {
		return this.ants;
	}

	public boolean isWaveActive() {
		return this.ants.isWaveActive();
	}

	public void onAntReachedAnthill(Ant a) {
		this.remainingSugar -= a.getSugarCap();

		if (this.remainingSugar <= 0) {
			// TODO
			System.out.println("GAME OVER");
		}
	}

	public void onTowerPlaced(TowerInfo tower) {
		this.cash -= tower.cost;
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);

		HexCoordinates click = (HexCoordinates.fromGameSpace(viewport
				.toGameSpace(new Vec2d(e.getSceneX(), e.getSceneY()), false)));
		Set<HexCoordinates> neighbs = level.getTraversableNeighbors(
				click.getOffsetCoordinates().x, click.getOffsetCoordinates().y);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		super.onKeyPressed(e);

		if (e.getCode() == KeyCode.SPACE) {
			ants.startWave();
		}
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element ATDWorld = doc.createElement("ATDWorld");
		ATDWorld.setAttribute("name", this.name);
		ATDWorld.setAttribute("seed", new Long(worldSeed).toString());
		ATDWorld.setAttribute("remainingSugar",
				new Integer(remainingSugar).toString());
		ATDWorld.setAttribute("cash", new Integer(remainingSugar).toString());
		ATDWorld.appendChild(viewport.writeXML(doc));
		ATDWorld.appendChild(level.writeXML(doc));
		ATDWorld.appendChild(ants.writeXML(doc));
		return ATDWorld;
	}

}

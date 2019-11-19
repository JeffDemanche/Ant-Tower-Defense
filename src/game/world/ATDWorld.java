package game.world;

import java.util.Random;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.World;
import game.viewport.ATDViewport;
import game.world.system.HexCoordinates;
import game.world.system.SystemAnts;
import game.world.system.SystemGUI;
import game.world.system.SystemLevel;
import game.world.system.SystemTowers;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ATDWorld extends World {

	private ATDViewport viewport;
	private String name;

	private SystemLevel level;
	private SystemAnts ants;
	private SystemGUI gui;
	private SystemTowers towers;

	private long worldSeed;
	private Random worldRandom;

	public ATDWorld(ATDViewport viewport, String name) {
		super(viewport);
		this.viewport = viewport;
		this.name = name;

		this.level = new SystemLevel(this);
		this.ants = new SystemAnts(this, level);
		this.gui = new SystemGUI(this);
		this.towers = new SystemTowers(this, level);

		this.worldSeed = System.currentTimeMillis();
		this.worldRandom = new Random(this.worldSeed);

		this.addSystem(level);
		this.addSystem(ants);
		this.addSystem(gui);
		this.addSystem(towers);
	}

	public Random getRandom() {
		return this.worldRandom;
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
			// TODO temporary until button for next wave.
			ants.startWave();
		}
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
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element ATDWorld = doc.createElement("ATDWorld");
		ATDWorld.setAttribute("name", this.name);
		ATDWorld.setAttribute("seed", new Long(worldSeed).toString());
		ATDWorld.appendChild(viewport.writeXML(doc));
		ATDWorld.appendChild(level.writeXML(doc));
		ATDWorld.appendChild(ants.writeXML(doc));
		return ATDWorld;
	}

}

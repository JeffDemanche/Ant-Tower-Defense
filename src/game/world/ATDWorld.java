package game.world;

import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.World;
import game.viewport.ATDViewport;
import game.world.system.SystemLevel;

public class ATDWorld extends World {

	private ATDViewport viewport;
	private String name;

	private SystemLevel level;

	private long worldSeed;
	private Random worldRandom;

	public ATDWorld(ATDViewport viewport, String name) {
		super(viewport);
		this.viewport = viewport;
		this.name = name;

		this.level = new SystemLevel(this);

		this.worldSeed = System.currentTimeMillis();
		this.worldRandom = new Random(this.worldSeed);

		this.addSystem(level);
	}

	public Random getRandom() {
		return this.worldRandom;
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
		return ATDWorld;
	}

}

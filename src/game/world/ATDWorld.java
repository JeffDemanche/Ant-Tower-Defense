package game.world;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.Viewport;
import engine.world.World;
import game.viewport.ATDViewport;

public class ATDWorld extends World {

	public ATDWorld(Viewport viewport) {
		super(viewport);
	}

	/**
	 * Construct from XML element.
	 */
	public ATDWorld(Element element, Vec2d size) {
		super(new ATDViewport(
				(Element) element.getElementsByTagName("ATDViewport").item(0),
				size));
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element ATDWorld = doc.createElement("ATDWorld");
		return ATDWorld;
	}

}

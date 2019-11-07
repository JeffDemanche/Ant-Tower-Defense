package game.viewport;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.Viewport;
import engine.world.serialization.XMLEngine;

public class ATDViewport extends Viewport {

	public ATDViewport(Vec2d initialScreenSize) {
		super(initialScreenSize);
	}

	/**
	 * Create ATDViewport from XML element.
	 */
	public ATDViewport(Element element, Vec2d initialScreenSize) {
		super(initialScreenSize);
		this.setViewportCenter(XMLEngine.readVec2d(element.getAttribute("center")));
		this.setScale(Double.parseDouble(element.getAttribute("scale")));
	}
	
	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element ATDViewport = doc.createElement("ATDViewport");
		ATDViewport.setAttribute("center", XMLEngine.writeVec2d(getViewportCenter()));
		ATDViewport.setAttribute("scale", new Double(getScale()).toString());
		return ATDViewport;
	}

}

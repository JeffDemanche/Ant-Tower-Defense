package engine.world.serialization;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLSerializable {

	/**
	 * Outputs an object implementing this interface to XML as a string.
	 * 
	 * @return The string XML.
	 */
	public Element writeXML(Document doc) throws ParserConfigurationException;

}

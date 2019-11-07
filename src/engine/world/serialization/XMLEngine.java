package engine.world.serialization;

import java.util.ArrayList;

import org.w3c.dom.Element;

import application.Vec2d;

public final class XMLEngine {

	public static String writeVec2d(Vec2d v) {
		if (v == null) {
			return null;
		}
		return v.x + ", " + v.y;
	}

	public static Vec2d readVec2d(String val) {
		if (val.equals("null")) {
			return null;
		}
		String[] strVals = val.split(", ");
		return new Vec2d(Double.parseDouble(strVals[0]),
				Double.parseDouble(strVals[1]));
	}

	public static String writeIterableAsAttribute(Iterable<String> i) {
		String attrVal = "";
		for (Object o : i) {
			attrVal += o.toString().replace(" ", "%20");
			attrVal += " ";
		}
		attrVal.substring(0, attrVal.length() - 1);
		return attrVal;
	}

	public static ArrayList<String> readIterableElementAsList(String attr) {
		ArrayList<String> newList = new ArrayList<>();

		for (String val : attr.split(" ")) {
			newList.add(val.replace("%20", " "));
		}

		return newList;
	}

	public static Element loadFirstChildAsElement(String childTag,
			Element parent) {
		return (Element) parent.getElementsByTagName(childTag).item(0);
	}

}

package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.Viewport;
import engine.world.serialization.XMLEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ComponentLine extends Component {

	private Vec2d start;
	private Vec2d end;

	private Color color;
	private double strokeWidth;

	public ComponentLine(GameObject object, Vec2d start, Vec2d end, Color color,
			double strokeWidth) {
		super("Line", object);
		this.start = start;
		this.end = end;
		this.color = color;
		this.strokeWidth = strokeWidth;
	}

	public ComponentLine(GameObject object, Element element) {
		super("Line", object);
		this.start = XMLEngine.readVec2d(element.getAttribute("start"));
		this.end = XMLEngine.readVec2d(element.getAttribute("end"));
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentLine = doc.createElement("ComponentLine");
		componentLine.setAttribute("start", XMLEngine.writeVec2d(start));
		componentLine.setAttribute("end", XMLEngine.writeVec2d(end));
		return componentLine;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Viewport viewport = this.getObject().getSystem().getWorld()
				.getViewport();
		Vec2d screenStart = viewport.toScreenSpace(start, false);
		Vec2d screenEnd = viewport.toScreenSpace(end, false);

		g.setLineWidth(strokeWidth);
		g.setStroke(color);
		g.strokeLine(screenStart.x, screenStart.y, screenEnd.x, screenEnd.y);
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameObjectRemoved() {
	}

}

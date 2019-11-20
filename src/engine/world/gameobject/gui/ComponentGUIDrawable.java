package engine.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class ComponentGUIDrawable extends Component implements Drawable {

	private Vec2d screenPos;
	private Vec2d screenSize;

	public ComponentGUIDrawable(GameObject object, Vec2d screenPos,
			Vec2d screenSize) {
		super("GUI Drawable", object);
		this.screenPos = screenPos;
		this.screenSize = screenSize;
	}

	@Override
	public Vec2d getScreenPosition() {
		return this.screenPos;
	}

	@Override
	public Vec2d getScreenSize() {
		return this.screenSize;
	}

	@Override
	public Vec2d getPosition() {
		// Has no game-space position
		return null;
	}

	@Override
	public void adjustPosition(Vec2d adjustment) {
		screenPos = screenPos.plus(adjustment);
	}

	@Override
	public boolean insideBB(Vec2d point) {
		return screenPos.x <= point.x && point.x <= screenPos.x + screenSize.x
				&& screenPos.y <= point.y
				&& point.y <= screenPos.y + screenSize.y;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Vec2d getSize() {
		return this.getObject().getSystem().getWorld().getViewport()
				.toGameSpace(screenSize, true);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {

		Element componentGUIDrawable = doc.createElement("ComponentGUIDrawable");
		return componentGUIDrawable;
	}

	@Override
	public void setPosition(Vec2d position) {
		this.screenPos = position;
	}
	
	public void setSize(Vec2d size) {
		this.screenSize = size;
	}

}

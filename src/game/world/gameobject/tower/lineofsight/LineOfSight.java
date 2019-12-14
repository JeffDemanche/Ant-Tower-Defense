package game.world.gameobject.tower.lineofsight;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;

public class LineOfSight extends GameObject {

	private boolean draw;
	
	public LineOfSight(GameSystem system, String name, Vec2d initPosition,
			Vec2d direction, double range) {
		super(system, name);
		// TODO Auto-generated constructor stub

		LineOfSightComponent lineOfSightComponent = new LineOfSightComponent(
				"LineOfSight", this, initPosition, direction, range);

		this.addComponent(lineOfSightComponent);
	}
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	} 
	
	public boolean draw() {
		return this.draw;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public Vec2d getDirection() {
		return ((LineOfSightComponent) this.getComponent("LineOfSight"))
				.getDirection();
	}

	public Vec2d getEndPoint() {
		return ((LineOfSightComponent) this.getComponent("LineOfSight"))
				.getEndPoint();
	}

	public void updateEndPoint(Vec2d direction) {
		((LineOfSightComponent) this.getComponent("LineOfSight"))
				.updateEndPoint(direction);
	}

}

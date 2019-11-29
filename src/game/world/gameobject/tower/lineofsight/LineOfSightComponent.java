package game.world.gameobject.tower.lineofsight;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineOfSightComponent extends Component implements Drawable{

	private Vec2d position;
	private Vec2d endPosition;
	private double range;
	
	
	public LineOfSightComponent(String tag, GameObject object, Vec2d initPosition,Vec2d direction, double range) {
		super(tag, object);
		
		// TODO Auto-generated constructor stub
		
		this.position = initPosition;
		this.range = range;
		
		updateEndPoint(direction);
		
		
	}

	public void updateEndPoint(Vec2d direction) {
		// TODO Auto-generated method stub
		this.endPosition = this.position.plus(direction.smult(this.range));
		
	}

	@Override
	public Vec2d getScreenPosition() {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Vec2d getScreenSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec2d getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}

	@Override
	public Vec2d getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vec2d position) {
		// TODO Auto-generated method stub
		this.position = position;
	}

	@Override
	public void adjustPosition(Vec2d adjustment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean insideBB(Vec2d screenPos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDraw(GraphicsContext graphicsCx) {
		// TODO Auto-generated method stub
		 
		Vec2d startPosScreenCords =  this.getObject().gameToScreen(this.position, false);
		Vec2d endPosScreenCords =  this.getObject().gameToScreen(this.endPosition, false);
		 graphicsCx.setStroke(Color.RED);
		 
		 graphicsCx.strokeLine(startPosScreenCords.x,startPosScreenCords.y,
				 endPosScreenCords.x,endPosScreenCords.y);	  
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}

	public Vec2d getEndPoint() {
		return endPosition;
	}

	public void setEndPosition(Vec2d endPosition) {
		this.endPosition = endPosition;
	}
	
	public Vec2d getDirection()
	{
		return endPosition.minus(position).normalize();
	}

}

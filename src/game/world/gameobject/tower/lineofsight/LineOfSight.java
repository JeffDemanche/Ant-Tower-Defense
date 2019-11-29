package game.world.gameobject.tower.lineofsight;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class LineOfSight extends Component implements Drawable{

	public LineOfSight(String tag, GameObject object) {
		super(tag, object);
		// TODO Auto-generated constructor stub
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
		return null;
	}

	@Override
	public Vec2d getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vec2d position) {
		// TODO Auto-generated method stub
		
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
	public void onDraw(GraphicsContext g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}

}

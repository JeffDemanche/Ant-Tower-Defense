package game.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CostBillboardComponent extends Component implements Drawable{

	private String cost;
	private Vec2d position;
	
	private long timer;
	private int visibleTime = 2;
	
	public CostBillboardComponent(String tag, GameObject object, String cost,Vec2d position) {
		super(tag, object);
		// TODO Auto-generated constructor stub
		this.cost = cost;
		this.position = position;
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
		//g.setFont(this.font);
		Vec2d startPosScreenCords =  this.getObject().gameToScreen(position, false);
		//text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
		g.setFill(Color.BLACK);
		g.strokeText(cost, startPosScreenCords.x, startPosScreenCords.y);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		timer+=nanosSincePreviousTick;
		if(timer > 1000000000)
		{
			visibleTime--;
			if(visibleTime < 0)
			{
				getObject().remove();
			}
		}
	}

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}

}

package game.world.gameobject.ant;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;
import game.world.system.SystemAnts;

public class AntPathSpline extends GameObject{

	
	public AntPathSpline(GameSystem system, String name,
			List<Vec2d> controlPointsTo,List<Vec2d> controlPointsFrom) 
	{
		
		super(system, name);
		// TODO Auto-generated constructor stub
		
		AntPahSplineComponent splineComponentTo =
				new AntPahSplineComponent("pathSplineTo", this,controlPointsTo);
		
		AntPahSplineComponent splineComponentFrom =
				new AntPahSplineComponent("pathSplineFrom", this,controlPointsFrom);
		
		this.addComponent(splineComponentTo);
		this.addComponent(splineComponentFrom);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void enableShowPathTo(boolean enabled)
	{
		((AntPahSplineComponent)this.getComponent("pathSplineTo")).setEnabled(enabled);
	}
	
	public void enableShowPathFrom(boolean enabled)
	{
		((AntPahSplineComponent)this.getComponent("pathSplineFrom")).setEnabled(enabled);
	}
	
	
	
}

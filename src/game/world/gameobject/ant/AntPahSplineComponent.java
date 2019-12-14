package game.world.gameobject.ant;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AntPahSplineComponent extends Component implements Drawable{

	private List<Vec2d> controlPoints;
	
	private boolean enabled = false;
	
	public AntPahSplineComponent(String tag, GameObject object, List<Vec2d> controlPoints) {
		super(tag, object);
		// TODO Auto-generated constructor stub
		this.controlPoints = controlPoints;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
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
	public void onDraw(GraphicsContext graphicsCx) {
		// TODO Auto-generated method stub
		if(this.enabled)
		{
			drawLines(graphicsCx);	
		}
		
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}
	
	private Vec2d getSplinePoint(double t)
	{
	    	int p0,p1,p2,p3;
	    	p1 = (int)t +1;
	    	p2 = p1 + 1;
	    	p3 = p2 + 1;
	    	p0 = p1 - 1;
	    	
	    	t = t - (int)t;
	    	
	    	double tt = t * t;
	    	double ttt = tt * t;
	    	
	    	double q1 = -ttt +2.0*tt -t;
	    	double q2 = 3.0 * ttt -5.0 *tt+2.0;
	    	double q3 = -3.0 * ttt +4.0 *tt +t;
	    	double q4 = ttt - tt;
	    	
	    	if(p1 < this.controlPoints.size()  &&
	    	   p2 < this.controlPoints.size()  &&	
	    	   p3 < this.controlPoints.size()  &&
	    	   p0 < this.controlPoints.size() )
	    	{
	    		Vec2d controlP0 = this.controlPoints.get(p0);
	        	Vec2d controlP1 = this.controlPoints.get(p1);
	        	Vec2d controlP2 = this.controlPoints.get(p2);
	        	Vec2d controlP3 = this.controlPoints.get(p3);
	        	
	        	double x = 0.5 *( controlP0.x * q1 + controlP1.x * q2 + controlP2.x * q3 + controlP3.x * q4);
	        	double y = 0.5 * (controlP0.y * q1 + controlP1.y * q2 + controlP2.y * q3 + controlP3.y * q4);
	        	
	        	return new Vec2d(x,y);	
	    	}
	    	
	    	return null;
	    	
	    	
	 }
	
	
	 private void drawLines(GraphicsContext graphicsCx)
	 {
	      double delta =  0.005;
	      for(double t = 0; t < (double) this.controlPoints.size() -3.0 ; t += delta)
	      {
	    	  
	    	  Vec2d start = getSplinePoint(t);
	    	  Vec2d end = getSplinePoint(t + delta);
	    	 
	    	  if( start ==  null || end == null)
	    	  {
	    		  continue;  
	    	  }
	    	  else
	    	  {
	    		  Vec2d startPosScreenCords =  this.getObject().gameToScreen(start, false);
	    		  Vec2d endPosScreenCords =  this.getObject().gameToScreen(end, false);
	    		  graphicsCx.setStroke(Color.RED);
	    		  graphicsCx.strokeLine(startPosScreenCords.x,startPosScreenCords.y,
	    				  endPosScreenCords.x,endPosScreenCords.y);	  
	    	  }

	      }
	 }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}

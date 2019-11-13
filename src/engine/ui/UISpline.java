package engine.ui;

import java.util.ArrayList;
import java.util.List;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UISpline extends UIElement{
	
	private class ControlPoint extends UIRectangle
	{
		public ControlPoint(Vec2d position, Vec2d size) {
			super(position, size);
		}
		
		@Override
		public void onDraw(GraphicsContext graphicsCx) 
		{
			if (this.isMouseOver()) {
				myColor = Color.rgb(255, 255, 0);
			} else {
				myColor = Color.rgb(0, 0, 0);
			}
			
			super.onDraw(graphicsCx);
		}
		
		@Override
		public void onMouseClicked(MouseEvent e) {
			
			super.onMouseClicked(e);
			System.out.println("CLICK ControlPoint");
		}
		
		@Override
		public void onMouseDragged(MouseEvent e) {
			super.onMouseDragged(e);

		}
	
	}
	
	private List<UIElement> myControlPoints = new ArrayList<UIElement>();
	
	public UISpline(List<Vec2d> controlPoints) 
	{
		for(Vec2d point: controlPoints)
		{
			UIElement rectControlPoint = new ControlPoint(point, new Vec2d(5,5));
			//this.add(rectControlPoint);
			myControlPoints.add(rectControlPoint);
			
		}
		
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
    	
    	if(p1 < myControlPoints.size()  &&
    	   p2 < myControlPoints.size()  &&	
    	   p3 < myControlPoints.size()  &&
    	   p0 < myControlPoints.size() )
    	{
    		Vec2d controlP0 = ((UIElement)myControlPoints.get(p0)).getAbsoutePosition();
        	Vec2d controlP1 = ((UIElement)myControlPoints.get(p1)).getAbsoutePosition();
        	Vec2d controlP2 = ((UIElement)myControlPoints.get(p2)).getAbsoutePosition();
        	Vec2d controlP3 = ((UIElement)myControlPoints.get(p3)).getAbsoutePosition();
        	
        	double x = 0.5 *( controlP0.x * q1 + controlP1.x * q2 + controlP2.x * q3 + controlP3.x * q4);
        	double y = 0.5 * (controlP0.y * q1 + controlP1.y * q2 + controlP2.y * q3 + controlP3.y * q4);
        	
        	return new Vec2d(x,y);	
    	}
    	
    	return null;
    	
    	
    }

    private void drawLines(GraphicsContext graphicsCx)
    {
      double delta =  0.005;
      for(double t = 0; t < (double) myControlPoints.size() -3.0 ; t += delta)
      {
    	  
    	  Vec2d start = getSplinePoint(t);
    	  Vec2d end = getSplinePoint(t + delta);
    	 
    	  if( start ==  null || end == null)
    	  {
    		  continue;  
    	  }
    	  else
    	  {
    		  graphicsCx.strokeLine(start.x,start.y,end.x,end.y);	  
    	  }

      }
    }
    
    
    
	@Override
	public void onDraw(GraphicsContext graphicsCx) 
	{
	   	super.onDraw(graphicsCx);
	   	drawLines(graphicsCx);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);
		System.out.println("CLICKED SPLINE");
	}

	public List<UIElement> getControlPoints() {
		return myControlPoints;
	}

	
	 
	
}

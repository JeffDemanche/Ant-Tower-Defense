package engine.ui;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UILine extends UIElement{

	private Color myColor = Color.rgb(0, 0, 0);
	private double myLineWidth = 1.0;
	
	public UILine(Vec2d start,Vec2d end) {
		// TODO Auto-generated constructor stub
		super(start, end.minus( start));
	}
	
	public UILine(Vec2d start,Vec2d end, Color color) {
		// TODO Auto-generated constructor stub
		super(start, end.minus( start));
		myColor = color;
		
	}
	
	public UILine(Vec2d start,Vec2d end, double lineWidth) {
		// TODO Auto-generated constructor stub
		super(start, end.minus( start));
		myLineWidth = lineWidth;
	}
	
	public UILine(Vec2d start,Vec2d end, Color color, double lineWidth) {
		// TODO Auto-generated constructor stub
		super(start, end.minus( start));
		myLineWidth = lineWidth;
		myColor = color;
	}
	
	@Override
	public void onDraw(GraphicsContext graphicsCx) {
		Vec2d start = this.getAbsoutePosition();
		Vec2d end =  start.plus(this.getSize());
		
		graphicsCx.setLineWidth(myLineWidth);
		graphicsCx.setStroke(myColor);
		graphicsCx.strokeLine(start.x,start.y,end.x,end.y);

		super.onDraw(graphicsCx);
	}
}

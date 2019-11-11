package engine.ui;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class UIRectangle extends UIElement{

	protected Color myColor = Color.rgb(1, 1, 1);
	private double cornerArcRadius;
	
	public UIRectangle(Vec2d position, Vec2d size) {
		
		super(position,size);
		this.cornerArcRadius = 0D;
	}
	
    public UIRectangle(Vec2d position, Vec2d size, Color color) {
		
		super(position,size);
		myColor = color;
		this.cornerArcRadius = 0D;
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d pos = this.getAbsoutePosition();
		
		g.setFill(this.myColor);
		
		g.fillRoundRect(pos.x, pos.y, this.getSize().x, this.getSize().y,
				this.cornerArcRadius, this.cornerArcRadius);

		super.onDraw(g);
	}
	
	
}

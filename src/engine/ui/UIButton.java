package engine.ui;

import application.Vec2d;
import engine.ui.UITextLabel.HorizontalAlign;
import engine.ui.UITextLabel.VerticalAlign;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * A extensible button class that does button things.
 * 
 * @author jdemanch
 */
public class UIButton extends UIElement {

	private Paint background;
	private Paint hoverBackground;

	private UITextLabel textLabel;

	private double cornerArcRadius;

	public UIButton(Vec2d position, Vec2d size, String text, Font labelFont,
			Paint foreground, Paint background) {
		super(position, size);
		this.cornerArcRadius = 0D;
		this.background = background;
		this.textLabel = new UITextLabel(new Vec2d(0), this.getSize(),
				HorizontalAlign.CENTER, VerticalAlign.MIDDLE, text, labelFont,
				foreground);

		try {
			this.add(textLabel);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	public void setBackground(Paint p) {
		this.background = p;
	}
	
	public void setBackgroundHovered(Paint p) {
		this.hoverBackground = p;
	}

	public void setText(String text) {
		this.textLabel.setText(text);
	}

	public void setArcRadius(double arcRadius) {
		this.cornerArcRadius = arcRadius;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d pos = this.getAbsoutePosition();
		
		if (this.isMouseOver()) {
			g.setFill(this.hoverBackground);
		} else {
			g.setFill(this.background);
		}

		g.fillRoundRect(pos.x, pos.y, this.getSize().x, this.getSize().y,
				this.cornerArcRadius, this.cornerArcRadius);

		super.onDraw(g);
	}

}

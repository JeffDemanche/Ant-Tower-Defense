package engine.ui;

import application.FontMetrics;
import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * A text-rendering UI element.
 * 
 * @author jdemanch
 */
public class UITextLabel extends UIElement {

	private String text;
	private Font font;
	private Paint color;
	private HorizontalAlign horizontalAlign;
	private VerticalAlign verticalAlign;

	private FontMetrics fontMetrics;

	public enum HorizontalAlign {
		LEFT, CENTER, RIGHT
	}

	public enum VerticalAlign {
		TOP, MIDDLE, BOTTOM
	}

	public UITextLabel(Vec2d position, String text, Font font, Paint color) {
		this(position, new Vec2d(0), HorizontalAlign.LEFT, VerticalAlign.TOP,
				text, font, color);
	}

	public UITextLabel(Vec2d position, Vec2d size, HorizontalAlign hAlign,
			VerticalAlign vAlign, String text, Font font, Paint color) {
		super(position, size);

		this.text = text;
		this.font = font;
		this.color = color;
		this.horizontalAlign = hAlign;
		this.verticalAlign = vAlign;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);

		Vec2d pos = this.getAbsoutePosition();

		this.fontMetrics = new FontMetrics(text, font);
		double fontWidth = fontMetrics.width;
		double fontHeight = fontMetrics.height;

		g.setFont(this.font);
		g.setFill(this.color);

		double posX = 0;
		double posY = 0;

		switch (this.horizontalAlign) {
		case LEFT:
			posX = pos.x;
			break;
		case CENTER:
			posX = pos.x + ((this.getSize().x - fontWidth) / 2);
			break;
		case RIGHT:
			posX = pos.x + (this.getSize().x - fontWidth);
			break;
		}

		switch (this.verticalAlign) {
		case TOP:
			posY = pos.y + fontHeight;
			break;
		case MIDDLE:
			posY = pos.y + (this.getSize().y / 2) + (fontHeight / 2);
			break;
		case BOTTOM:
			posY = pos.y + this.getSize().y;
			break;
		}

		g.fillText(this.text, posX, posY);
	}

}

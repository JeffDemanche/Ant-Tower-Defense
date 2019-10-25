package engine.world.gameobject.gui;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ComponentGUITextRenderer extends Component {

	private String text;
	private Vec2d screenPos;
	private Font font;

	public ComponentGUITextRenderer(String tag, GameObject object, String text,
			Vec2d screenPos, Font font) {
		super(tag, object);

		this.text = text;
		this.screenPos = screenPos;
		this.font = font;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setScreenPos(Vec2d screenPos) {
		this.screenPos = screenPos;
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
		g.setFont(this.font);
		g.setFill(Color.WHITE);
		g.fillText(this.text, screenPos.x, screenPos.y);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

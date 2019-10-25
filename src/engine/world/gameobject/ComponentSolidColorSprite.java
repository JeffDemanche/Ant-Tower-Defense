package engine.world.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ComponentSolidColorSprite extends Component {

	private Color color;
	private Drawable drawable;

	public ComponentSolidColorSprite(GameObject object, Color color,
			Drawable drawable) {
		super("Solid Color Sprite", object);
		this.color = color;
		this.drawable = drawable;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		g.save();
		g.setFill(this.color);
		g.fillRect(drawable.getScreenPosition().x,
				drawable.getScreenPosition().y, drawable.getScreenSize().x,
				drawable.getScreenSize().y);
		g.restore();
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

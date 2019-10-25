package engine.world.gameobject;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ComponentDraggable extends Component {

	private EventHandler.MouseListener mouseHandler;

	/** In game space. */
	private Vec2d beginPosition;
	private Vec2d dragPosition;

	public ComponentDraggable(String tag, GameObject object,
			Drawable drawable) {
		super(tag, object);
		this.mouseHandler = new EventHandler.MouseListener() {
			@Override
			public void onMouseReleased(MouseEvent e) {
				if (!e.isConsumed()) {
					if (dragPosition != beginPosition) {
						e.consume();
					}
					if (e.getButton() == MouseButton.PRIMARY) {
						dragPosition = null;
					}
				}
			}

			@Override
			public void onMousePressed(MouseEvent e) {
				if (!e.isConsumed()) {
					if (e.getButton() == MouseButton.PRIMARY) {
						dragPosition = object.screenToGame(
								new Vec2d(e.getSceneX(), e.getSceneY()), false);
						beginPosition = dragPosition;
					}
					e.consume();
				}
			}

			@Override
			public void onMouseMoved(MouseEvent e) {
			}

			@Override
			public void onMouseDragged(MouseEvent e) {
				if (!e.isConsumed()) {
					if (e.getButton() == MouseButton.PRIMARY) {
						Vec2d newPosition = object.screenToGame(
								new Vec2d(e.getSceneX(), e.getSceneY()), false);

						if (dragPosition != null) {
							drawable.adjustPosition(
									new Vec2d(newPosition.x - dragPosition.x,
											newPosition.y - dragPosition.y));
							dragPosition = newPosition;
							e.consume();
						}

					}
				}
			}

			@Override
			public void onMouseClicked(MouseEvent e) {
			}
		};
	}

	public EventHandler.MouseListener getMouseListener() {
		return this.mouseHandler;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
		this.getObject().getEventHandler().removeMouseLister(mouseHandler);
	}

}

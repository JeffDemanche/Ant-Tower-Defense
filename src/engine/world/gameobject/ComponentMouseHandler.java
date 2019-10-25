package engine.world.gameobject;

import application.Vec2d;
import engine.world.gameobject.EventHandler.MouseListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ComponentMouseHandler extends Component {

	private MouseListener listener;

	public ComponentMouseHandler(String tag, GameObject object,
			Drawable position, MouseListener listener) {
		super(tag, object);
		this.listener = new MouseListener() {
			@Override
			public void onMouseReleased(MouseEvent e) {
				if (position.insideBB(new Vec2d(e.getSceneX(), e.getSceneY())))
					listener.onMouseReleased(e);
			}

			@Override
			public void onMousePressed(MouseEvent e) {
				if (position.insideBB(new Vec2d(e.getSceneX(), e.getSceneY())))
					listener.onMousePressed(e);
			}

			@Override
			public void onMouseMoved(MouseEvent e) {
				if (position.insideBB(new Vec2d(e.getSceneX(), e.getSceneY())))
					listener.onMouseMoved(e);
			}

			@Override
			public void onMouseDragged(MouseEvent e) {
				if (position.insideBB(new Vec2d(e.getSceneX(), e.getSceneY())))
					listener.onMouseDragged(e);
			}

			@Override
			public void onMouseClicked(MouseEvent e) {
				if (position.insideBB(new Vec2d(e.getSceneX(), e.getSceneY())))
					listener.onMouseClicked(e);
			}
		};
		object.getEventHandler().registerMouseListener(this.listener);
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
		this.getObject().getEventHandler().removeMouseLister(this.listener);
	}

}

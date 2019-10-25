package engine.world.gameobject;

import engine.world.gameobject.EventHandler.KeyListener;
import javafx.scene.canvas.GraphicsContext;

/**
 * Handles Key listening. (Currently) distributes events 
 * @author jdemanch
 */
public class ComponentKeyHandler extends Component {

	private KeyListener listener;

	public ComponentKeyHandler(String tag, GameObject object,
			KeyListener keyListener) {
		super(tag, object);
		this.listener = keyListener;
		object.getEventHandler().registerKeyListener(keyListener);
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
		this.getObject().getEventHandler().removeKeyListener(this.listener);
	}

}

package engine.world.gameobject;

import javafx.scene.canvas.GraphicsContext;

/**
 * A component is some behavior which belongs to a game object and influences
 * its behavior.
 * 
 * @author jdemanch
 */
public abstract class Component {

	private String tag;
	private GameObject object;

	public Component(String tag, GameObject object) {
		this.tag = tag;
		this.object = object;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return tag;
	}

	/**
	 * Gets the GameObject this component is attached to.
	 * 
	 * @return The GameObject object.
	 */
	public GameObject getObject() {
		return this.object;
	}

	/**
	 * Called on the component every draw.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public abstract void onDraw(GraphicsContext g);

	/**
	 * Called on the component every tick.
	 * 
	 * @param nanosSincePreviousTick
	 *            Delta since last tick.
	 */
	public abstract void onTick(long nanosSincePreviousTick);

	/**
	 * Called when a gameobject is removed from the system it belongs to. This
	 * should unregister all event handlers, etc.
	 */
	public abstract void onGameObjectRemoved();

}

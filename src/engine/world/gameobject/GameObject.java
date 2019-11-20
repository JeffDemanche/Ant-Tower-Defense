package engine.world.gameobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.serialization.XMLSerializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * A GameObject is some kind of element in the game (sound, character, wall,
 * etc.)
 * 
 * @author jdemanch
 */
public abstract class GameObject implements XMLSerializable {

	private String name;
	private ArrayList<Component> components;
	private GameSystem system;

	public GameObject(GameSystem system, String name) {
		this.components = new ArrayList<>();
		this.system = system;
		this.name = name;
	}

	public void addComponent(Component c) {
		this.components.add(c);
	}

	public void removeComponent(Component c) {
		this.components.remove(c);
	}

	public void remove() {
		for (Component c : components) {
			c.onGameObjectRemoved();
		}
		if (this.getSystem().getWorld().getViewportFocus() == this
				.getDrawable()) {
			this.getSystem().getWorld().setViewportFocus(null);
		}
		this.system.removeGameObject(this);
	}

	public String getName() {
		return this.name;
	}

	public boolean isCollidable() {
		for (Component c : this.components) {
			if (c instanceof ComponentAABB || c instanceof ComponentCircle
					|| c instanceof ComponentPolygon) {
				return true;
			}
		}
		return true;
	}

	public Drawable getDrawable() {
		for (Component c : this.components) {
			if (c instanceof Drawable) {
				return (Drawable) c;
			}
		}
		return null;
	}

	public Collidable getCollider() {
		for (Component c : this.components) {
			if (c instanceof Collidable) {
				return (Collidable) c;
			}
		}
		return null;
	}

	public boolean isActiveCollider() {
		for (Component c : this.components) {
			if (c.getTag().equals("Active Collider")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets active collider layers, or null if not an active collider.
	 */
	public Set<String> getActiveColliderLayers() {
		for (Component c : this.components) {
			if (c.getTag().equals("Active Collider")) {
				return ((ComponentActiveCollider) c).getLayers();
			}
		}
		return null;
	}

	public boolean isPassiveCollider(Set<String> layers) {
		for (Component c : this.components) {
			if (c.getTag().equals("Passive Collider") && layers.isEmpty()) {
				return true;
			}
			if (c.getTag().equals("Passive Collider") && layers
					.contains(((ComponentPassiveCollider) c).getLayer())) {
				return true;
			}
		}
		return false;
	}

	public boolean isPhysicsCollider(Set<String> layers) {
		for (Component c : this.components) {
			if (c.getTag().equals("Physics Collider")) {
				if (layers == null || layers.isEmpty()) {
					return true;
				} else if (layers
						.contains(((ComponentPhysicsCollider) c).getLayer())) {
					return true;
				}
			}
		}
		return false;
	}

	public Set<String> getPhysicsColliderLayers() {
		for (Component c : this.components) {
			if (c.getTag().equals("Physics Collider")) {
				return ((ComponentPhysicsCollider) c).getCollidesWith();
			}
		}
		return null;
	}

	/**
	 * Gets the game system that this game object belongs to.
	 * 
	 * @return The GameSystem object.
	 */
	public GameSystem getSystem() {
		return this.system;
	}

	public EventHandler getEventHandler() {
		return this.system.getEventHandler();
	}

	/**
	 * Gives screen space coords corresponding to a given pair of game space
	 * coords.
	 * 
	 * @param gameCoords
	 *            The game space coords.
	 * @param size
	 *            True if converting size vector.
	 * @return The screen space coords.
	 */
	public Vec2d gameToScreen(Vec2d gameCoords, boolean size) {
		return this.getSystem().gameToScreen(gameCoords, size);
	}

	public Vec2d screenToGame(Vec2d gameCoords, boolean size) {
		return this.getSystem().screenToGame(gameCoords, size);
	}

	/**
	 * Gets a component based on its tag identifier.
	 * 
	 * @param componentTag
	 *            The tag.
	 * @return The first component with this tag, or null if not found.
	 */
	public Component getComponent(String componentTag) {
		// Would love to use functional here but idk if it's worth it.
		for (Component c : this.components) {
			if (c.getTag().equals(componentTag)) {
				return c;
			}
		}
		return null;
	}

	public void onTick(long nanosSincePreviousTick) {
		components
				.forEach(component -> component.onTick(nanosSincePreviousTick));
	}

	public void onDraw(GraphicsContext g) {
		components.forEach(component -> component.onDraw(g));
	}

	public void onResize(Vec2d newSize) {
	}

	public void onMousePressed(MouseEvent e) {
	}

	public void onMouseDragged(MouseEvent e) {
	}

	public void onMouseReleased(MouseEvent e) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		return true;
	}
}

package engine.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import application.Vec2d;
import engine.world.gameobject.ComponentActiveCollider;
import engine.world.gameobject.ComponentPhysics;
import engine.world.gameobject.ComponentPhysicsCollider;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.EventHandler;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class World {

	private Viewport worldViewport;
	private ArrayList<GameSystem> systems;
	private EventHandler eventHandler;

	private Vec2d dragPosition;

	private GameObject currentlyDraggedObj;

	/** The viewport will track this drawable component. */
	private Drawable viewportFocus;

	private TileCoordinates coordinateSystem;

	public World(Viewport viewport) {
		this.worldViewport = viewport;
		this.systems = new ArrayList<>();
		this.eventHandler = new EventHandler();
		this.currentlyDraggedObj = null;
	}

	public Viewport getViewport() {
		return this.worldViewport;
	}

	public void addSystem(GameSystem system) {
		this.systems.add(system);
	}

	public EventHandler getEventHandler() {
		return this.eventHandler;
	}

	/**
	 * Put null as layers to get all physics colliders.
	 */
	public Set<GameObject> getPhysicsColliders(Set<String> layers) {
		HashSet<GameObject> colliders = new HashSet<>();
		for (GameSystem system : this.systems) {
			for (GameObject obj : system.getCollidableObjects()) {
				if (obj.isPhysicsCollider(layers)) {
					colliders.add(obj);
				}
			}
		}
		return colliders;
	}

	public Set<GameObject> getActiveColliders() {
		HashSet<GameObject> colliders = new HashSet<>();
		for (GameSystem system : this.systems) {
			for (GameObject obj : system.getCollidableObjects()) {
				if (obj.isActiveCollider()) {
					colliders.add(obj);
				}
			}
		}
		return colliders;
	}

	/**
	 * @param layer
	 *            If not an empty string, returns all passive colliders denoted
	 *            to be on the layer.
	 */
	public Set<GameObject> getPassiveColliders(Set<String> layers) {
		HashSet<GameObject> colliders = new HashSet<>();
		for (GameSystem system : this.systems) {
			for (GameObject obj : system.getCollidableObjects()) {
				if (obj.isPassiveCollider(layers)) {
					colliders.add(obj);
				}
			}
		}
		return colliders;
	}

	public GameObject getDraggedObj() {
		return this.currentlyDraggedObj;
	}

	/**
	 * Sets the game object the viewport position should track. Use null for no
	 * tracking.
	 */
	public void setViewportFocus(GameObject gameObject) {
		if (gameObject == null) {
			this.viewportFocus = null;
		} else {
			this.viewportFocus = gameObject.getDrawable();
		}
	}

	public Drawable getViewportFocus() {
		return this.viewportFocus;
	}

	public void setDraggedObj(GameObject obj) {
		this.currentlyDraggedObj = obj;
	}

	public void setCoordinateSystem(TileCoordinates coords) {
		this.coordinateSystem = coords;
	}

	/**
	 * Gets the coordinate system, or null if it's not set (it still could
	 * exist, just not registered with the world).
	 */
	public TileCoordinates getCoordinateSystem() {
		return this.coordinateSystem;
	}

	public void onTick(long nanosSincePreviousTick) {
		// Active and passive collisions with layers are handled centrally.
		for (GameObject activeCollider : this.getActiveColliders()) {
			Set<String> layers = activeCollider.getActiveColliderLayers();
			for (GameObject passiveCollider : this
					.getPassiveColliders(layers)) {
				Vec2d mtv = passiveCollider.getCollider()
						.collidesMTV(activeCollider.getCollider());

				if (mtv != null) {
					if (!((ComponentActiveCollider) activeCollider
							.getComponent("Active Collider"))
									.isPhysicsEnabled()) {
						activeCollider.getDrawable().adjustPosition(mtv);
					}
					((ComponentActiveCollider) activeCollider
							.getComponent("Active Collider"))
									.setCollidedThisTick(mtv);
				}
			}
		}

		for (GameObject physicsCollider : this.getPhysicsColliders(null)) {
			Set<String> layers = physicsCollider.getPhysicsColliderLayers();
			for (GameObject otherCollider : this.getPhysicsColliders(layers)) {
				if (physicsCollider == otherCollider)
					continue;

				Vec2d mtvPhysics = physicsCollider.getCollider()
						.collidesMTV(otherCollider.getCollider());
				Vec2d mtvOther = mtvPhysics == null ? null : mtvPhysics.reflect();//otherCollider.getCollider()
						//.collidesMTV(physicsCollider.getCollider());

				((ComponentPhysicsCollider) physicsCollider
						.getComponent("Physics Collider")).onCollide(mtvPhysics,
								(ComponentPhysics) otherCollider
										.getComponent("Physics"));
				((ComponentPhysicsCollider) otherCollider
						.getComponent("Physics Collider")).onCollide(mtvOther,
								(ComponentPhysics) physicsCollider
										.getComponent("Physics"));
			}
		}

		this.systems.forEach(system -> system.onTick(nanosSincePreviousTick));

		if (this.viewportFocus != null) {
			this.worldViewport.tickTrackedViewport(
					viewportFocus.getPosition().x
							+ viewportFocus.getSize().x / 2,
					viewportFocus.getPosition().y
							+ viewportFocus.getSize().y / 2);
		}

	}

	public void onDraw(GraphicsContext g) {
		this.systems.forEach(system -> system.onDraw(g));
	}

	public void onResize(Vec2d newSize) {
		this.getViewport().onResize(newSize);
	}

	public void onKeyTyped(KeyEvent e) {
		eventHandler.dispatchKeyEvent("KeyType", e);
	}

	public void onKeyPressed(KeyEvent e) {
		eventHandler.dispatchKeyEvent("KeyPress", e);
	}

	public void onKeyReleased(KeyEvent e) {
		eventHandler.dispatchKeyEvent("KeyRelease", e);
	}

	public void onMouseClicked(MouseEvent e) {
		eventHandler.dispatchMouseEvent("MouseClick", e);
	}

	public void onMouseMoved(MouseEvent e) {
		eventHandler.dispatchMouseEvent("MouseMove", e);
	}

	public void onMouseWheelMoved(ScrollEvent e) {
		double verticalDelta = e.getDeltaY();
		if (!e.isConsumed()) {
			getViewport().zoomViewport(verticalDelta);
		}
	}

	public void onMousePressed(MouseEvent e) {
		eventHandler.dispatchMouseEvent("MousePress", e);

		if (!e.isConsumed()) {
			if (e.getButton() == MouseButton.PRIMARY) {
				this.dragPosition = new Vec2d(e.getSceneX(), e.getSceneY());
			}
		}
	}

	public void onMouseDragged(MouseEvent e) {
		eventHandler.dispatchMouseEvent("MouseDrag", e);

		if (!e.isConsumed()) {
			if (e.getButton() == MouseButton.PRIMARY) {
				Vec2d newPosition = new Vec2d(e.getSceneX(), e.getSceneY());

				if (this.dragPosition != null) {
					getViewport().translateViewport(
							this.dragPosition.x - newPosition.x,
							this.dragPosition.y - newPosition.y);
				}

				this.dragPosition = newPosition;
			}
		}
	}

	public void onMouseReleased(MouseEvent e) {
		eventHandler.dispatchMouseEvent("MouseRelease", e);

		if (!e.isConsumed()) {
			if (e.getButton() == MouseButton.PRIMARY) {
				this.dragPosition = null;
			}
		}
	}

	public void onFocusChanged(boolean newVal) {
	}

	public void onShutdown() {
		this.systems.forEach(system -> system.onShutdown());
	}

	public void onStartup() {
		this.systems.forEach(system -> system.onStartup());
	}
}

package engine.world.gameobject;

import java.util.ArrayList;

import engine.world.WorldError;
import javafx.scene.canvas.GraphicsContext;

public class ComponentCollidable extends Component {

	public interface CollisionHandler {
		public void onCollide(GameObject obj);
	}

	private ArrayList<GameObject> collisions;
	private Collidable thisCollider;
	private CollisionHandler handler;

	public ComponentCollidable(String tag, GameObject object,
			Collidable thisCollider, CollisionHandler handler) {
		super(tag, object);
		this.collisions = new ArrayList<>();
		this.thisCollider = thisCollider;
		this.handler = handler;
	}

	public void addCollision(GameObject object) {
		if (!object.isCollidable()) {
			throw new WorldError(
					"Tried to add non-collidable object for collision");
		}
		this.collisions.add(object);
	}
	
	protected CollisionHandler getCollisionHandler() {
		return this.handler;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		for (GameObject obj : this.getObject().getSystem()
				.getCollidableObjects()) {
			if (obj != this.getObject()
					&& thisCollider.collides(obj.getCollider())) {
				this.handler.onCollide(obj);
			}
		}
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

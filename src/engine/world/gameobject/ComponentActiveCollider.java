package engine.world.gameobject;

import java.util.Set;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class ComponentActiveCollider extends Component {

	private Set<String> layers;
	private Collidable collidable;

	private Vec2d collidedThisTick;
	private Vec2d lastCollision;
	
	private boolean physicsEnabled;

	public ComponentActiveCollider(Set<String> layers, GameObject object,
			Collidable collidable) {
		super("Active Collider", object);
		this.layers = layers;
		this.collidable = collidable;
		this.collidedThisTick = null;
		this.physicsEnabled = false;
	}

	public void setCollidedThisTick(Vec2d mtv) {
		if (this.collidedThisTick == null) {
			this.collidedThisTick = mtv;
		} else {
			this.collidedThisTick = this.collidedThisTick.plus(mtv);
		}
	}
	
	public void setPhysicsEnabled(boolean physics) {
		this.physicsEnabled = physics;
	}

	public boolean onGround() {
		return lastCollision != null && lastCollision.y < 0;
	}

	public Vec2d getCollisionMTV() {
		return collidedThisTick;
	}

	public Set<String> getLayers() {
		return this.layers;
	}

	public Collidable getCollidable() {
		return this.collidable;
	}
	
	public boolean isPhysicsEnabled() {
		return this.physicsEnabled;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		this.lastCollision = this.collidedThisTick;
		this.collidedThisTick = null;
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

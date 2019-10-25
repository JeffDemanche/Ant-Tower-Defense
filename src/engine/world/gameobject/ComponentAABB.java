package engine.world.gameobject;

import application.Vec2d;
import engine.world.WorldError;
import javafx.scene.canvas.GraphicsContext;

/**
 * Gives a game object a position and bounding box in game space.
 * 
 * @author jdemanch
 */
public class ComponentAABB extends Component implements Collidable, Drawable {

	private Vec2d position;
	private Vec2d size;

	private Vec2d impulse;
	private double impulseSpeed;

	/**
	 * Legacy constructor
	 */
	public ComponentAABB(String tag, GameObject object, Vec2d initialPos,
			Vec2d initialSize) {
		this(object, initialPos, initialSize);
	}

	public ComponentAABB(GameObject object, Vec2d initialPos,
			Vec2d initialSize) {
		super("AABB", object);
		this.setPosition(initialPos);
		this.setSize(initialSize);

		this.impulse = new Vec2d(0, 0);
		this.impulseSpeed = 0;
	}

	public void setPosition(Vec2d newPosition) {
		this.position = newPosition;
	}

	public void setSize(Vec2d newSize) {
		if (newSize.x < 0 || newSize.y < 0) {
			throw new WorldError(
					"GameObject with position component given negative size.");
		}
		this.size = newSize;
	}

	public void impulse(Vec2d vector, double speed) {
		this.impulse = vector;
		this.impulseSpeed = speed;
	}

	public Vec2d getGamePosition() {
		return this.position;
	}

	/**
	 * Gets the position of the the object in screen space.
	 * 
	 * @return A Vec2d in screen space.
	 */
	@Override
	public Vec2d getScreenPosition() {
		return this.getObject().gameToScreen(this.position, false);
	}

	@Override
	public Vec2d getSize() {
		return this.size;
	}

	/**
	 * Gets the size of the object in screen space.
	 * 
	 * @return A Vec2d in screen space.
	 */
	public Vec2d getScreenSize() {
		return this.getObject().gameToScreen(this.size, true);
	}

	/**
	 * Sets position relative to the current position.
	 * 
	 * @param change
	 *            The offset amount.
	 */
	public void adjustPosition(Vec2d change) {
		this.setPosition(new Vec2d(this.position.x + change.x,
				this.position.y + change.y));
	}

	/**
	 * Sets size relative to the current size.
	 * 
	 * @param change
	 *            The change in size.
	 */
	public void adjustSize(Vec2d change) {
		this.setSize(new Vec2d(this.size.x + change.x, this.size.y + change.y));
	}

	@Override
	public boolean insideBB(Vec2d screenPos) {
		return Collisions.pointToAABB(
				this.getObject().screenToGame(screenPos, false), this);
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		if (impulse.mag() > 0) {
			double componentX = impulse.x - (impulse.x / (1
					+ impulseSpeed * (nanosSincePreviousTick / 1000000000D)));
			double componentY = impulse.y - (impulse.y / (1
					+ impulseSpeed * (nanosSincePreviousTick / 1000000000D)));

			if (Math.abs(impulse.x) < 10E-4 && Math.abs(impulse.y) < 10E-4) {
				this.adjustPosition(impulse);
				impulse = new Vec2d(0);
			} else {
				this.adjustPosition(new Vec2d(componentX, componentY));
				impulse = impulse.minus(new Vec2d(componentX, componentY));
			}
		}
	}

	@Override
	public boolean collides(Collidable collider) {
		return collider.collidesAABB(this);
	}

	@Override
	public Vec2d collidesMTV(Collidable collider) {
		return collider.collidesAABBMTV(this);
	}

	@Override
	public boolean collidesAABB(Collidable collider) {
		if (!(collider instanceof ComponentAABB)) {
			throw new WorldError(
					"Expected double dispatch to be AABB collider");
		}
		ComponentAABB aabbCollider = (ComponentAABB) collider;
		return Collisions.AABBtoAABB(this, aabbCollider);
	}

	@Override
	public Vec2d collidesAABBMTV(Collidable collider) {
		if (!(collider instanceof ComponentAABB)) {
			throw new WorldError(
					"Expected double dispatch to be AABB collider");
		}
		ComponentAABB aabbCollider = (ComponentAABB) collider;
		Vec2d v = Collisions.mtvAABBtoAABB(this, aabbCollider);
		return v;
	}

	@Override
	public boolean collidesCircle(Collidable collider) {
		if (!(collider instanceof ComponentCircle)) {
			throw new WorldError(
					"Expected double dispatch to be Circle collider");
		}
		ComponentCircle circleCollider = (ComponentCircle) collider;
		return Collisions.AABBtoCircle(this, circleCollider);
	}

	@Override
	public Vec2d collidesCircleMTV(Collidable collider) {
		if (!(collider instanceof ComponentCircle)) {
			throw new WorldError(
					"Expected double dispatch to be Circle collider");
		}
		ComponentCircle circleCollider = (ComponentCircle) collider;
		return Collisions.mtvAABBtoCircle(this, circleCollider);
	}
	
	@Override
	public boolean collidesPolygon(Collidable collider) {
		return collidesPolygonMTV(collider) != null;
	}

	@Override
	public Vec2d collidesPolygonMTV(Collidable collider) {
		if (!(collider instanceof ComponentPolygon)) {
			throw new WorldError(
					"Expected double dispatch to be Polygon collider");
		}
		ComponentPolygon polygonCollider = (ComponentPolygon) collider;
		return CollisionsPolygon.mtvPolygonToAABB(this, polygonCollider);
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Vec2d getPosition() {
		return this.position;
	}

}

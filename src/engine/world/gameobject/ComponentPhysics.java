package engine.world.gameobject;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class ComponentPhysics extends Component {

	private Drawable drawable;

	private boolean stationary;

	private double mass;

	private double restitution;

	private Vec2d velocity;

	private Vec2d impulse;
	private Vec2d force;

	private Vec2d gravity;

	private ComponentPhysicsCollider collider;
	
	private Vec2d lastMTV;

	public ComponentPhysics(GameObject object, Drawable drawable,
			ComponentPhysicsCollider collider, boolean stationary, double mass,
			double restitution, Vec2d gravity) {
		super("Physics", object);

		this.mass = mass;
		if (restitution > 1 || restitution < 0)
			throw new IllegalArgumentException("Invalid restitution value");
		this.restitution = restitution;
		this.stationary = stationary;

		this.drawable = drawable;
		this.velocity = new Vec2d(0);
		this.impulse = new Vec2d(0);
		this.force = new Vec2d(0);
		this.collider = collider;

		this.gravity = gravity;
		
		this.lastMTV = null;
	}

	/**
	 * This is passed from the world when this collides with another physics
	 * object.
	 * 
	 * @param mtv
	 */
	public void recieveCollision(Vec2d mtv, ComponentPhysics otherObject) {
		this.lastMTV = mtv;
		
		if (!stationary) {
			if (mtv != null) {
				drawable.adjustPosition(mtv.reflect());
				// System.out.println("here");
				double COR = Math
						.sqrt(this.restitution * otherObject.restitution);

				double u_a = this.velocity.dot(mtv.normalize());
				// TODO Should mtv be reflected?
				double u_b = otherObject.velocity
						.dot(mtv.normalize().reflect());

				double I_a = ((this.mass * otherObject.mass * (1 + COR))
						/ (this.mass + otherObject.mass)) * (u_b - u_a);

				Vec2d impulse = mtv.smult(I_a);
				if (!(Double.isNaN(impulse.x) || Double.isNaN(impulse.y)))
					applyImpulse(impulse);
			}
		}
	}
	
	public void jumpIfOnGround(double impulse) {
		if (Math.abs(velocity.y) < 5) {
			this.applyImpulse(new Vec2d(0, -impulse));
		}
	}

	public void applyForce(Vec2d f) {
		if (!stationary)
			this.force = this.force.plus(f);
	}

	public void applyImpulse(Vec2d p) {
		if (!stationary)
			this.impulse = this.impulse.plus(p);
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		double seconds = nanosSincePreviousTick / 1000000000D;
		if (seconds > 1)
			return;
		
		this.force = gravity;

		this.velocity = this.velocity.plus(force.sdiv((float) mass)
				.smult(seconds).plus(impulse.sdiv((float) mass)));
		this.drawable.adjustPosition(this.velocity.smult(seconds));

		this.force = new Vec2d(0);
		this.impulse = new Vec2d(0);
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

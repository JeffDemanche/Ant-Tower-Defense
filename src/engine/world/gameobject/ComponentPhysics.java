package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.serialization.XMLEngine;
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

	public ComponentPhysics(GameObject object, Drawable drawable,
			ComponentPhysicsCollider collider, Element element) {
		super("Physics", object);

		this.mass = Double.parseDouble(element.getAttribute("mass"));
		this.restitution = Double
				.parseDouble(element.getAttribute("restitution"));
		this.stationary = Boolean
				.parseBoolean(element.getAttribute("stationary"));
		this.drawable = drawable;
		this.velocity = XMLEngine.readVec2d(element.getAttribute("velocity"));
		this.impulse = XMLEngine.readVec2d(element.getAttribute("impulse"));
		this.force = XMLEngine.readVec2d(element.getAttribute("force"));
		this.collider = collider;
		this.gravity = XMLEngine.readVec2d(element.getAttribute("gravity"));
		this.lastMTV = XMLEngine.readVec2d(element.getAttribute("lastMTV"));
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
				double COR = Math
						.sqrt(this.restitution * otherObject.restitution);

				double u_a = this.velocity.dot(mtv.normalize());
				double u_b = otherObject.velocity
						.dot(mtv.normalize().reflect());

				double I_a = ((this.mass * otherObject.mass * (1 + COR))
						/ (this.mass + otherObject.mass)) * (u_b - u_a);

				if (otherObject.stationary) {
					I_a = -this.mass * (1 + COR) * (u_a - u_b);
				}

				Vec2d impulse = mtv.smult(I_a);
				if (!(Double.isNaN(impulse.x) || Double.isNaN(impulse.y)))
					applyImpulse(impulse);
			}
		}
	}

	public boolean isStationary() {
		return this.stationary;
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

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentPhysics = doc.createElement("ComponentPhysics");
		componentPhysics.setAttribute("stationary",
				new Boolean(stationary).toString());
		componentPhysics.setAttribute("mass", new Double(mass).toString());
		componentPhysics.setAttribute("restitution",
				new Double(restitution).toString());
		componentPhysics.setAttribute("velocity",
				XMLEngine.writeVec2d(velocity));
		componentPhysics.setAttribute("impulse", XMLEngine.writeVec2d(impulse));
		componentPhysics.setAttribute("force", XMLEngine.writeVec2d(force));
		componentPhysics.setAttribute("gravity", XMLEngine.writeVec2d(gravity));
		componentPhysics.setAttribute("lastMTV", XMLEngine.writeVec2d(lastMTV));
		return componentPhysics;
	}

}

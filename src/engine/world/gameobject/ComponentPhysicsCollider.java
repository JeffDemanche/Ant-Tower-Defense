package engine.world.gameobject;

import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class ComponentPhysicsCollider extends Component {

	private String layer;
	private Set<String> collidesWith;
	private Collidable collidable;
	private ComponentPhysics physics;

	public ComponentPhysicsCollider(GameObject object, String layer,
			Set<String> collidesWith, Collidable collidable,
			ComponentPhysics physics) {
		super("Physics Collider", object);

		this.layer = layer;
		this.collidesWith = collidesWith;
		this.collidable = collidable;
		this.physics = physics;
	}

	public boolean isPassive() {
		return this.physics.isStationary();
	}

	public String getLayer() {
		return this.layer;
	}

	public Collidable getCollidable() {
		return this.collidable;
	}

	public Set<String> getCollidesWith() {
		return this.collidesWith;
	}

	public void onCollide(Vec2d mtv, ComponentPhysics otherObject) {
		physics.recieveCollision(mtv, otherObject);
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentPhysicsCollider = doc
				.createElement("ComponentPhysicsCollider");
		return componentPhysicsCollider;
	}

}

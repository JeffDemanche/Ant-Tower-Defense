package engine.world.gameobject;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.serialization.XMLEngine;
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

	public ComponentActiveCollider(GameObject object, Collidable collidable,
			Element element) {
		super("Active Collider", object);
		this.collidable = collidable;
		this.layers = new HashSet<>(XMLEngine
				.readIterableElementAsList(element.getAttribute("layers")));
		this.collidedThisTick = XMLEngine
				.readVec2d(element.getAttribute("collidedThisTick"));
		this.lastCollision = XMLEngine
				.readVec2d(element.getAttribute("lastCollision"));

		this.physicsEnabled = Boolean
				.parseBoolean(element.getAttribute("physicsEnabled"));
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

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentActiveCollider = doc.createElement("ComponentActiveCollider");
		componentActiveCollider.setAttribute("layers",
				XMLEngine.writeIterableAsAttribute(layers));
		componentActiveCollider.setAttribute("collidedThisTick",
				XMLEngine.writeVec2d(collidedThisTick));
		componentActiveCollider.setAttribute("lastCollision",
				XMLEngine.writeVec2d(lastCollision));
		componentActiveCollider.setAttribute("physicsEnabled",
				new Boolean(physicsEnabled).toString());
		return componentActiveCollider;
	}

}

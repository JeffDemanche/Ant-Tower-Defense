package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.WorldError;
import engine.world.serialization.XMLEngine;
import javafx.scene.canvas.GraphicsContext;

/**
 * Gives a game object a position and bounding box in game space.
 * 
 * @author jdemanch
 */
public class ComponentAABB extends Component implements Collidable, Drawable {

	private Vec2d position;
	private Vec2d size;
	
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
	}

	public ComponentAABB(GameObject object, Element element) {
		this(object, XMLEngine.readVec2d(element.getAttribute("position")),
				XMLEngine.readVec2d(element.getAttribute("size")));
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

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentAABB = doc.createElement("ComponentAABB");
		componentAABB.setAttribute("position", XMLEngine.writeVec2d(this.position));
		componentAABB.setAttribute("size", XMLEngine.writeVec2d(this.size));
		return componentAABB;
	}

}

package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.WorldError;
import engine.world.serialization.XMLEngine;
import javafx.scene.canvas.GraphicsContext;

public class ComponentPolygon extends Component
		implements Collidable, Drawable {

	private Vec2d[] gameSpacePoints;
	private Vec2d[] points;
	private Vec2d position;

	public ComponentPolygon(GameObject object, Vec2d initialPos,
			Vec2d... points) {
		super("Polygon", object);

		this.setPosition(initialPos);
		setPoints(points);
	}

	public ComponentPolygon(GameObject object, Element element,
			Vec2d... points) {
		super("Polygon", object);

		this.setPosition(XMLEngine.readVec2d(element.getAttribute("position")));
		setPoints(points);
	}

	public void setPosition(Vec2d newPosition) {
		this.position = newPosition;
		this.setPoints(this.points);
	}

	/**
	 * Sets points of this poly. Not in game space.
	 * 
	 * @param points
	 *            Relative to object position.
	 */
	public void setPoints(Vec2d[] points) {
		if (points == null)
			return;

		this.points = new Vec2d[points.length];
		this.gameSpacePoints = new Vec2d[points.length];

		for (int i = 0; i < points.length; i++) {
			this.points[i] = points[i];
			this.gameSpacePoints[i] = this.position.plus(points[i]);
		}
	}

	/**
	 * Position-adjusted points (in game space).
	 */
	public Vec2d[] getPoints() {
		return this.gameSpacePoints;
	}

	/**
	 * Position-adjusted points (in game space).
	 */
	public Vec2d getPoint(int index) {
		return this.gameSpacePoints[index];
	}

	public Vec2d getScreenPoint(int index) {
		return this.getObject()
				.gameToScreen(this.position.plus(this.points[index]), false);
	}

	public int getNumPoints() {
		return this.points.length;
	}

	@Override
	public Vec2d getScreenPosition() {
		return this.getObject().gameToScreen(this.position, false);
	}

	@Override
	public Vec2d getScreenSize() {
		return this.getObject().gameToScreen(this.getSize(), true);
	}

	@Override
	public Vec2d getPosition() {
		return this.position;
	}

	@Override
	public Vec2d getSize() {
		double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE,
				minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

		for (Vec2d point : this.points) {
			if (point.x < minX) {
				minX = point.x;
			}
			if (point.y < minY) {
				minY = point.y;
			}
			if (point.x > maxX) {
				maxX = point.x;
			}
			if (point.y > maxY) {
				maxY = point.y;
			}
		}

		return new Vec2d(maxX - minX, maxY - minY);
	}

	@Override
	public void adjustPosition(Vec2d adjustment) {
		this.setPosition(this.position.plus(adjustment));
	}

	@Override
	public boolean insideBB(Vec2d screenPos) {
		return CollisionsPolygon.pointToPolygon(
				this.getObject().screenToGame(screenPos, false), this);
	}

	@Override
	public Vec2d getGamePosition() {
		return this.position;
	}

	@Override
	public boolean collides(Collidable collider) {
		return collider.collidesPolygon(this);
	}

	@Override
	public Vec2d collidesMTV(Collidable collider) {
		return collider.collidesPolygonMTV(this);
	}

	@Override
	public boolean collidesAABB(Collidable collider) {
		Vec2d c = collidesAABBMTV(collider);
		return c != null;
	}

	@Override
	public Vec2d collidesAABBMTV(Collidable collider) {
		if (!(collider instanceof ComponentAABB)) {
			throw new WorldError(
					"Expected double dispatch to be AABB collider");
		}
		ComponentAABB aabbCollider = (ComponentAABB) collider;
		return CollisionsPolygon.mtvPolygonToAABB(aabbCollider, this);
	}

	@Override
	public boolean collidesCircle(Collidable collider) {
		return collidesCircleMTV(collider) != null;
	}

	@Override
	public Vec2d collidesCircleMTV(Collidable collider) {
		if (!(collider instanceof ComponentCircle)) {
			throw new WorldError(
					"Expected double dispatch to be Circle collider");
		}
		ComponentCircle circleCollider = (ComponentCircle) collider;
		return CollisionsPolygon.mtvPolygonToCircle(circleCollider, this);
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
		return CollisionsPolygon.mtvPolygonToPolygon(this, polygonCollider);
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
		Element componentPolygon = doc.createElement("ComponentPolygon");
		componentPolygon.setAttribute("position",
				XMLEngine.writeVec2d(position));
		return componentPolygon;
	}

}

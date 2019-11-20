package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.WorldError;
import engine.world.serialization.XMLEngine;
import javafx.scene.canvas.GraphicsContext;

public class ComponentCircle extends Component implements Collidable, Drawable {

	private Vec2d position;
	private double radius;

	public ComponentCircle(String tag, GameObject object, Vec2d initialPos,
			double initialRadius) {
		this(object, initialPos, initialRadius);
	}

	public ComponentCircle(GameObject object, Vec2d initialPos,
			double initialRadius) {
		super("Cicle", object);
		this.position = initialPos;
		this.radius = initialRadius;
	}

	public ComponentCircle(GameObject object, Element element) {
		super("Circle", object);
		this.position = XMLEngine.readVec2d(element.getAttribute("position"));
		this.radius = Double.parseDouble(element.getAttribute("radius"));
	}

	@Override
	public void setPosition(Vec2d position) {
		this.position = position;
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public Vec2d getGamePosition() {
		return this.position;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setGamePosition(Vec2d pos) {
		this.position = pos;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public boolean collides(Collidable collider) {
		return collider.collidesCircle(this);
	}

	@Override
	public Vec2d collidesMTV(Collidable collider) {
		return collider.collidesCircleMTV(this);
	}

	@Override
	public boolean collidesAABB(Collidable collider) {
		if (!(collider instanceof ComponentAABB)) {
			throw new WorldError(
					"Expected double dispatch to be AABB collider");
		}
		ComponentAABB aabbCollider = (ComponentAABB) collider;
		return Collisions.AABBtoCircle(aabbCollider, this);
	}

	@Override
	public Vec2d collidesAABBMTV(Collidable collider) {
		if (!(collider instanceof ComponentAABB)) {
			throw new WorldError(
					"Expected double dispatch to be AABB collider");
		}
		ComponentAABB aabbCollider = (ComponentAABB) collider;
		return Collisions.mtvAABBtoCircle(aabbCollider, this);
	}

	@Override
	public boolean collidesCircle(Collidable collider) {
		if (!(collider instanceof ComponentCircle)) {
			throw new WorldError(
					"Expected double dispatch to be Circle collider");
		}
		ComponentCircle circleCollider = (ComponentCircle) collider;
		return Collisions.circleToCircle(this, circleCollider);
	}

	@Override
	public Vec2d collidesCircleMTV(Collidable collider) {
		if (!(collider instanceof ComponentCircle)) {
			throw new WorldError(
					"Expected double dispatch to be Circle collider");
		}
		ComponentCircle circleCollider = (ComponentCircle) collider;
		return Collisions.mtvCircleToCircle(this, circleCollider);
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
		return CollisionsPolygon.mtvPolygonToCircle(this, polygonCollider);
	}

	@Override
	public Vec2d getScreenPosition() {
		Vec2d adjustedPosition = new Vec2d(this.position.x - this.radius,
				this.position.y - this.radius);

		return this.getObject().gameToScreen(adjustedPosition, false);
	}

	@Override
	public Vec2d getScreenSize() {
		return this.getObject().gameToScreen(new Vec2d(radius * 2, radius * 2),
				true);
	}

	@Override
	public void adjustPosition(Vec2d adjustment) {
		this.position = this.position.plus(adjustment);
	}

	@Override
	public boolean insideBB(Vec2d screenPos) {
		boolean o = Collisions.pointToCircle(
				this.getObject().screenToGame(screenPos, false), this);
		return o;
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Vec2d getPosition() {
		return this.position;
	}

	@Override
	public Vec2d getSize() {
		return new Vec2d(this.radius);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentCircle = doc.createElement("ComponentCircle");
		componentCircle.setAttribute("position",
				XMLEngine.writeVec2d(this.position));
		componentCircle.setAttribute("radius",
				new Double(this.radius).toString());
		return componentCircle;
	}

}

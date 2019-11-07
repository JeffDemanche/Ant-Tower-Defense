package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;

/**
 * Required for pathfinding behavior. Gives a game object a speed and the
 * ability to navigate to a single destination.
 */
public class ComponentNavigable extends Component {

	/** Units per second. */
	private double speed;

	/** Whether this game object is in the process of walking a path. */
	private boolean isMoving;
	
	private Drawable drawable;

	private Vec2d nextDestination;

	public ComponentNavigable(GameObject object, double initialSpeed, Drawable drawable) {
		super("Navigable", object);
		this.speed = initialSpeed;
		this.isMoving = false;
		this.drawable = drawable;
		this.nextDestination = null;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return this.speed;
	}

	public boolean isMoving() {
		return this.isMoving;
	}

	public void setNextDestination(Vec2d nextDestination) {
		this.nextDestination = nextDestination;
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		if (drawable.getPosition().equals(nextDestination)) {
			nextDestination = null;
			isMoving = false;
		}
		else if (!isMoving && nextDestination != null) {
			isMoving = true;
		}
		
		if (isMoving && nextDestination != null) {
			double unitsTraveled = this.speed * (nanosSincePreviousTick / 1000000000D);
			Vec2d toDest = new Vec2d(nextDestination.minus(drawable.getPosition()));
			Vec2d travel = toDest.normalize().smult(unitsTraveled);
			
			if (toDest.mag() < travel.mag()) {
				drawable.adjustPosition(toDest);
				isMoving = false;
			} else {
				drawable.adjustPosition(travel);
			}
		}
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentNavigable = doc.createElement("ComponentNavigable");
		return componentNavigable;
	}

}

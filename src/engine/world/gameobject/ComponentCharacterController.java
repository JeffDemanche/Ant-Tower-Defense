package engine.world.gameobject;

import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.EventHandler.KeyListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Enables movement of a Drawable with WASD controls.
 * 
 * @author jdemanch
 */
public class ComponentCharacterController extends Component {

	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NONE
	}

	private boolean allowVertical;

	private Direction currentDirection;
	private Direction restingDirection;
	private HashSet<KeyCode> currentPressed;

	private Drawable drawable;

	private double speed;

	private KeyCode up;
	private KeyCode right;
	private KeyCode down;
	private KeyCode left;

	public Direction getDirection() {
		return this.currentDirection;
	}

	public ComponentCharacterController(String tag, GameObject object,
			Drawable drawable, double speed, KeyCode up, KeyCode right,
			KeyCode down, KeyCode left) {
		this(tag, object, drawable, true, speed, up, right, down, left);
	}

	/**
	 * @param speed
	 *            Game space units per second.
	 */
	public ComponentCharacterController(String tag, GameObject object,
			Drawable drawable, boolean allowVertical, double speed, KeyCode up,
			KeyCode right, KeyCode down, KeyCode left) {
		super(tag, object);

		this.allowVertical = allowVertical;

		this.currentDirection = Direction.NONE;
		this.restingDirection = Direction.EAST;
		this.currentPressed = new HashSet<>();
		this.drawable = drawable;
		this.speed = speed;

		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;

		object.getEventHandler().registerKeyListener(new KeyListener() {
			@Override
			public void onKeyTyped(KeyEvent e) {
			}

			@Override
			public void onKeyReleased(KeyEvent e) {
				if (e.getCode() == up || e.getCode() == right
						|| e.getCode() == down || e.getCode() == left) {
					currentPressed.remove(e.getCode());
					updateDirection();
				}
			}

			@Override
			public void onKeyPressed(KeyEvent e) {
				if (e.getCode() == up || e.getCode() == right
						|| e.getCode() == down || e.getCode() == left) {
					currentPressed.add(e.getCode());
					updateDirection();
				}
			}
		});
	}

	public Direction getRestingDirection() {
		return this.restingDirection;
	}

	private void updateDirection() {
		Direction vertical = Direction.NONE;
		if (allowVertical)
			if (currentPressed.contains(this.up)) {
				if (!currentPressed.contains(this.down)) {
					vertical = Direction.NORTH;
				}
			} else if (currentPressed.contains(this.down)) {
				vertical = Direction.SOUTH;
			}

		Direction horizontal = Direction.NONE;
		if (currentPressed.contains(this.right)) {
			if (!currentPressed.contains(this.left)) {
				horizontal = Direction.EAST;
			}
		} else if (currentPressed.contains(this.left)) {
			horizontal = Direction.WEST;
		}

		if (vertical == Direction.NONE && horizontal == Direction.NONE)
			this.currentDirection = Direction.NONE;
		else if (vertical == Direction.NORTH && horizontal == Direction.NONE)
			this.currentDirection = Direction.NORTH;
		else if (vertical == Direction.SOUTH && horizontal == Direction.NONE)
			this.currentDirection = Direction.SOUTH;

		else if (vertical == Direction.NONE && horizontal == Direction.EAST)
			this.currentDirection = Direction.EAST;
		else if (vertical == Direction.NORTH && horizontal == Direction.EAST)
			this.currentDirection = Direction.NORTHEAST;
		else if (vertical == Direction.SOUTH && horizontal == Direction.EAST)
			this.currentDirection = Direction.SOUTHEAST;

		else if (vertical == Direction.NONE && horizontal == Direction.WEST)
			this.currentDirection = Direction.WEST;
		else if (vertical == Direction.NORTH && horizontal == Direction.WEST)
			this.currentDirection = Direction.NORTHWEST;
		else if (vertical == Direction.SOUTH && horizontal == Direction.WEST)
			this.currentDirection = Direction.SOUTHWEST;

		if (currentDirection != Direction.NONE) {
			this.restingDirection = currentDirection;
		}
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		double secondsSincePreviousTick = nanosSincePreviousTick / 1000000000D;

		double straightSpeed = speed * secondsSincePreviousTick;
		double diagonalComponentSpeed = straightSpeed / Math.sqrt(2);

		switch (currentDirection) {
		case NORTH:
			drawable.adjustPosition(new Vec2d(0, -straightSpeed));
			break;
		case NORTHEAST:
			drawable.adjustPosition(
					new Vec2d(diagonalComponentSpeed, -diagonalComponentSpeed));
			break;
		case EAST:
			drawable.adjustPosition(new Vec2d(straightSpeed, 0));
			break;
		case SOUTHEAST:
			drawable.adjustPosition(
					new Vec2d(diagonalComponentSpeed, diagonalComponentSpeed));
			break;
		case SOUTH:
			drawable.adjustPosition(new Vec2d(0, straightSpeed));
			break;
		case SOUTHWEST:
			drawable.adjustPosition(
					new Vec2d(-diagonalComponentSpeed, diagonalComponentSpeed));
			break;
		case WEST:
			drawable.adjustPosition(new Vec2d(-straightSpeed, 0));
			break;
		case NORTHWEST:
			drawable.adjustPosition(new Vec2d(-diagonalComponentSpeed,
					-diagonalComponentSpeed));
			break;
		default:
			break;
		}
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentCharacterController = doc
				.createElement("ComponentCharacterController");
		return componentCharacterController;
	}

}

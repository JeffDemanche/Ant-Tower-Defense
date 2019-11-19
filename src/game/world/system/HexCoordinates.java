package game.world.system;

import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import application.Vec2i;
import engine.world.PathfindableCoordinates;
import engine.world.gameobject.ComponentPolygon;
import engine.world.gameobject.GameObject;
import engine.world.serialization.XMLEngine;
import engine.world.serialization.XMLSerializable;

/**
 * This is a class for managing tile coordinates (in hex form).
 * 
 * There's two coordinate systems we can use for hex tiles (read here
 * https://math.stackexchange.com/questions/2254655/hexagon-grid-coordinate-system).
 */
public class HexCoordinates
		implements XMLSerializable, PathfindableCoordinates {

	// Side length of equilateral triangle in hex space = 1.
	private static final double HEX_MAX_X = ((Math.sqrt(3) / 2) + 1) / 2;
	private static final double HEX_MIN_X = ((-(Math.sqrt(3) / 2)) + 1) / 2;

	private static final double HEX_WIDTH = Math.sqrt(3) / 2;

	private Vec2i offsetCoordinates;

	/**
	 * Construct a hex coordinate from "offset" coordinates. These work similar
	 * to using square tiles, but odd rows are offset because of hexagons.
	 */
	public HexCoordinates(Vec2i offsetCoordinates) {
		this.offsetCoordinates = offsetCoordinates;
	}

	public HexCoordinates(Element element) {
		this.offsetCoordinates = XMLEngine
				.readVec2i(element.getAttribute("offsetCoordinates"));
	}

	public Vec2i getOffsetCoordinates() {
		return offsetCoordinates;
	}

	public int offsetX() {
		return this.offsetCoordinates.x;
	}

	public int offsetY() {
		return this.offsetCoordinates.y;
	}

	public HashSet<HexCoordinates> getNeighbors() {
		HashSet<HexCoordinates> neighbors = new HashSet<>();
		neighbors.add(new HexCoordinates(new Vec2i(offsetX() + 1, offsetY())));
		neighbors.add(new HexCoordinates(new Vec2i(offsetX() - 1, offsetY())));
		neighbors.add(new HexCoordinates(new Vec2i(offsetX(), offsetY() - 1)));
		neighbors.add(new HexCoordinates(new Vec2i(offsetX(), offsetY() + 1)));
		if ((offsetY() & 1) != 0) {
			// If odd y
			neighbors.add(new HexCoordinates(
					new Vec2i(offsetX() - 1, offsetY() - 1)));
			neighbors.add(new HexCoordinates(
					new Vec2i(offsetX() - 1, offsetY() + 1)));
		} else {
			// If even y
			neighbors.add(new HexCoordinates(
					new Vec2i(offsetX() + 1, offsetY() - 1)));
			neighbors.add(new HexCoordinates(
					new Vec2i(offsetX() + 1, offsetY() + 1)));
		}
		return neighbors;
	}

	/**
	 * Converts this hex coordinate to a game space coordinate.
	 */
	public Vec2d toGameSpace() {
		double rowOffsetMult = (this.offsetCoordinates.y & 1) == 0 ? 1 : 0.5;

		// X in offset space, accounting for odd rows 0.5 offset.
		double xWithOffset = offsetCoordinates.x + rowOffsetMult;

		double x = xWithOffset - (xWithOffset * HEX_MIN_X * 2);
		double y = offsetCoordinates.y - (offsetCoordinates.y * 0.25);

		return new Vec2d(x, y);
	}

	@Override
	public Vec2d getPathfindingGameSpace() {
		return this.toGameSpace().plus(new Vec2d(HEX_WIDTH / 2, 0.5));
	}

	/**
	 * Given a point in game-space, gets the hex-coordinate.
	 * 
	 * @param point
	 *            The point in game space.
	 * @return A HexCoordinate (based on offset coordinates) of the hex
	 *         containing that point.
	 */
	public static HexCoordinates fromGameSpace(Vec2d point) {
		double xNoOffset = (point.x / (1D - HEX_MIN_X * 2));

		boolean withinZigZag;
		if (point.y > 0) {
			withinZigZag = ((int) (point.y / 0.25)) % 3 == 0;
		} else {
			withinZigZag = ((int) (-(point.y - 0.25) / 0.25)) % 3 == 0;
		}

		int x, y;

		// The case where the point is within the y-bands of the zig-zag.
		if (withinZigZag) {
			double yDec = (point.y * (4.0 / 3)) - 0.125;

			int division = ((int) (point.y / 0.25)) / 3;

			if (point.y < 0)
				division--;

			double phase = Math.abs(((xNoOffset - (int) xNoOffset) - 0.5) / 2)
					- 0.125;

			boolean flipPhase = (division & 1) != 0;

			double yFromPhaseTop = point.y - (0.75 * division);
			double absPhase = Math.abs((phase + 0.125) - 0.5);
			double absPhaseInvert = 0.25 - absPhase;

			boolean abovePhase = flipPhase ? yFromPhaseTop < absPhaseInvert
					: absPhase > yFromPhaseTop;

			if (!abovePhase) {
				yDec += phase;
			} else {
				yDec -= phase;
			}
			y = (int) Math.floor(yDec);

			if ((flipPhase && abovePhase) || (!flipPhase && !abovePhase)) {
				// offset x.
				x = (int) (Math.floor(xNoOffset) - 1);
			} else {
				// don't offset
				x = (int) (Math.round(xNoOffset) - 1);
			}
		} else {
			int division = ((int) (point.y / 0.25)) / 3;
			y = point.y > 0 ? division : division - 1;

			double offset = (division & 1) == 0 ? 0 : 0.5;

			if (y < 0) {
				// Flip if -y is odd idk why.
				int flip = -y % 2 == 0 ? 2 : 1;
				x = ((int) Math.round(xNoOffset + offset)) - flip;
			} else {
				x = ((int) Math.ceil(xNoOffset + offset)) - 2;
			}
		}
		return new HexCoordinates(new Vec2i(x, y));
	}

	/**
	 * Creates a drawable component for a Tile gameobject to use which is the
	 * polygon hex shape.
	 * 
	 * @param object
	 *            The user Tile object.
	 */
	public ComponentPolygon createPolygon(GameObject object) {
		Vec2d[] points = new Vec2d[] { new Vec2d(0.5, 0),
				new Vec2d(HEX_MAX_X, 1.0 / 4.0),
				new Vec2d(HEX_MAX_X, 3.0 / 4.0), new Vec2d(0.5, 1),
				new Vec2d(HEX_MIN_X, 3.0 / 4.0),
				new Vec2d(HEX_MIN_X, 1.0 / 4.0) };
		ComponentPolygon p = new ComponentPolygon(object, toGameSpace(),
				points);
		return p;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offsetCoordinates == null) ? 0
				: offsetCoordinates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HexCoordinates other = (HexCoordinates) obj;
		if (offsetCoordinates == null) {
			if (other.offsetCoordinates != null)
				return false;
		} else if (!offsetCoordinates.equals(other.offsetCoordinates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return offsetCoordinates.toString();
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element HexCoordinates = doc.createElement("HexCoordinates");
		HexCoordinates.setAttribute("offsetCoordinates",
				XMLEngine.writeVec2i(offsetCoordinates));
		return HexCoordinates;
	}

	@Override
	public double dist(PathfindableCoordinates otherCoord) {
		return otherCoord.getPathfindingGameSpace().dist(this.toGameSpace());
	}

	@Override
	public int getX() {
		return this.offsetX();
	}

	@Override
	public int getY() {
		return this.offsetY();
	}

}

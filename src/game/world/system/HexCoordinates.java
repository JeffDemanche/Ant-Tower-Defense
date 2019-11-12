package game.world.system;

import application.Vec2d;
import application.Vec2i;
import engine.world.gameobject.ComponentPolygon;
import engine.world.gameobject.GameObject;

/**
 * This is a class for managing tile coordinates (in hex form).
 * 
 * There's two coordinate systems we can use for hex tiles (read here
 * https://math.stackexchange.com/questions/2254655/hexagon-grid-coordinate-system).
 */
public class HexCoordinates {

	// Side length of equilateral triangle in hex space = 1.
	private static final double HEX_MAX_X = ((Math.sqrt(3) / 2) + 1) / 2;
	private static final double HEX_MIN_X = ((-(Math.sqrt(3) / 2)) + 1) / 2;

	private Vec2i offsetCoordinates;

	/**
	 * Construct a hex coordinate from "offset" coordinates. These work similar
	 * to using square tiles, but odd rows are offset because of hexagons.
	 */
	public HexCoordinates(Vec2i offsetCoordinates) {
		this.offsetCoordinates = offsetCoordinates;
	}

	public Vec2i getOffsetCoordinates() {
		return offsetCoordinates;
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

}

package engine.world;

import application.Vec2d;

public interface PathfindableCoordinates {

	/**
	 * Distance between two coordinates, used in pathfinding algorithm. Most
	 * simple implementation is just Euclidean game-space distance.
	 * 
	 * @param otherCoord
	 *            The second point to find distance to.
	 * @return The distance between this and that point.
	 */
	public double dist(PathfindableCoordinates otherCoord);

	public int getX();

	public int getY();

	/**
	 * Gets the game-space point of this coordinate.F
	 */
	public Vec2d getPathfindingGameSpace();

}

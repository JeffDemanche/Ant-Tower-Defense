package engine.world;

import java.util.Set;

import engine.world.gameobject.GameObject;

/**
 * To be implemented by game systems that have tile-based coordinates. Used for
 * pathfinding.
 */
public interface TileCoordinates<T extends PathfindableCoordinates> {

	public GameObject getTileAt(int x, int y);
	
	public Set<T> getAllTiles();

	public Set<T> getTraversableNeighbors(int x, int y);
	
}

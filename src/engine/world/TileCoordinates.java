package engine.world;

import java.util.Set;

import application.Vec2d;
import application.Vec2i;
import engine.world.gameobject.GameObject;

/**
 * To be implemented by game systems that have tile-based coordinates. Used for
 * pathfinding.
 */
public interface TileCoordinates {

	public GameObject getTileAt(int x, int y);
	
	public Set<Vec2i> getAllTiles();

	public Set<Vec2d> getTraversableNeighbors(int x, int y);
	
}

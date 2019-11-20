package engine.world.gameobject;

import application.Vec2d;

/**
 * To be implemented by components that can be drawn to screen space.
 * 
 * @author jdemanch
 */
public interface Drawable {

	public Vec2d getScreenPosition();

	public Vec2d getScreenSize();
	
	public Vec2d getPosition();
	
	public Vec2d getSize();
	
	public void setPosition(Vec2d position);

	public void adjustPosition(Vec2d adjustment);
	
	/**
	 * Returns whether a screen coord is within this bounding box.
	 * 
	 * @param screenPos
	 *            The screen space position.
	 * @return Whether the position is within this position bounding box.
	 */
	public boolean insideBB(Vec2d screenPos);
	
}

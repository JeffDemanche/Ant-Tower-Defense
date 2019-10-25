package engine.world.gameobject;

import application.Vec2d;

/**
 * To be implemented by components which give a GameObject a position and
 * collidable boundary.
 * 
 * @author jdemanch
 */
public interface Collidable {

	public enum CollisionType {
		AABB, CIRCLE, POINT
	}

	public Vec2d getGamePosition();

	public boolean collides(Collidable collider);

	public Vec2d collidesMTV(Collidable collider);

	public boolean collidesAABB(Collidable collider);

	public Vec2d collidesAABBMTV(Collidable collider);

	public boolean collidesCircle(Collidable collider);

	public Vec2d collidesCircleMTV(Collidable collider);
	
	public boolean collidesPolygon(Collidable collider);
	
	public Vec2d collidesPolygonMTV(Collidable collider);

}

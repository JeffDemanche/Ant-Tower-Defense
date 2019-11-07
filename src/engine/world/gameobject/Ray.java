package engine.world.gameobject;

import application.Vec2d;

public class Ray {

	public final Vec2d src;
	public final Vec2d dir;

	public Ray(Vec2d src, Vec2d dir) {
		this.src = src;
		this.dir = dir;
	}

}

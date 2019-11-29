package game.world.gameobject.projectile;

import application.Vec2d;

public class ProjectileInfo {
	
	public final String type;
	public final String parentTower;
	public final Vec2d direction;
	public final double speed;
	public final Vec2d target;
	
	public ProjectileInfo(String parentTower,String type, Vec2d direction, double speed, Vec2d target) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.parentTower = parentTower;
		this.direction = direction;
		this.speed = speed;
		this.target = target;
	}
	
	
	
}

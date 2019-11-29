package game.world.gameobject.projectile;

import application.Vec2d;

public class ProjectileInfo {
	
	public final String type;
	public final String parentTower;
	public final Vec2d direction;
	public final double speed;
	
	public ProjectileInfo(String parentTower,String type, Vec2d direction, double speed) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.parentTower = parentTower;
		this.direction = direction;
		this.speed = speed;
	}
	
	
	
}

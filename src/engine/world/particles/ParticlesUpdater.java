package engine.world.particles;

import java.util.List;

import application.Vec2d;
import engine.world.GameSystem;

public abstract class ParticlesUpdater  {
	
	Vec2d initialPosition;
	Vec2d initialDirection;
	Vec2d particleSize;
	int particleTimeToLive;
	double speed;
	String texture;
	int initNumParticles;
	GameSystem system;
	String parentName;
	boolean loop;
	long currentTimer;
	
	public ParticlesUpdater(GameSystem system, String parentName,Vec2d initialPosition, Vec2d initialDirection, Vec2d particleSize, int particleTimeToLive,
			double speed, String texture, int initParticles, boolean loop)
	{
		this.initialPosition = initialPosition;
		this.initialDirection = initialDirection;
		this.particleSize = particleSize;
		this.particleTimeToLive = particleTimeToLive;
		this.speed = speed;
		this.texture = texture;
		this.initNumParticles = initParticles;
		this.system = system;
		this.parentName = parentName;
		this.loop = loop;
	}
	
	public List<Particle> initialize() {
		return null;
		
	};
	
	public void update(long nanosSincePreviousTick ) {};
	
}

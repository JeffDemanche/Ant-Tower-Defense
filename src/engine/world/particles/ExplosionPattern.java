package engine.world.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.Vec2d;
import engine.world.GameSystem;
import game.world.gameobject.SpriteRegistry;

public class ExplosionPattern extends ParticlesUpdater{

	
	boolean initParticles = false;
	
	public ExplosionPattern(GameSystem system, String parentName,Vec2d initialPosition, Vec2d initialDirection, Vec2d particleSize, int particleTimeToLive,
			double speed, String texture, int initParticles, boolean loop) {
		super( system,  parentName, initialPosition,  initialDirection,  particleSize,  particleTimeToLive,
				 speed,  texture,  initParticles,  loop);		
	}

	
	@Override
	public List<Particle> initialize() {
		List<Particle> particles = new ArrayList<Particle>();
		// TODO Auto-generated method stub
		Random rand = new Random(); 
		double rand_int1 = rand.nextDouble()+0.005;
		
		double anglesDt = (360.0/initNumParticles) * (Math.PI/180);
		double currentAngle = 0;
		
		for(int i = 0 ; i <this.initNumParticles; i++)
		{
			double x = Math.cos(currentAngle) * this.initialDirection.x - Math.sin(currentAngle)  * this.initialDirection.y;
			double y = Math.sin(currentAngle) * this.initialDirection.x + Math.cos(currentAngle)  * this.initialDirection.y;
			Vec2d rotatedDirection = new Vec2d(x,y);
			Particle p =  new Particle(system, parentName+"_particle"+rand_int1,
					SpriteRegistry.FIRE_PARTICLE,this.initialPosition,this.particleSize,
					this.particleTimeToLive,rotatedDirection,
					this.speed);
			
			particles.add(p);
			
			currentAngle += anglesDt;
		}
		
		return particles;
	}

	@Override
	public void update(long nanosSincePreviousTick ) {
		// TODO Auto-generated method stub
		if(this.loop)
		{
			currentTimer+=nanosSincePreviousTick;
			float toSeconds = (float)currentTimer/1000000000.0f;
			if(toSeconds > 0.2)
			{
				initialize();
				currentTimer = 0;
			}
		}
		else
		{
			if(!initParticles)
			{
				currentTimer+=nanosSincePreviousTick;
				float toSeconds = (float)currentTimer/1000000000.0f;
				if(toSeconds > 0)
				{
					//initializeParticles();
					//this.initializeSingleParticle();
					
					currentTimer = 0;
					initParticles = true;	
				}
				
			}
		}
	}

}

package engine.world.gameobject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.particles.Particle;
import javafx.scene.canvas.GraphicsContext;

public class ComponentEmitter extends Component {

	
	public enum PATTERTYPE{
	 EXPLOSION,
	 FIRE,
	 RAIN
	};
	
	
	private static final  String starTexture = "img/particle/star.png";
	private static final  String fireTexture = "img/particle/fire.png";
	private static final  String rainTexture = "img/particle/rainDrop.png";
	
	List<Particle> myParticles;
	Queue<Integer> myParticlesToRemove;
	
	private float myDuration;
	private long currentTimer;
	
	private boolean myLooping;
	private int myMaxParticles;
	private int myInitParticles;
	private String parentName;
	
	private String myParticleTexture;
	private float myParticlesMass;
	private float myParticlesRestitution;
	private int myParticlesTimeToLive;
	private Vec2d myParticlesIntialDirection;
	private Vec2d myParticlesInitialPosition;	
	private double myDelayToCreateParticles;
	private double myParticlesSpeed;
	private Vec2d myParticlesSize;
	private boolean initParticles = false;
	private boolean firstUpdate = true;
	private PATTERTYPE myPatternType;
	
	public ComponentEmitter(PATTERTYPE pattern, String tag, GameObject object,
			int initParticles,int particlesTimeToLive,
			Vec2d initialPosition,Vec2d initialSize,
			Vec2d initialDirection,
			double speed, boolean isLooping, float duration, float delayToCreateParticles) {
		super("Emitter", object);
		myParticles = new ArrayList<Particle>();
		myParticlesToRemove = new LinkedList<Integer>();
		
		
		switch(pattern)
		{
		 case EXPLOSION:
			 this.myParticleTexture = starTexture;
			 break;
		 case FIRE:
		   this.myParticleTexture = fireTexture;
		   break;
		 case RAIN:
			   this.myParticleTexture = rainTexture;
			   break;
		}
		
		
		
		this.myParticlesTimeToLive = particlesTimeToLive;
		this.myParticlesInitialPosition = initialPosition;
		this.myParticlesIntialDirection = initialDirection;
		this.myParticlesSpeed = speed;
		this.myInitParticles = initParticles;
		this.myParticlesSize = initialSize;
		//this.myLooping = isLooping;
		
		this.myDelayToCreateParticles = delayToCreateParticles;
		this.myDuration = duration;
		this.myPatternType = pattern;
		
		
		this.myLooping = true;
		
		switch(pattern)
		{
		 case EXPLOSION:
		 {
		      	 
			 this.initializeExplosionParticles();
			 break;
		 }
		 case FIRE:
		 {
			 this.initializeFireParticle();
			 break;
		 }
		 case RAIN:
		 {
			 
		    this.initializeRainParticle();
		    break;
		 }
		}
		/*if()
		{
			//this.initializeParticles();	
			//this.initializeSingleParticle();
		
		}*/
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		if(firstUpdate)
		{
			firstUpdate = false;
			return;
		}
		
		
		switch(this.myPatternType)
		{
		 case EXPLOSION:
			 updateExplosion(nanosSincePreviousTick);
			 break;
		 case FIRE:
			 updateFire(nanosSincePreviousTick);
			 break;
		 case RAIN:
			 updateRain(nanosSincePreviousTick);
			 break;
		}
		
		/*if(myLooping)
		{
			currentTimer+=nanosSincePreviousTick;
			float toSeconds = (float)currentTimer/1000000000.0f;
			if(toSeconds > 0.2)
			{
				//initializeParticles();
				//this.initializeSingleParticle();
				this.initializeRainParticle();
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
			
		}*/
		
		while(!myParticlesToRemove.isEmpty())
		{
			Integer index = myParticlesToRemove.remove();
			
			Particle p = this.myParticles.get(index);
			p.remove();
			
			if(myParticles.remove(index))
			{
				System.out.println(index + " deleted");
			}
		}
		
		for(int i = 0 ; i < this.myParticles.size(); i++)
		{
			if(this.myParticles.get(i).getTimeToLive() <=0)
			{
				myParticlesToRemove.add(i);
				 
			}
		}
	}

	private void updateRain(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		if(myLooping)
		{
			currentTimer+=nanosSincePreviousTick;
			float toSeconds = (float)currentTimer/1000000000.0f;
			if(toSeconds > 0.2)
			{

				this.initializeRainParticle();
				currentTimer = 0;
			}
		}

	}

	private void updateFire(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		if(myLooping)
		{
			currentTimer+=nanosSincePreviousTick;
			float toSeconds = (float)currentTimer/1000000000.0f;
			if(toSeconds > 0.2)
			{
				this.initializeFireParticle();
				currentTimer = 0;
			}
		}
		
	}

	private void updateExplosion(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		if(myLooping)
		{
			currentTimer+=nanosSincePreviousTick;
			float toSeconds = (float)currentTimer/1000000000.0f;
			if(toSeconds > 3.0)
			{
				initializeExplosionParticles();
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
					initializeExplosionParticles();
					currentTimer = 0;
					initParticles = true;	
				}
				
			}
			
		}
	}

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}

	private void initializeExplosionParticles()
	{
		Random rand = new Random(); 
		double rand_int1 = rand.nextDouble()+0.005;
		
		double anglesDt = (360.0/myInitParticles) * (Math.PI/180);
		double currentAngle = 0;
		
		for(int i = 0 ; i <this.myInitParticles; i++)
		{
			double x = Math.cos(currentAngle) * myParticlesIntialDirection.x - Math.sin(currentAngle)  * myParticlesIntialDirection.y;
			double y = Math.sin(currentAngle) * myParticlesIntialDirection.x + Math.cos(currentAngle)  * myParticlesIntialDirection.y;
			Vec2d rotatedDirection = new Vec2d(x,y);
			Particle p =  new Particle(getObject().getSystem(), parentName+"_particle"+rand_int1,
					this.myParticleTexture,this.myParticlesInitialPosition,this.myParticlesSize,
					this.myParticlesTimeToLive,rotatedDirection,
					this.myParticlesSpeed);
			
			myParticles.add(p);
			
			this.getObject().getSystem().addGameObject(5, p);
			currentAngle += anglesDt;
		}
	}
	
	private void initializeFireParticle()
	{
		Random rand = new Random();
		double rand_int1 = rand.nextDouble() + 0.005;

		// double anglesDt = (360.0/myInitParticles) * (Math.PI/180);
		// double currentAngle = 0;

		double x = this.myParticlesInitialPosition.x + ((Math.random() * ((0.8 + 0.8)) + 1) - 0.8);
		Vec2d positon = new Vec2d(x, this.myParticlesInitialPosition.y);

		// Vec2d rotatedDirection = new Vec2d(x,y);
		Particle p = new Particle(getObject().getSystem(), 
				parentName + "_particle" + rand_int1, this.myParticleTexture,
				positon, this.myParticlesSize, this.myParticlesTimeToLive, 
				new Vec2d(0, -1), this.myParticlesSpeed);

		myParticles.add(p);

		this.getObject().getSystem().addGameObject(5, p);
		
			
	}
	
	private void initializeRainParticle()
	{
		Random rand = new Random();
		double rand_int1 = rand.nextDouble() + 0.005;

		// double anglesDt = (360.0/myInitParticles) * (Math.PI/180);
		// double currentAngle = 0;

		double x = this.myParticlesInitialPosition.x + ((Math.random() * ((0.8 + 0.8)) + 1) - 0.8);
		double y = this.myParticlesInitialPosition.y + 0.5;
		Vec2d positon = new Vec2d(x, y);

		// Vec2d rotatedDirection = new Vec2d(x,y);
		Particle p = new Particle(getObject().getSystem(), 
				parentName + "_particle" + rand_int1, this.myParticleTexture,
				positon, this.myParticlesSize, this.myParticlesTimeToLive, 
				new Vec2d(0, 1), this.myParticlesSpeed);

		myParticles.add(p);

		this.getObject().getSystem().addGameObject(5, p);
		
	}
	

	
}

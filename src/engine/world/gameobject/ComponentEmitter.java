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

	List<Particle> myParticles;
	Queue<Integer> myParticlesToRemove;
	
	private int myDuration;
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
	private double delayToCreateParticle;
	private double myParticlesSpeed;
	
	public ComponentEmitter(String tag, GameObject object,String texturePath, int initParticles,
			int particlesTimeToLive, Vec2d initialPosition,Vec2d initialDirection,
			double speed) {
		super("Emitter", object);
		myParticles = new ArrayList<Particle>();
		myParticlesToRemove = new LinkedList<Integer>();
		
		this.myParticleTexture = texturePath;
		this.myParticlesTimeToLive = particlesTimeToLive;
		this.myParticlesInitialPosition = initialPosition;
		this.myParticlesIntialDirection = initialDirection;
		this.myParticlesSpeed = speed;
		this.myInitParticles = initParticles;
		
		Random rand = new Random(); 
		double rand_int1 = rand.nextDouble()+0.005;
		
		
		for(int i = 0 ; i <this.myInitParticles; i++)
		{
			Particle p =  new Particle(getObject().getSystem(), parentName+"_particle"+rand_int1,
					this.myParticleTexture,this.myParticlesInitialPosition,
					this.myParticlesTimeToLive,this.myParticlesIntialDirection,
					this.myParticlesSpeed);
			
			myParticles.add(p);
			
			this.getObject().getSystem().addGameObject(1, p);
			
		}
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

	@Override
	public void onGameObjectRemoved() {
		// TODO Auto-generated method stub
		
	}

	
	
}

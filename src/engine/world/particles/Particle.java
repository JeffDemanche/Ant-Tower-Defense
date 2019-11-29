package engine.world.particles;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentPhysics;
import engine.world.gameobject.GameObject;
import javafx.scene.image.Image;
import engine.world.gameobject.ComponentAABB;

public class Particle extends GameObject{

	private Image myTexture;
	private int timeToLive;
	private long currentTimeLife = 0;
	private Vec2d direction;
	private double speed;
	private Vec2d myPosition;
	private boolean initialized = false;
	private boolean firstUpdate = true;
	private Vec2d mySize;
	private boolean myDirectionParticle = true;
	
	public Particle(GameSystem system, String name, Image texture, 
			Vec2d initialPosition, Vec2d initialSize, int timeTolive,
			Vec2d direction, double speed) {
		super(system, name);
		// TODO Auto-generated constructor stub
		this.direction = direction;
	    this.timeToLive = timeTolive;
	    this.myPosition = initialPosition;
	    this.myTexture = texture;
	    this.mySize = initialSize;
	    this.speed =speed;
	    
	    
	    ComponentAABB aabComponent = new ComponentAABB(this,initialPosition,
			this.mySize);
	    
	    ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
	    		this.myTexture, aabComponent);
	    
	    this.addComponent(aabComponent);
		this.addComponent(sprite);
	}

	
	public void onTick(long nanosSincePreviousTick) {
		
		if(firstUpdate)
		{
			firstUpdate = false;
			return;
		}
		currentTimeLife+=nanosSincePreviousTick;
		
		
		if(currentTimeLife > 1000000000)
		{
			timeToLive--;
			currentTimeLife = 0;
		}
		
		if(timeToLive > 0)
		{
			if(!this.initialized)
			{
				((ComponentAABB)this.getComponent("AABB")).setPosition(this.myPosition);
				this.initialized = true;
			}
			
			
			
			((ComponentAABB) this.getComponent("AABB")).adjustPosition(this.direction.smult(this.speed));	
			
			
	    	
		}
		//tick components
		super.onTick(nanosSincePreviousTick);
	}
	
	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}


	public Vec2d getDirection() {
		return direction;
	}


	public void setDirection(Vec2d direction) {
		this.direction = direction;
	}


	public Vec2d getPosition() {
		return myPosition;
	}


	public void setPosition(Vec2d position) {
		this.myPosition = position;
	}

	

}

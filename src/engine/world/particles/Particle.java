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

	private String myTexturePath;
	private int timeToLive;
	private long currentTimeLife = 0;
	private Vec2d direction;
	private double speed;
	private Vec2d initialPosition;
	private boolean initialized = false;
	private boolean firstUpdate = true;
	
	public Particle(GameSystem system, String name, String texturePath, 
			Vec2d initialPosition, int timeTolive,
			Vec2d direction, double speed) {
		super(system, name);
		// TODO Auto-generated constructor stub
		this.direction = direction;
	    this.timeToLive = timeTolive;
	    this.initialPosition = initialPosition;
	    this.myTexturePath = texturePath;
	    this.speed =speed;
	    
	    ComponentAABB aabComponent = new ComponentAABB(this,initialPosition,
			new Vec2d(1,1));
	    Image texture = new Image(this.myTexturePath);
	    ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
	    		texture, aabComponent);
	    
	    this.addComponent(aabComponent);
		this.addComponent(sprite);
	}

	@Override
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
				((ComponentAABB)this.getComponent("AABB")).setPosition(this.initialPosition);
				this.initialized = true;
			}
			Vec2d position = ((ComponentAABB)this.getComponent("AABB")).getPosition();
			position = position.plus(this.direction).smult(this.speed);//.smult(nanosSincePreviousTick);
			((ComponentAABB)this.getComponent("AABB")).setPosition(position);
			
			//this.position.x += this.velocity.x * dt;
			//this.position.y += this.velocity.y * dt;
	    	
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

	

}

package game.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentAABB;
import engine.world.gameobject.ComponentEmitter;
import engine.world.gameobject.GameObject;

public class WaterParticlesEmitter extends GameObject {

	public WaterParticlesEmitter(GameSystem system, String name, Vec2d position) {
		super(system, name);
		// TODO Auto-generated constructor stub
		
		
		ComponentAABB aabComponent = new ComponentAABB(this, position, new Vec2d(1,1));
		ComponentEmitter particleEmitter = 
				new ComponentEmitter(ComponentEmitter.PATTERTYPE.EXPLOSION , "Emitter", this,
		                    10, 1, aabComponent.getPosition(), new Vec2d(0.2,0.2),new Vec2d(0,-1),
		                    SpriteRegistry.WATER_PARTICLE, 0.05,false,3.0f,0);
		
		this.addComponent(particleEmitter);
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick)
	{
		if(((ComponentEmitter)this.getComponent("Emitter")).isFinished())
		{
			this.remove();
		}
		
		super.onTick(nanosSincePreviousTick);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

package game.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentAABB;
import engine.world.gameobject.ComponentEmitter;
import engine.world.gameobject.GameObject;

public class EmitterGameObject2 extends GameObject {

	public EmitterGameObject2(GameSystem system, String name) {
		super(system, name);
		// TODO Auto-generated constructor stub
		
		
		ComponentAABB aabComponent = new ComponentAABB(this, new Vec2d(-5,5), new Vec2d(1,1));
		ComponentEmitter particleEmitter = new ComponentEmitter(ComponentEmitter.PATTERTYPE.FIRE , "Emitter", this,
		10, 2, aabComponent.getPosition(), new Vec2d(0.5,0.5),new Vec2d(0,-1), 0.05,true,3.0f,0);
		
		this.addComponent(particleEmitter);
	}
	
	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

}

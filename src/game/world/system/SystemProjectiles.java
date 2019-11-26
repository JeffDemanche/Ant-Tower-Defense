package game.world.system;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.GameSystem;
import engine.world.World;
import game.world.ATDWorld;
import javafx.scene.canvas.GraphicsContext;

public class SystemProjectiles extends GameSystem{

	private ATDWorld atdWorld;
	private SystemLevel level;
	
	public SystemProjectiles(World world, SystemLevel level ) {
		super(world);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWorldLoaded() {
		// TODO Auto-generated method stub
		
	}

}

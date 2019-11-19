package game.world.system;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.GameSystem;
import game.world.ATDWorld;
import javafx.scene.canvas.GraphicsContext;

public class SystemGUI extends GameSystem {

	private ATDWorld atdWorld;
	
	public SystemGUI(ATDWorld world) {
		super(world);
		this.atdWorld = world;
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		this.drawGameObjects(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		this.tickGameObjects(nanosSincePreviousTick);
	}

	@Override
	public void onStartup() {
	}

	@Override
	public void onShutdown() {
	}

	@Override
	public void onWorldLoaded() {
	}

}

package game.world.system;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.GameSystem;
import game.world.ATDWorld;
import javafx.scene.canvas.GraphicsContext;

public class SystemTowers extends GameSystem {

	private final int TOWERS_Z = 4;

	private ATDWorld atdWorld;
	private SystemLevel level;

	private int towerCounter;

	public SystemTowers(ATDWorld world, SystemLevel level) {
		super(world);
		this.atdWorld = world;
		this.level = level;
		this.towerCounter = 0;
	}

	public SystemLevel getLevel() {
		return this.level;
	}
	
	public int nextTowerId() {
		int id = towerCounter;
		towerCounter++;
		return id;
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

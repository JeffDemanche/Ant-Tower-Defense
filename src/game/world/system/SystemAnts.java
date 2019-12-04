package game.world.system;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import game.world.ATDWorld;
import game.world.gameobject.ant.Ant;
import game.world.gameobject.ant.AntCarpenter;
import game.world.gameobject.ant.Wave;
import javafx.scene.canvas.GraphicsContext;

public class SystemAnts extends GameSystem {

	private final int ANTS_Z = 5;

	private int currentWave;
	private ArrayList<Wave> waves;

	private ATDWorld atdWorld;
	private SystemLevel level;

	private int antCounter;

	public SystemAnts(ATDWorld world, SystemLevel level) {
		super(world);
		this.atdWorld = world;
		this.level = level;
		currentWave = -1;

		this.antCounter = 0;
	}

	public ArrayList<Wave> generateWaves() {
		ArrayList<Wave> w = new ArrayList<>();
		w.add(new Wave(this, 3000, createCarpenterAnts(10)));
		return w;
	}

	public AntCarpenter[] createCarpenterAnts(int number) {
		AntCarpenter[] ants = new AntCarpenter[number];
		for (int i = 0; i < number; i++) {
			AntCarpenter newCarpenter = new AntCarpenter(this, antCounter);
			this.antCounter++;
			ants[i] = newCarpenter;
		}
		return ants;
	}

	public void spawnAnt(Ant ant) {
		ant.getBound().setPosition(
				level.getAntHill().toGameSpace().plus(new Vec2d(0.5)));
		this.addGameObject(ANTS_Z, ant);
		ant.onSpawn();
	}

	public void startWave() {
		if (currentWave == -1 || !waves.get(currentWave).isRunning()) {
			if (currentWave < waves.size()) {
				currentWave++;
				waves.get(currentWave).start();
			} else {
				System.out.println("GAME WON");
			}
		}
	}

	public boolean isWaveActive() {
		return !(currentWave == -1 || !waves.get(currentWave).isRunning());
	}

	public SystemLevel getLevel() {
		return this.level;
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

		if (currentWave >= 0)
			waves.get(currentWave).onTick(nanosSincePreviousTick);
	}

	@Override
	public void onStartup() {
		this.waves = generateWaves();
	}

	@Override
	public void onShutdown() {
	}

	@Override
	public void onWorldLoaded() {
	}

}

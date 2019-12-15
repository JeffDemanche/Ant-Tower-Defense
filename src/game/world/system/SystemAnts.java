package game.world.system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.gameobject.ant.Ant;
import game.world.gameobject.ant.Wave;
import javafx.scene.canvas.GraphicsContext;

public class SystemAnts extends GameSystem {

	private final int ANTS_Z = 5;

	private int currentWave;
	private ArrayList<Wave> waves;

	private ATDWorld atdWorld;
	private SystemLevel level;
	private WaveFactory waveFactory;

	public SystemAnts(ATDWorld world, SystemLevel level) {
		super(world);
		this.atdWorld = world;
		this.level = level;
		this.waveFactory = new WaveFactory(level, this, world);
		currentWave = -1;
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

	public int getCurrentWave() {
		return this.currentWave;
	}

	public int getNumberOfWaves() {
		return this.waves.size();
	}

	/**
	 * Called when an ant dies, with the location where the ant was killed.
	 */
	public void onAntDeath(HexCoordinates location, Ant ant) {
		level.onAntDeath(location);

		atdWorld.addCash(ant.getReward());
	}

	public List<Ant> findClosestTargets(HexCoordinates towerLocation,
			double range, int howMany) {
		Ant closestAnt = null;
		double closestDist = Double.MAX_VALUE;

		ArrayList<Ant> ants = new ArrayList<>();

		for (GameObject object : this.getAllObjects()) {
			if (object instanceof Ant) {
				ants.add((Ant) object);
				double dist = ((Ant) object).getBound().getPosition()
						.dist(towerLocation.toGameSpaceCentered());
				if (dist < closestDist && dist < range) {
					closestAnt = (Ant) object;
					closestDist = dist;
				}
			}
		}

		ants.sort(new Comparator<Ant>() {
			@Override
			public int compare(Ant o1, Ant o2) {
				double dist1 = o1.getBound().getPosition()
						.dist(towerLocation.toGameSpaceCentered());
				double dist2 = o2.getBound().getPosition()
						.dist(towerLocation.toGameSpaceCentered());

				if (dist1 < dist2) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		ArrayList<Ant> list = new ArrayList<>();
		for (int i = 0; i < howMany; i++) {
			if (i <= ants.size() - 1)
				list.add(ants.get(i));
		}
		return list;
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
		this.waves = waveFactory.generateWaves();
	}

	@Override
	public void onShutdown() {
	}

	@Override
	public void onWorldLoaded() {
	}

}

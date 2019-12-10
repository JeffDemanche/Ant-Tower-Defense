package game.world.gameobject.ant;

import java.util.ArrayList;
import java.util.Random;

import game.world.system.SystemAnts;
import game.world.system.SystemLevel;

public class Wave {

	private SystemAnts systemAnts;
	private SystemLevel level;

	private Random random;

	private ArrayList<Ant> waveAnts;

	private boolean running;
	private boolean allAntsSpawned;

	private int numberOfPathsTo;
	private int numberOfPathsFrom;

	private int numClassSpawns;
	private int millisBetweenSpawns;

	private long nanosSinceWaveStart;

	private WavePaths paths;

	public Wave(SystemAnts systemAnts, SystemLevel level, Random random,
			int millisBetweenSpawns, int numberOfPathsTo, int numberOfPathsFrom,
			Ant... waveAnts) {
		this.systemAnts = systemAnts;
		this.level = level;
		this.random = random;
		this.waveAnts = new ArrayList<>();
		for (Ant a : waveAnts) {
			a.setWave(this);
			this.waveAnts.add(a);
		}
		this.running = false;
		this.allAntsSpawned = false;
		this.nanosSinceWaveStart = 0;
		this.millisBetweenSpawns = millisBetweenSpawns;
		this.numberOfPathsTo = numberOfPathsTo;
		this.numberOfPathsFrom = numberOfPathsFrom;
		this.numClassSpawns = 0;
	}

	private void generatePaths() {
		this.paths = new WavePaths(this.level, this.random,
				this.numberOfPathsTo, this.numberOfPathsFrom);
	}

	public WavePaths getPaths() {
		return this.paths;
	}
	
	/**
	 * Called from SystemAnts when the wave is started.
	 */
	public void start() {
		generatePaths();
		this.running = true;
	}

	public boolean isRunning() {
		return running;
	}

	public void onTick(long nanosSinceLastTick) {
		if (running && !allAntsSpawned) {
			nanosSinceWaveStart += nanosSinceLastTick;

			double millisSinceWaveStart = nanosSinceWaveStart * 1E-6;

			if (numClassSpawns == 0 || millisSinceWaveStart
					/ millisBetweenSpawns > numClassSpawns) {
				systemAnts.spawnAnt(waveAnts.get(numClassSpawns));
				numClassSpawns++;

				if (numClassSpawns >= waveAnts.size()) {
					allAntsSpawned = true;
				}
			}
		}
		
		boolean allAntsDead = true;
		for (Ant a : waveAnts) {
			if (a.isAlive()) {
				allAntsDead = false;
				break;
			}
		}
		running = !allAntsSpawned || !allAntsDead;
	}

}

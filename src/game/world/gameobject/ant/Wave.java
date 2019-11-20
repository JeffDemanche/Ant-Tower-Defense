package game.world.gameobject.ant;

import java.util.ArrayList;

import game.world.system.SystemAnts;

public class Wave {

	private SystemAnts systemAnts;
	private ArrayList<Ant> waveAnts;

	private boolean running;
	private boolean allAntsSpawned;

	private int numClassSpawns;
	private int millisBetweenSpawns;
	private long nanosSinceWaveStart;

	public Wave(SystemAnts systemAnts, int millisBetweenSpawns,
			Ant... waveAnts) {
		this.systemAnts = systemAnts;
		this.waveAnts = new ArrayList<>();
		for (Ant a : waveAnts) {
			this.waveAnts.add(a);
		}
		this.running = false;
		this.allAntsSpawned = false;
		this.nanosSinceWaveStart = 0;
		this.millisBetweenSpawns = millisBetweenSpawns;
		this.numClassSpawns = 0;
	}

	public void start() {
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
			
			boolean allAntsDead = true;
			for (Ant a : waveAnts) {
				if (a.isAlive()) {
					allAntsDead = false;
					break;
				}
			}
			running = !allAntsDead;
		}
	}

}

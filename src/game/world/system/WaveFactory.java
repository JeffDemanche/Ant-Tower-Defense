package game.world.system;

import java.util.ArrayList;

import game.world.ATDWorld;
import game.world.gameobject.ant.AntCarpenter;
import game.world.gameobject.ant.Wave;

/**
 * Handles wave generation.
 * 
 * @author jdemanch
 */
public class WaveFactory {

	private int antCounter;

	private SystemLevel level;
	private SystemAnts ants;
	private ATDWorld atdWorld;

	public WaveFactory(SystemLevel level, SystemAnts ants, ATDWorld atdWorld) {
		this.antCounter = 0;

		this.level = level;
		this.ants = ants;
		this.atdWorld = atdWorld;
	}

	public ArrayList<Wave> generateWaves() {
		ArrayList<Wave> w = new ArrayList<>();
		Wave wave1 = new Wave(ants, level, atdWorld.getRandom(), 3000, 2, 2,
				createCarpenterAnts(10));
		Wave wave2 = new Wave(ants, level, atdWorld.getRandom(), 2000, 3, 3,
				createCarpenterAnts(20));
		
		w.add(wave1);
		w.add(wave2);
		return w;
	}

	public AntCarpenter[] createCarpenterAnts(int number) {
		AntCarpenter[] antsArray = new AntCarpenter[number];
		for (int i = 0; i < number; i++) {
			AntCarpenter newCarpenter = new AntCarpenter(ants, antCounter);
			this.antCounter++;
			antsArray[i] = newCarpenter;
		}
		return antsArray;
	}

}

package game.world.system;

import java.util.ArrayList;

import game.world.ATDWorld;
import game.world.gameobject.ant.AntCarpenter;
import game.world.gameobject.ant.AntQueen;
import game.world.gameobject.ant.AntWeaver;
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
				createCarpenterAnts(10, 20, 1));
		Wave wave2 = new Wave(ants, level, atdWorld.getRandom(), 2000, 3, 3,
				createCarpenterAnts(20, 20, 1));
		Wave wave3 = new Wave(ants, level, atdWorld.getRandom(), 2000, 3, 3,
				createWeaverAnts(10, 12, 2));
		Wave wave4 = new Wave(ants, level, atdWorld.getRandom(), 2000, 3, 3,
				createWeaverAnts(20, 12, 2));
		Wave wave5 = new Wave(ants, level, atdWorld.getRandom(), 2000, 3, 3,
				createQueenAnts(20, 35, 4));
		
		w.add(wave1);
		w.add(wave2);
		w.add(wave3);
		w.add(wave4);
		w.add(wave5);
		return w;
	}

	public AntCarpenter[] createCarpenterAnts(int number, int maxHealth, int reward) {
		AntCarpenter[] antsArray = new AntCarpenter[number];
		for (int i = 0; i < number; i++) {
			AntCarpenter newCarpenter = new AntCarpenter(ants, antCounter, maxHealth, reward);
			this.antCounter++;
			antsArray[i] = newCarpenter;
		}
		return antsArray;
	}
	
	public AntWeaver[] createWeaverAnts(int number, int maxHealth, int reward) {
		AntWeaver[] antsArray = new AntWeaver[number];
		for (int i = 0; i < number; i++) {
			AntWeaver newWeaver = new AntWeaver(ants, antCounter, maxHealth, reward);
			this.antCounter++;
			antsArray[i] = newWeaver;
		}
		return antsArray;
	}
	
	public AntQueen[] createQueenAnts(int number, int maxHealth, int reward) {
		AntQueen[] antsArray = new AntQueen[number];
		for (int i = 0; i < number; i++) {
			AntQueen newQueen = new AntQueen(ants, antCounter, maxHealth, reward);
			this.antCounter++;
			antsArray[i] = newQueen;
		}
		return antsArray;
	}

}

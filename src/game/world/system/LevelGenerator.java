package game.world.system;

import java.util.Random;

import application.Vec2i;
import game.world.gameobject.tile.TileGrass;
import game.world.gameobject.tile.TileSand;

/**
 * Used in SystemLevel to generate new levels.
 */
public class LevelGenerator {

	private SystemLevel level;

	public LevelGenerator(SystemLevel level) {
		this.level = level;
	}

	public void generateTestLevel(Random random) {
		for (int y = -3; y <= 3; y++) {
			for (int x = -5; x <= 5; x++) {
				HexCoordinates coords = new HexCoordinates(new Vec2i(x, y));
				if (random.nextInt() % 3 == 0) {
					level.setTile((coords), new TileSand(level, coords));
				} else {
					level.setTile((coords), new TileGrass(level, coords));
				}
			}
		}
	}

}

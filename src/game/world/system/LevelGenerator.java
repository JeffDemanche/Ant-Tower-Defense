package game.world.system;

import java.util.Random;

import application.Vec2d;
import application.Vec2i;
import game.world.gameobject.tile.TileSand;
import game.world.gameobject.tile.TileWater;

/**
 * Used in SystemLevel to generate new levels.
 */
public class LevelGenerator {

	private SystemLevel level;

	private Vec2i size;

	public LevelGenerator(SystemLevel level, Vec2i size) {
		this.level = level;
		this.size = size;
	}

	private static double triangle(Vec2d sample, double radius) {
		double distToOrigin = Math
				.sqrt(sample.x * sample.x + sample.y * sample.y);
		return 1 - (distToOrigin / radius);
	}

	public void generateHeightIsland(Random random) {

		double seaLevel = 0.2;

		for (int y = (int) Math.floor(-size.y / 2); y < Math
				.ceil(size.y / 2); y++) {
			for (int x = (int) Math.floor(-size.x / 2); x < Math
					.ceil(size.x / 2); x++) {

				double height = triangle(new Vec2d(x, y), 15.0) * 20;

				HexCoordinates coords = new HexCoordinates(new Vec2i(x, y));
				long start = System.currentTimeMillis();
				if (height > seaLevel) {
					level.setTile((coords), new TileSand(level, coords));
				} else {
					level.setTile((coords), new TileWater(level, coords));
				}
				System.out.println(System.currentTimeMillis() - start);
			}
		}
	}

	public void generateTestLevel(Random random) {
		for (int y = -3; y <= 3; y++) {
			for (int x = -5; x <= 5; x++) {
				HexCoordinates coords = new HexCoordinates(new Vec2i(x, y));
				if (random.nextInt() % 3 == 0) {
					level.setTile((coords), new TileSand(level, coords));
				} else {
					level.setTile((coords), new TileWater(level, coords));
				}
			}
		}
	}

}

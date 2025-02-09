package game.world.system;

import java.util.Random;

import application.Vec2d;
import application.Vec2i;
import game.world.gameobject.tile.TileGrass;
import game.world.gameobject.tile.TileSand;
import game.world.gameobject.tile.TileWater;

/**
 * Used in SystemLevel to generate new levels.
 */
public class LevelGenerator {

	private SystemLevel level;

	private Vec2i size;
	private int radius;

	private double goalDistance;

	/**
	 * @param size
	 *            Width by height.
	 * @param goalDistance
	 *            Rough distance between the anthill and the sugar pile.
	 */
	public LevelGenerator(SystemLevel level, Vec2i size, double goalDistance) {
		this.level = level;
		this.size = size;
		this.radius = Math.min(size.x, size.y) / 2;
		this.goalDistance = goalDistance;
	}

	/**
	 * Gets a triangle noise value at the sample position, where the center of
	 * the triangle is 0, 0 and and the cone has radius.
	 */
	private static double triangle(Vec2d sample, double radius) {
		double distToOrigin = Math
				.sqrt(sample.x * sample.x + sample.y * sample.y);
		return 1 - (distToOrigin / radius);
	}

	private HexCoordinates getAntHill() {
		// TODO this should ensure more things and utilize randomness.
		return new HexCoordinates(new Vec2i((int) -(goalDistance / 2), 0));
	}

	private HexCoordinates getSugarPile() {
		return new HexCoordinates(new Vec2i((int) (goalDistance / 2), 0));
	}

	public void generateHeightIsland(Random random) {

		double seaLevel = 0.0;
		double grassLevel = 0.8;

		// Makes small isthmuses and stuff.
		PerlinNoise octaveFine = new PerlinNoise(random.nextInt(), 0.2, 1.4, 1,
				1);
		// Makes medium-sized islands.
		PerlinNoise octaveLarge = new PerlinNoise(random.nextInt(), 0.7, 0.6, 1,
				1);
		// Makes map-scale continents.
		PerlinNoise octaveContinents = new PerlinNoise(random.nextInt(), 0.7,
				0.25, 1, 1);

		double fineWeight = 0.3;
		double largeWeight = 1;
		double continentWeight = 1.3;

		for (int y = (int) Math.floor(-size.y / 2); y < Math
				.ceil(size.y / 2); y++) {
			for (int x = (int) Math.floor(-size.x / 2); x < Math
					.ceil(size.x / 2); x++) {

				double height = triangle(new Vec2d(x, y), radius / 2) * 3;
				height += octaveContinents.getHeight(x, y) * continentWeight;
				height += octaveLarge.getHeight(x, y) * largeWeight;
				height += octaveFine.getHeight(x, y) * fineWeight;

				HexCoordinates coords = new HexCoordinates(new Vec2i(x, y));
				if (height > grassLevel) {
					level.setTile((coords), new TileGrass(level, coords));
				} else if (height > seaLevel) {
					level.setTile((coords), new TileSand(level, coords));
				} else {
					level.setTile((coords), new TileWater(level, coords));
				}
			}
		}
		HexCoordinates coords = new HexCoordinates(new Vec2i(0, -2));
		level.setTile(coords, new TileWater(level, coords));
		level.setAntHill(getAntHill());
		if (!level.tileAt(getSugarPile()).traversableByDefault()) {
			System.err.println("Sugar pile on non-traversable tile???");
		}
		level.setSugarPile(getSugarPile());
	}

}

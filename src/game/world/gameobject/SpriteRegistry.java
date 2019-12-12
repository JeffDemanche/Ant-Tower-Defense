package game.world.gameobject;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public final class SpriteRegistry {

	private static boolean initialized = false;

	private static final int HEX_SAMPLE_MULTIPLIER = 8;

	public static Image EMPTY;

	public static Image SAND_MASKED;
	public static Image GRASS_MASKED;
	public static Image ANTHILL_MASKED;
	public static Image HONEY_MASKED;
	public static Image[] WATER_MASKED;

	public static Image CINNAMON;
	public static Image SEED_THROWER;
	public static Image HONEY;
	public static Image HONEY_PROJECTILE;
	public static Image HONEY_PARTICLE;

	public static Image WATER;
	public static Image WATER_PROJECTILE;
	public static Image WATER_PARTICLE;
	
	public static Image SPIDERWEB;
	public static Image SPIDER_PROJECTILE;
	
	public static Image STAR_PARTICLE;
	public static Image FIRE_PARTICLE;
	
	public SpriteRegistry() {
		System.out.println(
				"Working Directory = " + System.getProperty("user.dir"));

		EMPTY = new WritableImage(16, 16);

		SAND_MASKED = applyMask("file:src/img/tile/sand.png",
				"file:src/img/tile/hex_mask.png", 0, 16, HEX_SAMPLE_MULTIPLIER);
		GRASS_MASKED = applyMask("file:src/img/tile/grass.png",
				"file:src/img/tile/hex_mask.png", 0, 16, HEX_SAMPLE_MULTIPLIER);
		ANTHILL_MASKED = applyMask("file:src/img/tile/anthill.png",
				"file:src/img/tile/hex_mask.png", 0, 16, HEX_SAMPLE_MULTIPLIER);
		HONEY_MASKED = applyMask("file:src/img/tile/honeyTile.png",
				"file:src/img/tile/hex_mask.png", 0, 16, HEX_SAMPLE_MULTIPLIER);
		
		WATER_MASKED = new Image[3];
		for (int i = 0; i < 3; i++) {
			WATER_MASKED[i] = applyMask("file:src/img/tile/water.png",
					"file:src/img/tile/hex_mask.png", i, 16,
					HEX_SAMPLE_MULTIPLIER);
		}

		CINNAMON = applyUpscale(new Image("file:src/img/tower/cinnamon.png"),
				"file:src/img/tower/cinnamon.png", 6);
		SEED_THROWER = applyUpscale(new Image("file:src/img/tower/seed_thrower.png"),
				"file:src/img/tower/seed_thrower.png", 6);

		HONEY = applyUpscale(new Image("file:src/img/tower/honey.png"),
				"file:src/img/tower/honey.png", 6);
		
		
		HONEY_PROJECTILE = applyUpscale(new Image("file:src/img/tower/honeyProjectile.png"),
				"file:src/img/tower/honeyProjectile.png", 6);
		
		HONEY_PARTICLE = applyUpscale(new Image("file:src/img/particle/honeyParticle.png"),
				"file:src/img/particle/honeyParticle.png", 6);
		
		WATER =  applyUpscale(new Image("file:src/img/tower/water-gun.png"),
				"file:src/img/tower/water-gun.png", 6);
		
		WATER_PROJECTILE = applyUpscale(new Image("file:src/img/tower/waterProjectile.png"),
				"file:src/img/tower/waterProjectile.png", 6);
		
		WATER_PARTICLE = applyUpscale(new Image("file:src/img/particle/waterParticle.png"),
				"file:src/img/particle/waterParticle.png", 6);
		
		SPIDERWEB = applyUpscale(new Image("file:src/img/tower/spider-web.png"),
				"file:src/img/tower/spider-web.png", 6);
		
		SPIDER_PROJECTILE = applyUpscale(new Image("file:src/img/tower/spider.png"),
				"file:src/img/tower/spider.png", 6);
		
		STAR_PARTICLE = applyUpscale(new Image("file:src/img/particle/star.png"),
				"file:src/img/particle/star.png", 6);
		
		FIRE_PARTICLE = applyUpscale(new Image("file:src/img/particle/fire.png"),
				"file:src/img/particle/fire.png", 6);
				
		initialized = true;
	}

	public static boolean isInitialized() {
		return initialized;
	}

	public static Image applyUpscale(Image source, String sourcePath,
			int sampleMultiplier) {
		return new Image(sourcePath, source.getWidth() * sampleMultiplier,
				source.getHeight() * sampleMultiplier, true, false);
	}

	public static WritableImage applyMask(String spritePath, String maskPath,
			int phase, int frameWidth, int sampleMultiplier) {

		Image sprite = new Image(spritePath);
		sprite = new Image(spritePath, sprite.getWidth() * sampleMultiplier,
				sprite.getHeight() * sampleMultiplier, true, false);
		Image mask = new Image(maskPath);
		mask = new Image(maskPath, mask.getWidth() * sampleMultiplier,
				mask.getHeight() * sampleMultiplier, true, false);

		WritableImage composite = new WritableImage((int) mask.getWidth(),
				(int) mask.getWidth());

		int offset = phase * frameWidth * sampleMultiplier;
		PixelWriter pw = composite.getPixelWriter();
		for (int x = 0; x < mask.getWidth(); x++) {
			for (int y = 0; y < mask.getHeight(); y++) {
				Color originalColor = sprite.getPixelReader()
						.getColor(x + offset, y);
				double alpha = mask.getPixelReader().getColor(x, y)
						.getOpacity();
				pw.setColor(x, y,
						new Color(originalColor.getRed(),
								originalColor.getGreen(),
								originalColor.getBlue(), alpha));
			}
		}

		return composite;
	}

}

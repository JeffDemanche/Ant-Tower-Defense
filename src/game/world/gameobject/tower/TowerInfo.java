package game.world.gameobject.tower;

import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;
import javafx.scene.image.Image;

/**
 * This should represent a sort of static notion of a tower, so that it can be
 * represented without initializing a new tower object. That should be reserved
 * for when new towers are actually placed into the world.
 * 
 * @author jdemanch
 */
public class TowerInfo {

	public static TowerInfo CINNAMON = new TowerInfo("Cinnamon",
			"Hurts ants mildly as they walk over it.", 5, 30, 5,
			SpriteRegistry.CINNAMON, "file:src/img/tower/cinnamon.png");

	public static TowerInfo HONEY = new TowerInfo("Honey",
			"Stops ants momentarily, and expands over time", 10, 60, 15,
			SpriteRegistry.HONEY, "file:src/img/tower/honey.png");

	public final String name;
	public final String description;
	public final int cost;
	/** Per minute. */
	public final int rateOfFire;
	public final int damage;
	public final Image guiSprite;
	public final String spritePath;

	public TowerInfo(String name, String description, int cost, int rateOfFire,
			int damage, Image guiSprite, String spritePath) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rateOfFire = rateOfFire;
		this.damage = damage;
		this.guiSprite = guiSprite;
		this.spritePath = spritePath;
	}

	public static Tower createTower(SystemTowers system, HexCoordinates hex,
			TowerInfo info) {
		switch (info.name) {
		case "Cinnamon":
			return new TowerCinnamon(system, hex);
		case "Honey":
			return new HoneyTower(system, hex);
		default:
			return null;
		}
	}

}

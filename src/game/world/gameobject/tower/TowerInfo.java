package game.world.gameobject.tower;

import game.world.gameobject.SpriteRegistry;
import javafx.scene.image.Image;

public class TowerInfo {

	public static TowerInfo CINNAMON = new TowerInfo("Cinnamon",
			"Hurts ants mildly as they walk over it.", 5,
			SpriteRegistry.CINNAMON);

	public final String name;
	public final String description;
	public final int cost;
	public final Image guiSprite;

	public TowerInfo(String name, String descrprtion, int cost,
			Image guiSprite) {
		this.name = name;
		this.description = descrprtion;
		this.cost = cost;
		this.guiSprite = guiSprite;
	}

}

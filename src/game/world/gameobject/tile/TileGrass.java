package game.world.gameobject.tile;

import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentPolygon;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import javafx.scene.canvas.GraphicsContext;

public class TileGrass extends Tile {

	public TileGrass(GameSystem system, HexCoordinates offsetCoordinates) {
		super(system, createName("Grass", offsetCoordinates),
				offsetCoordinates);

		ComponentPolygon bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.GRASS_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

}

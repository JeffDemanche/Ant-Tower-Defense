package game.world.gameobject.tile;

import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentPolygon;
import game.world.gameobject.SpriteRegistry;
import game.world.system.HexCoordinates;
import javafx.scene.canvas.GraphicsContext;

public class TileSand extends Tile {

	public TileSand(GameSystem system, HexCoordinates offsetCoordinates) {
		super(system, createName("Sand", offsetCoordinates), offsetCoordinates);

		ComponentPolygon bound = offsetCoordinates.createPolygon(this);
		ComponentRegisteredSprite sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SAND_MASKED, bound);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

}

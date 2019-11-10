package game.world.gameobject.tile;

import engine.world.ComponentMaskedSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentPolygon;
import game.world.system.HexCoordinates;
import javafx.scene.canvas.GraphicsContext;

public class TileGrass extends Tile {

	public TileGrass(GameSystem system, HexCoordinates offsetCoordinates) {
		super(system, createName("Grass", offsetCoordinates), offsetCoordinates);

		ComponentPolygon bound = offsetCoordinates.createPolygon(this);
		ComponentMaskedSprite sprite = new ComponentMaskedSprite(this,
				"img/tile/grass.png", "img/tile/hex_mask.png", bound, 6);

		this.addComponent(bound);
		this.addComponent(sprite);
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

}

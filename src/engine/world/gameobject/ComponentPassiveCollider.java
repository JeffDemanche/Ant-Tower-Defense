package engine.world.gameobject;

import javafx.scene.canvas.GraphicsContext;

public class ComponentPassiveCollider extends Component {

	private String layer;
	private Collidable collidable;

	public ComponentPassiveCollider(String layer, GameObject object,
			Collidable collidable) {
		super("Passive Collider", object);
		this.layer = layer;
		this.collidable = collidable;
	}

	public String getLayer() {
		return this.layer;
	}

	public Collidable getCollidable() {
		return this.collidable;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

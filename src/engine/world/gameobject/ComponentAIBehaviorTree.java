package engine.world.gameobject;

import engine.world.gameobject.behavior.BehaviorNode;
import javafx.scene.canvas.GraphicsContext;

/**
 * Game objects with this component have some behavior specified by a behavior
 * tree.
 * 
 * @author jdemanch
 */
public class ComponentAIBehaviorTree extends Component {
	
	private BehaviorNode rootBehavior;

	public ComponentAIBehaviorTree(GameObject object, BehaviorNode rootBehavior) {
		super("AI", object);
		this.rootBehavior = rootBehavior;
	}

	@Override
	public void onDraw(GraphicsContext g) {
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		this.rootBehavior.tickBehavior(nanosSincePreviousTick);
	}

	@Override
	public void onGameObjectRemoved() {
	}

}

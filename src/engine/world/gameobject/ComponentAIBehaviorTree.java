package engine.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

	public ComponentAIBehaviorTree(GameObject object,
			BehaviorNode rootBehavior) {
		super("AI", object);
		this.rootBehavior = rootBehavior;
	}

	public ComponentAIBehaviorTree(GameObject object, BehaviorNode rootBehavior,
			Element element) {
		this(object, rootBehavior);
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

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentAIBehavior = doc
				.createElement("ComponentAIBehaviorTree");
		return componentAIBehavior;
	}

}

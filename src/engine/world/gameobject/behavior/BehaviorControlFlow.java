package engine.world.gameobject.behavior;

import java.util.List;

/**
 * Implemented by selectors and sequences.
 * @author jdemanch
 */
public interface BehaviorControlFlow {

	public List<BehaviorNode> getChildren();
	
	public void addChild(BehaviorNode child);
	
}

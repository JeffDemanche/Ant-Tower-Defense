package engine.world.gameobject.behavior;

/**
 * To be implemented by all nodes in a behavior tree.
 */
public interface BehaviorNode {

	public BehaviorStatus tickBehavior(long nanosSinceLastTick);
	
	public Blackboard getBlackboard();
	
}

package engine.world.gameobject.behavior;

public class BehaviorConditionPathfinding implements BehaviorNode {

	private Blackboard blackboard;
	private BehaviorAIPathfind<?> pathfinder;

	public BehaviorConditionPathfinding(Blackboard blackboard,
			BehaviorAIPathfind<?> pathfinder) {
		this.blackboard = blackboard;
		this.pathfinder = pathfinder;
	}
	
	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		return pathfinder.isRunning() ? BehaviorStatus.SUCCESS
				: BehaviorStatus.FAILURE;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

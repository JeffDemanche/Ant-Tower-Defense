package engine.world.gameobject.behavior;

import engine.world.WorldError;

public class BehaviorWrapperNot implements BehaviorNode {

	private Blackboard blackboard;
	private BehaviorNode child;

	public BehaviorWrapperNot(Blackboard blackboard, BehaviorNode child) {
		this.blackboard = blackboard;
		this.child = child;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		BehaviorStatus childStatus = child.tickBehavior(nanosSinceLastTick);

		switch (childStatus) {
		case SUCCESS:
			return BehaviorStatus.FAILURE;
		case FAILURE:
			return BehaviorStatus.SUCCESS;
		case RUNNING:
			return BehaviorStatus.RUNNING;
		default:
			throw new WorldError("Unexpected input to NOT wrapper");
		}
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

package engine.world.gameobject.behavior;

import engine.world.WorldError;

public class BehaviorWrapperNot implements BehaviorNode {

	private Blackboard blackboard;
	private BehaviorNode child;
	
	private boolean debug;

	public BehaviorWrapperNot(Blackboard blackboard, BehaviorNode child) {
		this.blackboard = blackboard;
		this.child = child;
	}

	public void debug() {
		this.debug = true;
	}
	
	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		BehaviorStatus childStatus = child.tickBehavior(nanosSinceLastTick);
		
		BehaviorStatus thisStatus;

		switch (childStatus) {
		case SUCCESS:
			thisStatus = BehaviorStatus.FAILURE;
			break;
		case FAILURE:
			thisStatus = BehaviorStatus.SUCCESS;
			break;
		case RUNNING:
			thisStatus = BehaviorStatus.RUNNING;
			break;
		default:
			throw new WorldError("Unexpected input to NOT wrapper");
		}
		
		if (debug)
			System.out.println(this.getClass().getSimpleName() + " " + thisStatus.name());
		
		return thisStatus;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

package engine.world.gameobject.behavior;

public class BehaviorDoNothing implements BehaviorNode {

	private Blackboard blackboard;

	private double seconds;
	private double secondsRemaining;

	public BehaviorDoNothing(Blackboard blackboard, double seconds) {
		this.blackboard = blackboard;
		this.seconds = seconds;
		this.secondsRemaining = seconds;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		secondsRemaining -= nanosSinceLastTick / 1000000000D;
		if (secondsRemaining <= 0) {
			secondsRemaining = seconds;
			return BehaviorStatus.SUCCESS;
		} else {
			return BehaviorStatus.RUNNING;
		}
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

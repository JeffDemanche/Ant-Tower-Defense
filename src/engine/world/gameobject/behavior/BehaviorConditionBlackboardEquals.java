package engine.world.gameobject.behavior;

public class BehaviorConditionBlackboardEquals implements BehaviorNode {

	private Blackboard blackboard;

	private String key;
	private Object value;

	public BehaviorConditionBlackboardEquals(Blackboard blackboard, String key,
			Object value) {
		this.blackboard = blackboard;

		this.key = key;
		this.value = value;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		return this.blackboard.get(key).getValue().equals(value)
				? BehaviorStatus.SUCCESS
				: BehaviorStatus.FAILURE;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

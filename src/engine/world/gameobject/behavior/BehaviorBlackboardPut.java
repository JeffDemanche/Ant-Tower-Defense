package engine.world.gameobject.behavior;

public class BehaviorBlackboardPut implements BehaviorNode {

	private Blackboard blackboard;

	private String key;
	private Object value;

	public BehaviorBlackboardPut(Blackboard blackboard, String key,
			Object value) {
		this.blackboard = blackboard;

		this.key = key;
		this.value = value;
	}
	
	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		this.blackboard.put(key, value);
		return BehaviorStatus.SUCCESS;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

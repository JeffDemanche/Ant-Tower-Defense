package engine.world.gameobject.behavior;

public class BehaviorCustomFunction implements BehaviorNode {

	public interface Callback {
		public void callback();
	}
	
	private Blackboard blackboard;
	private Callback callback;

	public BehaviorCustomFunction(Blackboard blackboard, Callback callback) {
		this.blackboard = blackboard;
		this.callback = callback;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		this.callback.callback();
		return BehaviorStatus.SUCCESS;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

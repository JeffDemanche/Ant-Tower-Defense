package engine.world.gameobject.behavior;

import java.util.ArrayList;
import java.util.List;

public class BehaviorSelector implements BehaviorNode, BehaviorControlFlow {

	private Blackboard blackboard;
	private ArrayList<BehaviorNode> children;

	public BehaviorSelector(Blackboard blackboard) {
		this.children = new ArrayList<>();
		this.blackboard = blackboard;
	}

	@Override
	public List<BehaviorNode> getChildren() {
		return this.children;
	}

	@Override
	public void addChild(BehaviorNode child) {
		this.children.add(child);
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		for (BehaviorNode child : children) {
			BehaviorStatus childStatus = child.tickBehavior(nanosSinceLastTick);

			if (childStatus == BehaviorStatus.RUNNING) {
				return BehaviorStatus.RUNNING;
			} else if (childStatus == BehaviorStatus.SUCCESS) {
				return BehaviorStatus.SUCCESS;
			}
		}
		return BehaviorStatus.FAILURE;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}
}

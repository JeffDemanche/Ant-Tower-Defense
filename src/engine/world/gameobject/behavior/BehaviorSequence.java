package engine.world.gameobject.behavior;

import java.util.ArrayList;
import java.util.List;

public class BehaviorSequence implements BehaviorNode, BehaviorControlFlow {

	private Blackboard blackboard;
	private ArrayList<BehaviorNode> children;
	
	public BehaviorSequence(Blackboard blackboard) {
		this.children = new ArrayList<>();
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
		for (BehaviorNode child : this.children) {
			BehaviorStatus childStatus = child.tickBehavior(nanosSinceLastTick);
			
			if (childStatus == BehaviorStatus.RUNNING) {
				return BehaviorStatus.RUNNING;
			} else if (childStatus == BehaviorStatus.FAILURE) {
				return BehaviorStatus.FAILURE;
			}
		}
		return BehaviorStatus.SUCCESS;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

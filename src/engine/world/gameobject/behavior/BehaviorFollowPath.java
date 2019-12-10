package engine.world.gameobject.behavior;

import java.util.Stack;

import engine.world.PathfindableCoordinates;
import engine.world.gameobject.ComponentNavigable;

public class BehaviorFollowPath<T extends PathfindableCoordinates>
		implements BehaviorNode {

	private Blackboard blackboard;

	private ComponentNavigable navigable;
	private Stack<T> currentPath;
	private boolean isRunning;

	private boolean debug;

	public BehaviorFollowPath(Blackboard blackboard, Stack<T> path,
			ComponentNavigable navigable) {
		this.blackboard = blackboard;

		this.navigable = navigable;

		this.isRunning = false;
		this.currentPath = path;
	}

	public void debug() {
		this.debug = true;
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		if (!isRunning || currentPath == null) {
			isRunning = true;
		}
		
		BehaviorStatus status;

		if (!navigable.isMoving()) {
			if (currentPath.isEmpty()) {
				isRunning = false;
				currentPath = null;
				status = BehaviorStatus.SUCCESS;
			} else {
				T nextLocation = currentPath.pop();
				navigable.setNextDestination(
						nextLocation.getPathfindingGameSpace());
				status = BehaviorStatus.RUNNING;
			}
		} else {
			status = BehaviorStatus.RUNNING;
		}

		if (debug)
			System.out.println(
					this.getClass().getSimpleName() + " " + status.name());

		return status;
	}

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

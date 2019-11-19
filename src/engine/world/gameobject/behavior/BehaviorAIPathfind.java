package engine.world.gameobject.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import engine.world.PathfindableCoordinates;
import engine.world.TileCoordinates;
import engine.world.WorldError;
import engine.world.gameobject.ComponentNavigable;
import engine.world.gameobject.Drawable;

public class BehaviorAIPathfind<T extends PathfindableCoordinates>
		implements BehaviorNode {

	private Blackboard blackboard;

	private TileCoordinates<T> coordinates;
	private Drawable drawable;
	private ComponentNavigable navigable;
	private T startPosition;
	private T destination;

	private Stack<T> currentPath;
	private boolean isRunning;
	
	private boolean debug;

	public BehaviorAIPathfind(Blackboard blackboard,
			TileCoordinates<T> coordinates, Drawable drawable,
			ComponentNavigable navigable, T startPosition, T destination) {
		this.blackboard = blackboard;

		this.coordinates = coordinates;
		this.drawable = drawable;
		this.navigable = navigable;
		this.startPosition = startPosition;
		this.destination = destination;

		this.currentPath = null;
		this.isRunning = false;
	}
	
	public void debug() {	
		this.debug = true;
	}
	
	public boolean isRunning() {
		return this.isRunning;
	}

	public T getStartPosition() {
		return this.startPosition;
	}

	@Override
	public BehaviorStatus tickBehavior(long nanosSinceLastTick) {
		if (!isRunning || currentPath == null) {
			currentPath = createPath(this.startPosition, this.destination);
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
			System.out.println(this.getClass().getSimpleName() + " " + status.name());
		
		return status;
	}

	private Stack<T> createPath(T nodeStart, T nodeEnd) {
		T currentNode = nodeStart;

		// Key is the child of value.
		HashMap<T, T> parentRelations = new HashMap<>();

		HashMap<T, Double> g = new HashMap<>();
		HashMap<T, Double> h = new HashMap<>();

		ArrayList<T> closed = new ArrayList<>();
		ArrayList<T> open = new ArrayList<>();
		open.add(nodeStart);

		while (!open.isEmpty()) {
			double minF = Double.MAX_VALUE;

			for (T possibleCurrent : open) {
				g.put(possibleCurrent, possibleCurrent.dist(nodeStart));
				h.put(possibleCurrent, possibleCurrent.dist(nodeEnd));
				double f = g.get(possibleCurrent) + h.get(possibleCurrent);
				if (f < minF) {
					currentNode = possibleCurrent;
					minF = f;
				}
			}

			if (currentNode.equals(nodeEnd)) {
				break;
			}

			Set<T> neighbors = coordinates.getTraversableNeighbors(
					currentNode.getX(), currentNode.getY());

			for (T nodeSuccessor : neighbors) {
				double successorCurrentCost = g.get(currentNode)
						+ currentNode.dist(nodeSuccessor);

				if (open.contains(nodeSuccessor)) {
					if (g.get(nodeSuccessor) <= successorCurrentCost) {
						continue;
					}
				} else if (closed.contains(nodeSuccessor)) {
					if (g.get(nodeSuccessor) <= successorCurrentCost) {
						continue;
					}
					closed.remove(nodeSuccessor);
					open.add(nodeSuccessor);
				} else {
					open.add(nodeSuccessor);
					h.put(nodeSuccessor, nodeSuccessor.dist(nodeEnd));
				}
				g.put(nodeSuccessor, successorCurrentCost);
				parentRelations.put(nodeSuccessor, currentNode);
			}
			open.remove(currentNode);
			closed.add(currentNode);
		}
		if (!currentNode.equals(nodeEnd)) {
			throw new WorldError("Pathfinding error");
		}

		// Consolidate path from parent relations.
		Stack<T> p = new Stack<>();
		p.add(nodeEnd);
		while (!p.peek().equals(nodeStart)) {
			p.add(parentRelations.get(p.peek()));
		}

		return p;
	}

	// @Deprecated
	// private Stack<Vec2d> createPath(Vec2d nodeStart, Vec2d nodeEnd) {
	// Vec2d currentNode = nodeStart;
	//
	// // Key is the child of value.
	// HashMap<Vec2d, Vec2d> parentRelations = new HashMap<>();
	//
	// HashMap<Vec2d, Double> g = new HashMap<>();
	// HashMap<Vec2d, Double> h = new HashMap<>();
	//
	// ArrayList<Vec2d> closed = new ArrayList<>();
	// ArrayList<Vec2d> open = new ArrayList<>();
	// open.add(nodeStart);
	//
	// while (!open.isEmpty()) {
	// double minF = Double.MAX_VALUE;
	//
	// for (Vec2d possibleCurrent : open) {
	// g.put(possibleCurrent, possibleCurrent.dist(nodeStart));
	// h.put(possibleCurrent, possibleCurrent.dist(nodeEnd));
	// double f = g.get(possibleCurrent) + h.get(possibleCurrent);
	// if (f < minF) {
	// currentNode = possibleCurrent;
	// minF = f;
	// }
	// }
	//
	// if (currentNode.equals(nodeEnd)) {
	// break;
	// }
	//
	// Set<Vec2d> neighbors = coordinates.getTraversableNeighbors(
	// (int) currentNode.x, (int) currentNode.y);
	//
	// for (Vec2d nodeSuccessor : neighbors) {
	// double successorCurrentCost = g.get(currentNode)
	// + currentNode.dist(nodeSuccessor);
	//
	// if (open.contains(nodeSuccessor)) {
	// if (g.get(nodeSuccessor) <= successorCurrentCost) {
	// continue;
	// }
	// } else if (closed.contains(nodeSuccessor)) {
	// if (g.get(nodeSuccessor) <= successorCurrentCost) {
	// continue;
	// }
	// closed.remove(nodeSuccessor);
	// open.add(nodeSuccessor);
	// } else {
	// open.add(nodeSuccessor);
	// h.put(nodeSuccessor, nodeSuccessor.dist(nodeEnd));
	// }
	// g.put(nodeSuccessor, successorCurrentCost);
	// parentRelations.put(nodeSuccessor, currentNode);
	// }
	// open.remove(currentNode);
	// closed.add(currentNode);
	// }
	// if (!currentNode.equals(nodeEnd)) {
	// throw new WorldError("Pathfinding error");
	// }
	//
	// // Consolidate path from parent relations.
	// Stack<Vec2d> p = new Stack<>();
	// p.add(nodeEnd);
	// while (!p.peek().equals(nodeStart)) {
	// p.add(parentRelations.get(p.peek()));
	// }
	//
	// return p;
	// }

	@Override
	public Blackboard getBlackboard() {
		return this.blackboard;
	}

}

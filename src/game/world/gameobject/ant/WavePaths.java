package game.world.gameobject.ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import engine.world.WorldError;
import game.world.system.HexCoordinates;
import game.world.system.SystemLevel;

/**
 * Handles managing pathfinding for waves. Paths to get to the sugar pile and
 * back are created before the wave starts, since pathfinding is slow.
 * 
 * @author jdemanch
 */
public class WavePaths {

	private SystemLevel level;

	private Random random;

	/** Stores all paths to sugar pile. */
	private ArrayList<Stack<HexCoordinates>> to;

	/** Stores all paths from sugar pile. */
	private ArrayList<Stack<HexCoordinates>> from;

	public WavePaths(SystemLevel level, Random random, int pathsTo,
			int pathsFrom) {
		this.level = level;

		this.random = random;

		this.to = new ArrayList<>();
		this.from = new ArrayList<>();

		for (int i = 0; i < pathsTo; i++) {
			this.to.add(createPath(level, random, level.getAntHill(),
					level.getSugarPile()));
		}
		for (int i = 0; i < pathsFrom; i++) {
			this.from.add(createPath(level, random, level.getSugarPile(),
					level.getAntHill()));
		}
	}

	/**
	 * Gets a random path from those generated.
	 */
	public Stack<HexCoordinates> getPathTo() {
		@SuppressWarnings("unchecked")
		Stack<HexCoordinates> s = (Stack<HexCoordinates>) to
				.get(random.nextInt(to.size())).clone();
		return s;
	}

	/**
	 * Gets a random path from those generated.
	 */
	public Stack<HexCoordinates> getPathFrom() {
		@SuppressWarnings("unchecked")
		Stack<HexCoordinates> s = (Stack<HexCoordinates>) from
				.get(random.nextInt(from.size())).clone();
		return s;
	}

	public static Stack<HexCoordinates> createPath(SystemLevel coordinates,
			Random random, HexCoordinates nodeStart, HexCoordinates nodeEnd) {
		TileMemory tileMemory = coordinates.getTileMemory();

		HexCoordinates currentNode = nodeStart;

		// Key is the child of value.
		HashMap<HexCoordinates, HexCoordinates> parentRelations = new HashMap<>();

		HashMap<HexCoordinates, Double> g = new HashMap<>();
		HashMap<HexCoordinates, Double> h = new HashMap<>();

		ArrayList<HexCoordinates> closed = new ArrayList<>();
		ArrayList<HexCoordinates> open = new ArrayList<>();
		open.add(nodeStart);

		while (!open.isEmpty()) {
			double minF = Double.MAX_VALUE;

			for (HexCoordinates possibleCurrent : open) {
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

			Set<HexCoordinates> neighbors = coordinates.getTraversableNeighbors(
					currentNode.getX(), currentNode.getY());

			// Keep this info in the edge case that all neighbors are avoided.
			// If that happens the path just chooses the neighbor with the
			// lowest avoidance.
			HexCoordinates lowestHex = null;
			double lowestAvoidance = 2;

			// This loops through neighbors, we figure out tile avoidance
			// probability in here.
			Iterator<HexCoordinates> neighborsIter = neighbors.iterator();
			while (neighborsIter.hasNext()) {
				HexCoordinates neighbor = neighborsIter.next();
				double rand = random.nextDouble();

				double neighborAvoidance = tileMemory.getAvoidance(neighbor);

				if (neighborAvoidance < lowestAvoidance) {
					lowestHex = neighbor;
					lowestAvoidance = neighborAvoidance;
				}

				if (tileMemory.getAvoidance(neighbor) > rand) {
					// If probability says this path should avoid this neighbor.
					neighborsIter.remove();
				}
			}
			
			if (neighbors.isEmpty()) {
				neighbors.add(lowestHex);
			}

			for (HexCoordinates nodeSuccessor : neighbors) {
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
		Stack<HexCoordinates> p = new Stack<>();
		p.add(nodeEnd);
		while (!p.peek().equals(nodeStart)) {
			p.add(parentRelations.get(p.peek()));
		}

		return p;
	}

}

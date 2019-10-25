package engine.world.gameobject;

import java.util.ArrayList;

import application.Vec2d;

public final class CollisionsPolygon {
	
	/*
	 * PROJECTIONS
	 */
	public static double[] projectPolygon(ComponentPolygon s1, Vec2d s2) {

		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;

		for (Vec2d point : s1.getPoints()) {
			Vec2d proj = point.projectOnto(s2);

			boolean sameDirection = s2.dot(proj) > 0;
			double projMag = proj.mag();
			if (!sameDirection)
				projMag = -projMag;

			if (projMag < min) {
				min = projMag;
			}
			if (projMag > max) {
				max = projMag;
			}
		}

		return new double[] { min, max };
	}

	public static double[] projectCircle(ComponentCircle s1, Vec2d s2) {
		Vec2d proj = s1.getPosition().projectOnto(s2);

		double projectedCenter = proj.mag();
		boolean sameDirection = s2.dot(proj) > 0;

		if (!sameDirection)
			projectedCenter = -projectedCenter;

		return new double[] { projectedCenter - s1.getRadius(),
				projectedCenter + s1.getRadius() };
	}

	public static double[] projectAAB(ComponentAABB s1, Vec2d s2) {

		ArrayList<Vec2d> points = new ArrayList<>();
		points.add(s1.getPosition());
		points.add(s1.getPosition().plus(s1.getSize()));
		points.add(s1.getPosition().plus(new Vec2d(0, s1.getSize().y)));
		points.add(s1.getPosition().plus(new Vec2d(s1.getSize().x, 0)));

		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;

		for (Vec2d point : points) {
			Vec2d proj = point.projectOnto(s2);

			boolean sameDirection = s2.dot(proj) > 0;
			double projMag = proj.mag();
			if (!sameDirection)
				projMag = -projMag;

			if (projMag < min) {
				min = projMag;
			}
			if (projMag > max) {
				max = projMag;
			}
		}
		return new double[] { min, max };
	}

	public static double intervalMTV(double[] a, double[] b) {
		double aRight = b[1] - a[0];
		double aLeft = a[1] - b[0];
		if (aLeft < 0 || aRight < 0) {
			return 0;
		}
		if (aRight < aLeft) {
			return aRight;
		} else {
			return -aLeft;
		}
	}
	
	public static boolean pointToPolygon(Vec2d s1, ComponentPolygon s2) {
		for (int i = 0; i < s2.getNumPoints(); i++) {
			Vec2d p1 = s2.getPoint(i);
			Vec2d p2 = i == s2.getNumPoints() - 1 ? s2.getPoint(0)
					: s2.getPoint(i + 1);

			Vec2d p = p2.minus(p1);
			Vec2d v = p1.minus(s1);

			if (v.cross(p) > 0) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * MTVs
	 */
	
	public static Vec2d mtvPolygonToAABB(ComponentAABB s1, ComponentPolygon s2) {
		ArrayList<Vec2d> vectors = new ArrayList<>();
		vectors.add(new Vec2d(1, 0));
		vectors.add(new Vec2d(0, 1));

		for (int i = 0; i < s2.getNumPoints(); i++) {
			Vec2d p1 = s2.getPoint(i);
			Vec2d p2 = i == s2.getNumPoints() - 1 ? s2.getPoint(0)
					: s2.getPoint(i + 1);

			Vec2d polyEdgeVector = p2.minus(p1);

			vectors.add(
					new Vec2d(-polyEdgeVector.y, polyEdgeVector.x).normalize());
		}

		double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;

		for (int i = 0; i < vectors.size(); i++) {
			Vec2d axis = vectors.get(i);
			double[] polyProj = projectPolygon(s2, axis);
			double[] aabProj = projectAAB(s1, axis);
			
			double mtv1d = intervalMTV(polyProj, aabProj);
			
			if (mtv1d == 0) {
				return null;
			}
			if (Math.abs(mtv1d) < minMagnitude) {
				minMagnitude = Math.abs(mtv1d);
				mtv = axis.smult(mtv1d).reflect();
			}
		}
		return mtv;
	}
	
	public static Vec2d mtvPolygonToCircle(ComponentCircle s1, ComponentPolygon s2) {
		double closestDist = Double.MAX_VALUE;
		Vec2d closestPointOnPoly = null;
		for (Vec2d point : s2.getPoints()) {
			double dist = s1.getPosition().dist(point);
			if (dist < closestDist) {
				closestPointOnPoly = point;
				closestDist = dist;
			}
		}
		Vec2d circleVector = s1.getPosition().minus(closestPointOnPoly)
				.normalize();

		ArrayList<Vec2d> vectors = new ArrayList<>();
		vectors.add(circleVector);

		for (int i = 0; i < s2.getNumPoints(); i++) {
			Vec2d p1 = s2.getPoint(i);
			Vec2d p2 = i == s2.getNumPoints() - 1 ? s2.getPoint(0)
					: s2.getPoint(i + 1);
			Vec2d polyEdgeVector = p2.minus(p1);

			vectors.add((new Vec2d(-polyEdgeVector.y, polyEdgeVector.x))
					.normalize());
		}

		double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;

		for (Vec2d axis : vectors) {
			double[] polyProj = projectPolygon(s2, axis);
			double[] circleProj = projectCircle(s1, axis);

			double mtv1d = intervalMTV(polyProj, circleProj);

			if (mtv1d == 0) {
				return null;
			}
			if (Math.abs(mtv1d) < minMagnitude) {
				minMagnitude = Math.abs(mtv1d);
				mtv = axis.smult(mtv1d).reflect();
			}
		}
		return mtv;
	}
	
	public static Vec2d mtvPolygonToPolygon(ComponentPolygon s1, ComponentPolygon s2) {
		ArrayList<Vec2d> vectors = new ArrayList<>();

		for (int i = 0; i < s1.getNumPoints(); i++) {
			Vec2d p1 = s1.getPoint(i);
			Vec2d p2 = i == s1.getNumPoints() - 1 ? s1.getPoint(0)
					: s1.getPoint(i + 1);
			Vec2d polyEdgeVector = p2.minus(p1);
			vectors.add(new Vec2d(-polyEdgeVector.y, polyEdgeVector.x));
		}
		for (int j = 0; j < s2.getNumPoints(); j++) {
			Vec2d p1 = s2.getPoint(j);
			Vec2d p2 = j == s2.getNumPoints() - 1 ? s2.getPoint(0)
					: s2.getPoint(j + 1);
			Vec2d polyEdgeVector = p2.minus(p1);
			vectors.add(new Vec2d(-polyEdgeVector.y, polyEdgeVector.x));
		}

		double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;

		for (Vec2d axis : vectors) {
			double[] poly1Proj = projectPolygon(s2, axis);
			double[] poly2Proj = projectPolygon(s1, axis);

			double mtv1d = intervalMTV(poly1Proj, poly2Proj);

			if (mtv1d == 0) {
				return null;
			}
			if (Math.abs(mtv1d) < minMagnitude) {
				minMagnitude = Math.abs(mtv1d);
				mtv = axis.smult(mtv1d).reflect();
			}
		}
		return mtv;
	}
	
}

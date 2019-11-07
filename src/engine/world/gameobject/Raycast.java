package engine.world.gameobject;

import application.Vec2d;

public final class Raycast {
	public static double raycast(Vec2d edgeA, Vec2d edgeB, Ray ray) {
		Vec2d m = edgeA.minus(edgeB).normalize();
		Vec2d n = new Vec2d(m.y, -m.x).normalize();

		double cross1 = edgeA.minus(ray.src).cross(ray.dir);
		double cross2 = edgeB.minus(ray.src).cross(ray.dir);

		// Opposite signs
		if (cross1 * cross2 >= 0) {
			return Float.MAX_VALUE;
		}

		double t = (edgeB.minus(ray.src).dot(n)) / (ray.dir.dot(n));

		return t;
	}

	public static double raycast(ComponentAABB s1, Ray s2) {
		Vec2d p1 = s1.getGamePosition();
		Vec2d p2 = new Vec2d(s1.getGamePosition().x + s1.getSize().x,
				s1.getGamePosition().y);
		Vec2d p3 = s1.getGamePosition().plus(s1.getSize());
		Vec2d p4 = new Vec2d(s1.getGamePosition().x,
				s1.getGamePosition().y + s1.getSize().y);

		double minT = Double.MAX_VALUE;

		double t1 = raycast(p1, p2, s2);
		double t2 = raycast(p2, p3, s2);
		double t3 = raycast(p3, p4, s2);
		double t4 = raycast(p4, p1, s2);

		if (t1 < minT)
			minT = t1;
		if (t2 < minT)
			minT = t2;
		if (t3 < minT)
			minT = t3;
		if (t4 < minT)
			minT = t4;

		return minT;
	}

	public static double raycast(ComponentCircle s1, Ray s2) {
		// Ray source collides circle.
		if (Collisions.pointToCircle(s2.src, s1)) {
			Vec2d projectedRaySpace = s1.getGamePosition().minus(s2.src)
					.projectOnto(s2.dir.normalize());
			Vec2d projected = projectedRaySpace.plus(s2.src);

			double x = projected.minus(s1.getGamePosition()).mag();

			double length = projectedRaySpace.mag()
					+ Math.sqrt(Math.pow(s1.getRadius(), 2) - Math.pow(x, 2));

			return Collisions.pointToCircle(projected, s1) ? length
					: Double.MAX_VALUE;
		} else {
			Vec2d projectedRaySpace = s1.getGamePosition().minus(s2.src)
					.projectOnto(s2.dir.normalize());
			Vec2d projected = projectedRaySpace.plus(s2.src);

			boolean positive = s1.getGamePosition().minus(s2.src)
					.dot(s2.dir.normalize()) > 0;

			double x = projected.minus(s1.getGamePosition()).mag();

			double length = projectedRaySpace.mag()
					- Math.sqrt(Math.pow(s1.getRadius(), 2) - Math.pow(x, 2));

			return (positive && Collisions.pointToCircle(projected, s1))
					? length
					: Double.MAX_VALUE;
		}
	}

	public static double raycast(ComponentPolygon s1, Ray s2) {
		double minT = Double.MAX_VALUE;

		for (int i = 0; i < s1.getNumPoints(); i++) {
			int j = i + 1;
			if (i == s1.getNumPoints() - 1) {
				j = 0;
			}
			Vec2d edgeA = s1.getPoint(i);
			Vec2d edgeB = s1.getPoint(j);

			double t = raycast(edgeA, edgeB, s2);
			if (t < minT) {
				minT = t;
			}
		}

		return minT;
	}
}

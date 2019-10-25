package engine.world.gameobject;

import com.sun.javafx.geom.Vec2f;

import application.Vec2d;

/**
 * Contains static methods for calculating collision stuff.
 * 
 * @author jdemanch
 */
public final class Collisions {

	public static boolean AABBtoAABB(ComponentAABB collider1,
			ComponentAABB collider2) {
		double otherX1 = collider1.getGamePosition().x;
		double otherX2 = otherX1 + collider1.getSize().x;
		double thisX1 = collider2.getGamePosition().x;
		double thisX2 = thisX1 + collider2.getSize().x;

		double otherY1 = collider1.getGamePosition().y;
		double otherY2 = otherY1 + collider1.getSize().y;
		double thisY1 = collider2.getGamePosition().y;
		double thisY2 = thisY1 + collider2.getSize().y;

		boolean xOverlap = !(thisX2 < otherX1 || otherX2 < thisX1);
		boolean yOverlap = !(thisY2 < otherY1 || otherY2 < thisY1);

		return xOverlap && yOverlap;
	}

	public static Vec2d mtvAABBtoAABB(ComponentAABB collider1,
			ComponentAABB collider2) {
		Vec2d up = new Vec2d(0, collider2.getGamePosition().y
				- collider1.getGamePosition().plus(collider1.getSize()).y);
		Vec2d down = new Vec2d(0,
				collider2.getGamePosition().plus(collider2.getSize()).y
						- collider1.getGamePosition().y);
		Vec2d left = new Vec2d(collider2.getGamePosition().x
				- collider1.getGamePosition().plus(collider1.getSize()).x,
				0);
		Vec2d right = new Vec2d(
				collider2.getGamePosition().plus(collider2.getSize()).x
						- collider1.getGamePosition().x,
				0);

		double min = Math.min(Math.abs(up.y), Math.min(Math.abs(down.y),
				Math.min(Math.abs(left.x), Math.abs(right.x))));

		Vec2d mtv = null;

		if (Math.abs(up.y) <= min)
			mtv = up;
		else if (Math.abs(down.y) <= min)
			mtv = down;
		else if (Math.abs(left.x) <= min)
			mtv = left;
		else if (Math.abs(right.x) <= min)
			mtv = right;

		return AABBtoAABB(collider1, collider2) ? mtv : null;
	}

	public static boolean AABBtoCircle(ComponentAABB collider1,
			ComponentCircle colliderCircle) {
		double aabbClosestX = Math.max(collider1.getGamePosition().x,
				Math.min(
						collider1.getGamePosition().x
								+ collider1.getSize().x,
						colliderCircle.getGamePosition().x));
		double aabbClosestY = Math.max(collider1.getGamePosition().y,
				Math.min(
						collider1.getGamePosition().y
								+ collider1.getSize().y,
						colliderCircle.getGamePosition().y));

		double distX = colliderCircle.getGamePosition().x - aabbClosestX;
		double distY = colliderCircle.getGamePosition().y - aabbClosestY;

		// Distance from the closest AABB point to the center of the circle is
		// less than the circle's radius.
		return Math.sqrt(distX * distX + distY * distY) <= colliderCircle
				.getRadius();
	}

	public static Vec2d mtvAABBtoCircle(ComponentAABB s1, ComponentCircle s2) {
		Vec2d bottomRight = s1.getGamePosition().plus(s1.getSize());
		boolean containsCenter = s2.getGamePosition().x >= s1
				.getGamePosition().x && s2.getGamePosition().x <= bottomRight.x
				&& s2.getGamePosition().y >= s1.getGamePosition().y
				&& s2.getGamePosition().y <= bottomRight.y;

		double distanceToTop = s1.getGamePosition().y - s2.getGamePosition().y;
		double distanceToBottom = bottomRight.y - s2.getGamePosition().y;
		double distanceToLeft = s2.getGamePosition().x - s1.getGamePosition().x;
		double distanceToRight = s2.getGamePosition().x - bottomRight.x;

		if (containsCenter) {
			double verticalDist;
			if (Math.abs(distanceToTop) < Math.abs(distanceToBottom)) {
				verticalDist = distanceToTop;
			} else {
				verticalDist = distanceToBottom;
			}
			double horizontalDist;
			if (Math.abs(distanceToLeft) < Math.abs(distanceToRight)) {
				horizontalDist = distanceToLeft;
			} else {
				horizontalDist = distanceToRight;
			}

			Vec2d p;
			Vec2d mtvDirection;
			if (Math.abs(verticalDist) < Math.abs(horizontalDist)) {
				p = new Vec2d(s2.getGamePosition().x,
						s2.getGamePosition().y + verticalDist);
				mtvDirection = new Vec2d(0, 1);
			} else {
				p = new Vec2d(s2.getGamePosition().x + horizontalDist,
						s2.getGamePosition().y);
				mtvDirection = new Vec2d(-1, 0);
			}
			double mtvLength = s2.getRadius() + p.dist(s2.getGamePosition());

			return mtvDirection.smult(mtvLength);
		} else {
			boolean clTop = false, clBottom = false, clRight = false,
					clLeft = false;
			double verticalDist;
			if (Math.abs(distanceToTop) < Math.abs(distanceToBottom)) {
				clTop = true;
				verticalDist = distanceToTop;
			} else {
				clBottom = true;
				verticalDist = distanceToBottom;
			}
			double horizontalDist;
			if (Math.abs(distanceToLeft) < Math.abs(distanceToRight)) {
				clLeft = true;
				horizontalDist = distanceToLeft;
			} else {
				clRight = true;
				horizontalDist = distanceToRight;
			}

			if (Math.abs(verticalDist) < Math.abs(horizontalDist)) {
				clRight = false;
				clLeft = false;
			} else {
				clTop = false;
				clBottom = false;
			}

			if ((Math.abs(s1.getGamePosition().y - s2.getGamePosition().y) > s2
					.getRadius()
					&& Math.abs(bottomRight.y - s2.getGamePosition().y) > s2
							.getRadius())
					|| (Math.abs(bottomRight.x - s2.getGamePosition().x) > s2
							.getRadius()
							&& Math.abs(s1.getGamePosition().x
									- s2.getGamePosition().x) > s2
											.getRadius())) {
				return null;
			}

			Vec2d clampedPoint = null;
			if (clTop) {
				clampedPoint = new Vec2d(s2.getGamePosition().x,
						s1.getGamePosition().y);
			}
			if (clBottom) {
				clampedPoint = new Vec2d(s2.getGamePosition().x, bottomRight.y);
			}
			if (clRight) {
				clampedPoint = new Vec2d(bottomRight.x, s2.getGamePosition().y);
			}
			if (clLeft) {
				clampedPoint = new Vec2d(s1.getGamePosition().x,
						s2.getGamePosition().y);
			}

			double mtvLength = s2.getRadius()
					- s2.getGamePosition().dist(clampedPoint);
			if (clTop)
				return new Vec2d(0, mtvLength);
			if (clBottom)
				return new Vec2d(0, -mtvLength);
			if (clRight)
				return new Vec2d(-mtvLength, 0);
			if (clLeft)
				return new Vec2d(mtvLength, 0);

		}

		return null;
	}

	public static boolean circleToCircle(ComponentCircle circle1,
			ComponentCircle circle2) {
		double distance = circle1.getGamePosition()
				.dist(circle2.getGamePosition());
		double radiusSum = circle1.getRadius() + circle2.getRadius();
		return distance <= radiusSum;
	}

	// TODO this isn't 100% correct.
	public static Vec2d mtvCircleToCircle(ComponentCircle circle1,
			ComponentCircle circle2) {
		double sum = (circle1.getRadius() + circle2.getRadius())
				- circle1.getGamePosition().dist(circle2.getGamePosition());
		double distance = circle1.getGamePosition()
				.dist2(circle2.getGamePosition());

		Vec2d direction = new Vec2d(
				circle1.getGamePosition().x - circle2.getGamePosition().x,
				circle1.getGamePosition().y - circle2.getGamePosition().y);
		Vec2d mtv = direction.normalize().smult(sum);

		double radiusSum = circle1.getRadius() + circle2.getRadius();
		return distance <= radiusSum * radiusSum ? mtv : null;
	}

	public static boolean pointToCircle(Vec2d point, ComponentCircle circle) {
		return point.dist2(circle.getGamePosition()) <= circle.getRadius()
				* circle.getRadius();
	}

	public static Vec2d mtvPointToCircle(ComponentCircle s1, Vec2f s2) {
		Vec2d centerToPoint = new Vec2d(s2.x - s1.getPosition().x,
				s2.y - s1.getPosition().y);
		double cLength = centerToPoint.mag();
		double toEdge = s1.getRadius() - cLength;
		Vec2d pointToEdge = centerToPoint.normalize().smult(toEdge);
		return centerToPoint.mag() <= s1.getRadius() ? pointToEdge : null;
	}

	public static boolean pointToAABB(Vec2d point, ComponentAABB aabb) {
		return aabb.getGamePosition().x <= point.x
				&& point.x <= aabb.getGamePosition().x + aabb.getSize().x
				&& aabb.getGamePosition().y <= point.y
				&& point.y <= aabb.getGamePosition().y + aabb.getSize().y;
	}

	public static Vec2d mtvPointToAABB(ComponentAABB aabb, Vec2d point) {
		Vec2d bottomRight = aabb.getGamePosition().plus(aabb.getSize());
		if (point.x < aabb.getGamePosition().x
				|| point.y < aabb.getGamePosition().y || point.x > bottomRight.x
				|| point.y > bottomRight.y) {
			return null;
		}

		Vec2d up = new Vec2d(0, aabb.getGamePosition().y - point.y);
		Vec2d down = new Vec2d(0, bottomRight.y - point.y);
		Vec2d right = new Vec2d(bottomRight.x - point.x, 0);
		Vec2d left = new Vec2d(aabb.getGamePosition().x - point.x, 0);

		double min = Math.min(Math.abs(up.y), Math.min(Math.abs(down.y),
				Math.min(Math.abs(right.x), Math.abs(left.x))));
		if (Math.abs(up.y) <= min)
			return up;
		if (Math.abs(down.y) <= min)
			return down;
		if (Math.abs(right.x) <= min)
			return right;
		if (Math.abs(left.x) <= min)
			return left;

		return null;
	}
}

package parosenb.engine.collision;

import java.awt.Shape;
import java.util.ArrayList;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.CollisionObject;
import cs1971.Vec2f;

public class Ray extends CollisionObject {
	
	public final Vec2f position;
	public final Vec2f direction;
	public final PhysicsEntity source;
	
	public Ray (Vec2f position, Vec2f direction, long collisionGroupMask, long rayCastingGroupMask, PhysicsEntity source){
		this.direction = direction.normalized();
		this.position = position;
		this.collisionGroupMask = collisionGroupMask;
		this.rayCastingGroupMask = rayCastingGroupMask;
		this.source = source;
		
	}

	@Override
	public Vec2f collides(CollisionObject o) {
		return o.collides(this);
	}

	@Override
	public Vec2f collidesCircle(Circle c) {
		float t = (c.center.minus(this.position)).dot(this.direction);
		if (!c.contains(this.position)) {
			if (t >= 0 && c.contains(this.position.plus(this.direction.smult(t)))){
				float length = this.position.dist(this.direction.smult(t).plus(this.position));
				float x = c.center.dist(this.direction.smult(t).plus(this.position));
				return this.position.plus(this.direction.smult((float) (length-Math.sqrt(c.radius*c.radius - x*x))));
			}
		} else if (c.contains(this.position)) {
			float length = this.position.dist(this.direction.smult(t).plus(this.position));
			float x = c.center.dist(this.direction.smult(t).plus(this.position));
			return this.position.plus(this.direction.smult((float) (length+Math.sqrt(c.radius*c.radius - x*x))));
		} 
		return null;
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		return this.collidesPolygon(aab.aabToPoly(aab));
	}

	@Override
	public Vec2f collidesPolygon(Polygon p) {
		Vec2f closestPointToRay = null;
		Vec2f tempStartPoint = p.startPoint;
		for (Vec2f v : p.vectorList) {
			Vec2f a = tempStartPoint;
			Vec2f b = tempStartPoint.plus(v);
			Vec2f m = v.normalized();
			Vec2f n = new Vec2f(-m.y, m.x);
			if ((a.minus(this.position).cross(this.direction) * b.minus(this.position).cross(this.direction) > 0)) {
				//do nothing
			} else {
				float t = (b.minus(this.position).dot(n))/(this.direction.dot(n));
				Vec2f q = this.position.plus(this.direction.smult(t));
				if (t > 0 && closestPointToRay == null){
					closestPointToRay = q;
				} else if (t > 0 && closestPointToRay.dist2(this.position) > (q.dist2(this.position))) {
					closestPointToRay = q;
				}
			}
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		return closestPointToRay;
	}
}
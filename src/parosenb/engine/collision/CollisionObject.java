package parosenb.engine.collision;

import cs1971.Vec2f;

public abstract class CollisionObject {
	public long collisionGroupMask;
	public long rayCastingGroupMask;
	
	public CollisionObject(){
		collisionGroupMask = 0;
		rayCastingGroupMask = 0;
	}
	
	public abstract Vec2f collides(CollisionObject o);
	public abstract Vec2f collidesCircle(Circle c);
	public abstract Vec2f collidesAAB(AAB aab);
	public abstract Vec2f collidesPolygon(Polygon p);
}

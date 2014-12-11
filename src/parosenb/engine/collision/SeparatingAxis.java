package parosenb.engine.collision;

import java.util.ArrayList;

import cs1971.Vec2f;

public class SeparatingAxis {
	Vec2f axis;
	public SeparatingAxis(Vec2f direction){
		this.axis = new Vec2f(-direction.y, direction.x);
		this.axis = this.axis.normalized();
		
	}
	
	public Range project(Circle c) {
		float min = c.center.dot(axis) - c.radius;
		float max = c.center.dot(axis) + c.radius;
		return new Range(min, max);
		
	}
	public Range project(AAB a) {
		float min = Float.POSITIVE_INFINITY;
		float max = Float.NEGATIVE_INFINITY;
		ArrayList<Vec2f> vectorList = new ArrayList<Vec2f>();
		vectorList.add(new Vec2f(a.min.x, a.max.y));
		vectorList.add(new Vec2f(a.max.x, a.max.y));
		vectorList.add(new Vec2f(a.max.x, a.min.y));
		vectorList.add(new Vec2f(a.min.x, a.min.y));
		for (Vec2f v : vectorList) {
			if (min > (v.dot(axis))){
				min = v.dot(axis);
			} if (max < (v.dot(axis))){
				max = v.dot(axis);
			}
		}
		return new Range(min, max);
	}
	public Range project(Polygon p) {
		Vec2f tempStartPoint = p.startPoint;
		float min = Float.POSITIVE_INFINITY;
		float max = Float.NEGATIVE_INFINITY;
		for (Vec2f v : p.vectorList) {
			if (min > (tempStartPoint.dot(axis))){
				min = tempStartPoint.dot(axis);
				
			}
			if (max < (tempStartPoint.dot(axis))){
				max = tempStartPoint.dot(axis);
			}
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		return new Range(min, max);
		
	}
}
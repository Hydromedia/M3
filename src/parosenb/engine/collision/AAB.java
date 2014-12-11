package parosenb.engine.collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import cs1971.Vec2f;

public class AAB extends Shape {
	private Vec2f subPosition;
	public Vec2f min;
	Vec2f max;
	public Vec2f size;
	
	public AAB(Vec2f subPosition, Vec2f min, Vec2f size){
		this.subPosition = subPosition;
		this.min = min;
		this.size = size;
		this.max = new Vec2f(min.x+size.x, min.y+size.y);
	}
	
	@Override
	public Vec2f collides(CollisionObject o) {
		return o.collidesAAB(this);
	}
	
	private float clamp(float x, float min, float max){
		if (x < min){
			x = min;
		} else if (x > max) {
			x = max;
		}
		return x;
	}
	
	@Override
	public Vec2f collidesCircle(Circle c) {
		//If the box contains the circle center
		if (this.contains(c.center)){
			Float minMagnitude = Float.POSITIVE_INFINITY;
			Vec2f mtv = null;
			Vec2f xAxis = new Vec2f(1, 0);
			Vec2f yAxis = new Vec2f(0, -1);
			SeparatingAxis x = new SeparatingAxis(xAxis);
			SeparatingAxis y = new SeparatingAxis(yAxis);
			Range thisRange = x.project(this);
			Range aabRange = x.project(c);
			Float mtv1d = Range.rangeMTV(thisRange, aabRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-xAxis.normalized().y, xAxis.normalized().x).smult(mtv1d);
			}
			thisRange = y.project(this);
			aabRange = y.project(c);
			mtv1d = Range.rangeMTV(thisRange, aabRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-yAxis.normalized().y, yAxis.normalized().x).smult(mtv1d);
			}
			return mtv;
		//Otherwise
		} else {
			Vec2f closestPoint = new Vec2f(clamp(c.center.x, this.min.x, this.max.x), clamp(c.center.y, this.min.y, this.max.y));
			//System.out.println(c.radius - c.center.dist(closestPoint));
			float mag = c.center.dist(closestPoint);
			if (c.radius < mag) {
				return null;
			} else {
				return closestPoint.minus(c.center).normalized().smult(c.radius - mag);
			}
			
		}
	}
	
	public Polygon aabToPoly(AAB aab){
		ArrayList<Vec2f> vecs = new ArrayList<Vec2f>();
		float width = max.x-min.x;
		float height = max.y-min.y;
		vecs.add(new Vec2f(0, -height));
		vecs.add(new Vec2f(-width, 0));
		vecs.add(new Vec2f(0, height));
		vecs.add(new Vec2f(width, 0));
		Polygon poly = new Polygon(new Vec2f(0,0), max, vecs);
		return poly;
	}
	
	@Override
	public Vec2f collidesAAB(AAB b) {
		Float minMagnitude = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;
		Vec2f xAxis = new Vec2f(1, 0);
		Vec2f yAxis = new Vec2f(0, -1);
		SeparatingAxis x = new SeparatingAxis(xAxis);
		SeparatingAxis y = new SeparatingAxis(yAxis);
		Range thisRange = x.project(this);
		Range aabRange = x.project(b);
		Float mtv1d = Range.rangeMTV(thisRange, aabRange);
		if (mtv1d == null){
			return null;
		}
		else if (Math.abs(mtv1d) < minMagnitude){
			 minMagnitude = Math.abs(mtv1d);
			 mtv = new Vec2f(-xAxis.normalized().y, xAxis.normalized().x).smult(mtv1d);
		}
		thisRange = y.project(this);
		aabRange = y.project(b);
		mtv1d = Range.rangeMTV(thisRange, aabRange);
		if (mtv1d == null){
			return null;
		}
		else if (Math.abs(mtv1d) < minMagnitude){
			 minMagnitude = Math.abs(mtv1d);
			 mtv = new Vec2f(-yAxis.normalized().y, yAxis.normalized().x).smult(mtv1d);
		}
		return mtv;
	}

	@Override
	public boolean contains(Vec2f point) {
		if (min.x <= point.x && point.x <= max.x
			&& min.y <= point.y && point.y <= max.y) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ArrayList<java.awt.Shape> getShape(Vec2f position) {
//		ArrayList<java.awt.Shape> shapes = new ArrayList<java.awt.Shape>();
//		Rectangle2D rekt = new Rectangle((int) position.x, (int)position.y, (int)size.x, (int)size.y);
//		shapes.add(rekt);
//		return shapes;
		Polygon p = this.aabToPoly(this);
		ArrayList<java.awt.Shape> shapes = new ArrayList<java.awt.Shape>();
		Path2D.Float path = new Path2D.Float();
		path.moveTo(p.startPoint.x, p.startPoint.y);
		Vec2f tempStartPoint = p.startPoint;
		for (Vec2f v : p.vectorList) {
			path.lineTo(tempStartPoint.x + v.x, tempStartPoint.y + v.y);
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		shapes.add(path);
		return shapes;
	}

	@Override
	public void updatePosition(Vec2f position) {
		min = position.plus(subPosition);
		max = new Vec2f(min.x + size.x, min.y+size.y);
	}

	@Override
	public Vec2f collidesPolygon(Polygon p) {
		Vec2f thing = p.collidesAAB(this);
		if (thing != null){
			return new Vec2f(-thing.x, -thing.y);
		} else {
			return null;
		}
	}

}

package parosenb.engine.collision;

import java.awt.geom.Path2D;
import java.util.ArrayList;

import cs1971.Vec2f;
import cs1971.Vec2i;

public class Polygon extends Shape {
	private Vec2f subPosition;
	ArrayList<Vec2f> vectorList = new ArrayList<Vec2f>();
	Vec2f startPoint;
	
	public Polygon(Vec2f subPosition, Vec2f startPoint, ArrayList<Vec2f> vectorList) {
		this.subPosition = subPosition;
		this.startPoint = startPoint;
		this.vectorList = vectorList;
	}
	@Override
	public Vec2f collides(CollisionObject o) {
		return o.collidesPolygon(this);
	}
	
	@Override
	public Vec2f collidesCircle(Circle c) {
		Vec2f tempStartPoint = startPoint;
		Float minMagnitude = Float.POSITIVE_INFINITY;
		Vec2f mtv = null;
		for (Vec2f v : vectorList) {
			SeparatingAxis a = new SeparatingAxis(v.normalized());
			Range thisRange = a.project(this);
			Range circleRange = a.project(c);
			Float mtv1d = Range.rangeMTV(thisRange, circleRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-v.normalized().y, v.normalized().x).smult(mtv1d);
			}
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		//Separating Axis perpendicularizes the axis it's given by doing (-direction.y, direction.x)
		//So we need to do that here so it reverses and uses the correct separating axis.
		Vec2f perpendicularAxis = new Vec2f(0, 0);
		float min = Float.MAX_VALUE;
		tempStartPoint = startPoint;
		for (Vec2f v : vectorList){
			Vec2f toUse = tempStartPoint;
			if (toUse.dist(c.center) < min){
				min = toUse.dist(c.center);
				perpendicularAxis = new Vec2f(-(toUse.minus(c.center).y), toUse.minus(c.center).x);
			}
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		SeparatingAxis a2 = new SeparatingAxis(perpendicularAxis.normalized());
		Range thisRange = a2.project(this);
		Range circleRange = a2.project(c);
		Float mtv1d = Range.rangeMTV(thisRange, circleRange);
		if (mtv1d == null){
			return null;
		}
		else if (Math.abs(mtv1d) < minMagnitude){
			 minMagnitude = Math.abs(mtv1d);
			 mtv = new Vec2f(-perpendicularAxis.normalized().y, perpendicularAxis.normalized().x).smult(mtv1d);
		}
		return mtv;
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		Vec2f mtv = null;
		Vec2f tempStartPoint = startPoint;
		Float minMagnitude = Float.POSITIVE_INFINITY;
		for (Vec2f v : vectorList) {
			SeparatingAxis a = new SeparatingAxis(v.normalized());
			Range vRange = a.project(this);
			Range pRange = a.project(aab);
			Float mtv1d = Range.rangeMTV(vRange, pRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-v.normalized().y, v.normalized().x).smult(mtv1d);
				 //System.out.println(this.startPoint);
			} 

			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		Vec2f xAxis = new Vec2f(1, 0);
		Vec2f yAxis = new Vec2f(0, -1);
		SeparatingAxis x = new SeparatingAxis(xAxis);
		SeparatingAxis y = new SeparatingAxis(yAxis);
		Range thisRange = x.project(this);
		Range aabRange = x.project(aab);
		Float mtv1d = Range.rangeMTV(thisRange, aabRange);
		if (mtv1d == null){
			return null;
		}
		else if (Math.abs(mtv1d) < minMagnitude){
			 minMagnitude = Math.abs(mtv1d);
			 mtv = new Vec2f(-xAxis.normalized().y, xAxis.normalized().x).smult(mtv1d);
		}
		thisRange = y.project(this);
		aabRange = y.project(aab);
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
	public Vec2f collidesPolygon(Polygon p) {
		Vec2f mtv = null;
		Vec2f tempStartPoint = startPoint;
		Float minMagnitude = Float.POSITIVE_INFINITY;
		for (Vec2f v : vectorList) {
			SeparatingAxis a = new SeparatingAxis(v.normalized());
			Range vRange = a.project(this);
			Range pRange = a.project(p);
			Float mtv1d = Range.rangeMTV(vRange, pRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-v.normalized().y, v.normalized().x).smult(mtv1d);
				 //System.out.println(this.startPoint);
			} 

			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		//tempStartPoint = p.startPoint;
		for (Vec2f pVec : p.vectorList) {
			SeparatingAxis a = new SeparatingAxis(pVec.normalized());
			Range vRange = a.project(this);
			Range pRange = a.project(p);
			Float mtv1d = Range.rangeMTV(vRange, pRange);
			if (mtv1d == null){
				return null;
			}
			else if (Math.abs(mtv1d) < minMagnitude){
				 minMagnitude = Math.abs(mtv1d);
				 mtv = new Vec2f(-pVec.normalized().y, pVec.normalized().x).smult(mtv1d);
				 
			} 
			tempStartPoint = new Vec2f(pVec.x + tempStartPoint.x, pVec.y + tempStartPoint.y);
		}
		return mtv;
	}
	
	@Override
	public boolean contains(Vec2f point) {
		Vec2f tempStartPoint = startPoint;
		for (Vec2f v : vectorList) {
			Vec2f vectorToInputPoint = new Vec2f(point.x - tempStartPoint.x, point.y - tempStartPoint.y);
			
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
			if (v.cross(vectorToInputPoint) > 0) {
				return false;
			}
		}
		return true;
//		Vec2f vectorToInputPoint = new Vec2f(point.x - tempStartPoint.x, point.y - tempStartPoint.y);
//		if (v.cross(vectorToInputPoint) > 0) {
//			return true;
//		} else {
//			return false;
//		}
	}

	@Override
	public ArrayList<java.awt.Shape> getShape(Vec2f position) {
		ArrayList<java.awt.Shape> shapes = new ArrayList<java.awt.Shape>();
		Path2D.Float path = new Path2D.Float();
		path.moveTo(startPoint.x, startPoint.y);
		Vec2f tempStartPoint = startPoint;
		for (Vec2f v : vectorList) {
			path.lineTo(tempStartPoint.x + v.x, tempStartPoint.y + v.y);
			tempStartPoint = new Vec2f(v.x + tempStartPoint.x, v.y + tempStartPoint.y);
		}
		shapes.add(path);
		return shapes;
	}

	@Override
	public void updatePosition(Vec2f position) {
		this.startPoint = new Vec2f(position.x,  position.y).plus(subPosition);
	}

}

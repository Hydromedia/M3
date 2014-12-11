package parosenb.engine.collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import cs1971.Vec2f;

public class Circle extends Shape {
	private Vec2f subPosition;
	protected float radius;
	protected Vec2f center;
	
	public float getRadius(){
		return radius;
	}
	
	public Vec2f getCenter(){
		return center;
	}
	
	public Circle(Vec2f subPosition, float radius, Vec2f center){
		this.subPosition = subPosition;
		this.radius = radius;
		this.center = center;
	}
	
	public Vec2f collides(CollisionObject o) {
		return o.collidesCircle(this);
	}
	
	public Vec2f collidesCircle(Circle c) {
		float dist = this.center.dist(c.center);
		if (this.radius + c.radius > dist) {
			return this.center.minus(c.center).normalized().smult((this.radius + c.radius - dist));
		} else {
			return null;
		}
		
	}
	
	public boolean contains(Vec2f point){
		float dx = Math.abs(point.x - center.x);
		float dy = Math.abs(point.y - center.y);
		float distance = (float) Math.sqrt(dx*dx + dy*dy);
		distance = distance * distance;
		if (distance <= (radius*radius)){
			return true;
		} else {
			return false;
		}
	}
	
	private float clamp(float x, float min, float max){
		if (x < min){
			x = min;
		} else if (x > max) {
			x = max;
		}
		return x;
	}
	
	public Vec2f collidesAAB(AAB aab) {
		Vec2f thing = aab.collidesCircle(this);
		if (thing != null) {
			return new Vec2f(-thing.x, -thing.y);
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<java.awt.Shape> getShape(Vec2f position) {
		ArrayList<java.awt.Shape> shapes = new ArrayList<java.awt.Shape>();
		Ellipse2D.Float el = new Ellipse2D.Float(
												position.x, 
												position.y, 
												2*radius, 
												2*radius);
		shapes.add(el);
		return shapes;
	}

	@Override
	public void updatePosition(Vec2f position) {
		this.center = new Vec2f(position.x + radius, position.y + radius).plus(subPosition);
	}

	@Override
	public Vec2f collidesPolygon(Polygon p) {
		Vec2f thing = p.collidesCircle(this);
		if (thing != null) {
			return new Vec2f(-thing.x, -thing.y);
		} else {
			return null;
		}
	}
}

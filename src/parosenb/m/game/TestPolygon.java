package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Shape;

public class TestPolygon extends TestShape {
	public Color color;
	private CollisionTestScreen screen;
	
	public TestPolygon(Vec2f position, Color color, CollisionTestScreen screen){
		ArrayList<Vec2f> vectors = new ArrayList<Vec2f>();
		vectors.add(new Vec2f(50, -50));
		vectors.add(new Vec2f(-50, -50));
		vectors.add(new Vec2f(-50, 50));
		vectors.add(new Vec2f(50, 50));
		this.collisionShape = new Polygon(new Vec2f(0, 0), position, vectors);
		this.position = position;
		this.color = color;
		this.screen = screen;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		screen.collides(this);
//		if (screen.collides(this)){
//			this.color = Color.RED;
//		} else {
//			this.color = Color.BLACK;
//		}
	}

	@Override
	public void onDraw(Graphics2D g) {
		java.awt.Shape drawPoly = collisionShape.getShape(this.position).get(0);
		g.setColor(color);
		g.fill(drawPoly);
		if (isSelected){
			BasicStroke s = new BasicStroke((float) 1);
			g.setStroke(s);
			g.setColor(Color.CYAN);
		}
		g.draw(drawPoly);
	}
	@Override
	public void onResize(Vec2i newSize){
		
	}
}

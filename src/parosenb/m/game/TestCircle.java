package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Shape;

public class TestCircle extends TestShape {
	public Color color;
	private CollisionTestScreen screen;
	
	public TestCircle(float radius, Vec2f position, Color color, CollisionTestScreen screen){
		this.collisionShape = new Circle(new Vec2f(0, 0), radius, new Vec2f(position.x + radius, position.y + radius));
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
		java.awt.Shape drawCircle = collisionShape.getShape(this.position).get(0);
		g.setColor(color);
		g.fill(drawCircle);
		if (isSelected){
			BasicStroke s = new BasicStroke((float) 1);
			g.setStroke(s);
			g.setColor(Color.CYAN);
		}
		g.draw(drawCircle);
	}
	@Override
	public void onResize(Vec2i newSize){
		
	}

}

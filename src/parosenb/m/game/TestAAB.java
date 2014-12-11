package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Shape;

public class TestAAB extends TestShape {
	public Color color;
	private CollisionTestScreen screen;
	
	public TestAAB(Vec2f size, Vec2f position, Color color, CollisionTestScreen screen){
		this.collisionShape = new AAB(new Vec2f(0, 0), position, size);
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
		java.awt.Shape drawRect = collisionShape.getShape(this.position).get(0);
		g.setColor(color);
		g.fill(drawRect);
		if (isSelected){
			BasicStroke s = new BasicStroke((float) 1);
			g.setStroke(s);
			g.setColor(Color.CYAN);
		}
		g.draw(drawRect);
	}
	@Override
	public void onResize(Vec2i newSize){
		
	}
}

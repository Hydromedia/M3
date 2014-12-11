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

public class DiamondChar extends GameEntity {
	public Color color;
	
	public DiamondChar(Vec2f position, float size, Color color, GameWorld world){
		ArrayList<Vec2f> vectors = new ArrayList<Vec2f>();
		vectors.add(new Vec2f(size, -size));
		vectors.add(new Vec2f(-size, -size));
		vectors.add(new Vec2f(-size, size));
		vectors.add(new Vec2f(size, size));
		this.collisionShape = new Polygon(new Vec2f(0, 0), position, vectors);
		this.position = position;
		this.color = color;
		super.world = world;
		world.addEntity(this);
		world.gameEntities.add(this);
		world.addPhysicsEntity(this);
		this.restitution = .5f;
	}

	@Override
	public void onDraw(Graphics2D g) {
		java.awt.Shape drawAab = collisionShape.getShape(this.position).get(0);
		g.setColor(color);
		g.fill(drawAab);
	}
}

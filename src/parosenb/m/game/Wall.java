package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Map;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Shape;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class Wall extends GameEntity {
	
	public Wall(Vec2f p, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(p, w, name, s, properties);
		this.isMoveable = false;
		this.position = position;
		this.restitution = restitution;
	}

	@Override
	public void onDraw(Graphics2D g) {
		java.awt.Shape drawRect = collisionShape.getShape(this.position).get(0);
		g.setColor(Color.blue);
		g.fill(drawRect);
		g.draw(drawRect);
	}

}

package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Shape;

public class AABChar extends GameEntity {
	
	//Vec2f size, Vec2f position, GameWorld world
	public AABChar(Vec2f position, World w, String name, ArrayList<Shape> s, Map<String, String> properties){
		super(position, w, name, s, properties);
		//this.restitution = Float.parseFloat(properties.get("restitution"));
		this.collisionShape = s.get(0);
		super.world = world;
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

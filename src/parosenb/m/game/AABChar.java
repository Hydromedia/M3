package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Shape;

public class AABChar extends GameEntity {
	public Color color;
	
	//Vec2f size, Vec2f position, GameWorld world
	public AABChar(String name, ArrayList<Shape> s, HashMap<String, String> properties){
		super(name, s, properties);
		//this.collisionShape = new AAB(new Vec2f(0, 0), position, size);
		this.position = position;
		this.color = Color.PINK;
		super.world = world;
		world.addEntity(this);
		//world.gameEntities.add(this);
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

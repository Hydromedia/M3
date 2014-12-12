package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Shape;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class CircleChar extends GameEntity {
	public Color color;
	private GameWorld world;
	
	public CircleChar(Vec2f position, World w, String name, ArrayList<Shape> s, Map<String, String> properties){
		super(position, w, name, s, properties);
	
	//(float radius, Vec2f position, Color color, GameWorld world){
		//this.collisionShape = new Circle(new Vec2f(0, 0), radius, new Vec2f(position.x + radius, position.y + radius));
		this.position = position;
		this.color = color;
		this.world = world;
		super.world = world;
		world.addEntity(this);
		world.addPhysicsEntity(this);
		this.restitution = .5f;
	}

	@Override
	public void onDraw(Graphics2D g) {
		java.awt.Shape drawCircle = collisionShape.getShape(this.position).get(0);
		g.setColor(Color.RED);
		g.fill(drawCircle);
	}
}

package parosenb.m.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Shape;

public class PlayerUnit extends GameEntity {
	Vec2f worldBounds;
	private Color color;
	private GameWorld world;
	
	public PlayerUnit(String name, ArrayList<Shape> s, HashMap<String, String> properties){
		super(name, s, properties);
	
		//(Vec2f startPosition, Vec2f worldBounds, GameWorld world) {
		this.world = world;
		super.world = world;
		this.color = Color.green;
		this.worldBounds = worldBounds;
		//this.position = startPosition;
		ArrayList<Vec2f> vectors = new ArrayList<Vec2f>();
		vectors.add(new Vec2f(16, -16));
		vectors.add(new Vec2f(-16, -16));
		vectors.add(new Vec2f(-16, 16));
		vectors.add(new Vec2f(16, 16));
		this.collisionShape = new Polygon(new Vec2f(11f, 32), position, vectors);
		world.addEntity(this);
		world.gameEntities.add(this);
		world.addPhysicsEntity(this);
		this.setMass(2f);
		this.collisionShape.collisionGroupMask = 2;
		this.collisionShape.rayCastingGroupMask = 2;
		this.restitution = 1;
	}
	
	@Override
	public void onDraw(Graphics2D g) {
	java.awt.Shape drawCircle = collisionShape.getShape(this.position).get(0);
			g.setColor(color);
			g.fill(drawCircle);
		}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		float millisecondsSincePreviousTick = nanosSincePreviousTick / 1000000;
		//System.out.println("PlayerUnit vel: " + this.velocity + " pos: " + this.position);
	}
}
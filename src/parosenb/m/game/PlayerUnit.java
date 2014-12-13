package parosenb.m.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Shape;

public class PlayerUnit extends GameEntity {
	private GameWorld world;
	
	public PlayerUnit(Vec2f position, World w, String name, ArrayList<Shape> s, Map<String, String> properties){
		super(position, w, name, s, properties);
		super.world = world;
		
		this.collisionShape = s.get(0);
		this.setMass(2f);
		this.collisionShape.collisionGroupMask = 2;
		this.collisionShape.rayCastingGroupMask = 2;
		this.restitution = 1;
		//world.addPhysicsEntity(this);
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
		//this.setLastMTV(lastMTV);
		//System.out.println("PlayerUnit vel: " + this.velocity + " pos: " + this.position);
	}
}
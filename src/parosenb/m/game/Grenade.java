package parosenb.m.game;

import java.awt.Color;
import java.awt.Graphics2D;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Ray;
import cs1971.Vec2f;

public class Grenade extends GameEntity {
	public Color color;
	private GameWorld world;
	public final int range = 200;
	
	public Grenade(float radius, Vec2f position, Color color, GameWorld world, Vec2f initDirection){
		this.collisionShape = new Circle(new Vec2f(0, 0), radius, new Vec2f(position.x + radius, position.y + radius));
		this.position = position;
		this.color = color;
		this.world = world;
		super.world = world;
		world.addEntity(this);
		world.gameEntities.add(this);
		world.addPhysicsEntity(this);
		this.collisionShape.collisionGroupMask = 2;
		this.applyImpulse(initDirection);
	}
	
	int counter = 0;
	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
	}
	@Override
	public void onDraw(Graphics2D g) {
		java.awt.Shape drawCircle = collisionShape.getShape(this.position).get(0);
		g.setColor(Color.RED);
		g.fill(drawCircle);
	}
	public void explode() {
		 world.removeEntity(this);
		 world.removePhysicsEntity(this);
		 for (PhysicsEntity e : world.getPhysicsEntities()) {
			 if (e != this){
				 Vec2f toUse = null;
				 if (e.collisionShape instanceof Circle){
					 Circle c = (Circle) e.collisionShape;
					 toUse = c.getCenter();
				 } else if (e.collisionShape instanceof Polygon) {
					 toUse = e.position;
				 } else if (e.collisionShape instanceof AAB) {
					 toUse = e.position;
				 }
				 Ray r = new Ray(this.position, (e.position).minus(this.position), 2, 2, this);
				 world.rays.add(r);
			 }
		 }
	}
}

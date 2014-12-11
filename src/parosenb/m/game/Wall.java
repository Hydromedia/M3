package parosenb.m.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.AAB;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class Wall extends GameEntity {
	private CollisionTestScreen screen;
	
	public Wall(Vec2f position, Vec2f size, GameWorld world, float restitution){
		this.isMoveable = false;
		this.collisionShape = new AAB(new Vec2f(0, 0), position, size);
		this.position = position;
		world.addEntity(this);
		world.gameEntities.add(this);
		world.addPhysicsEntity(this);
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

package parosenb.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import parosenb.engine.collision.Shape;
import cs1971.Vec2f;
import cs1971.Vec2i;

public abstract class Entity {
	public Vec2f position;
	public Shape collisionShape;
	public World world;
	public final String name;
	
	public Entity(String name, ArrayList<Shape> s, HashMap<String, String> properties) {
		collisionShape = s.get(0);
		this.name = name;
	}
	public abstract void onTick(long nanosSincePreviousTick);
	
	public abstract void onDraw(Graphics2D g);

	public void onResize(Vec2i newSize) {
		this.position = new Vec2f(newSize.x*(position.x/world.worldBounds.x), newSize.y*(position.y/world.worldBounds.y));
	}
}

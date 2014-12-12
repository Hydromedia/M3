package parosenb.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parosenb.engine.IO.Input;
import parosenb.engine.IO.Output;
import parosenb.engine.collision.Shape;
import cs1971.Vec2f;
import cs1971.Vec2i;

public abstract class Entity {
	public Vec2f position;
	public Shape collisionShape;
	public World world;
	public final String name;
	protected HashMap<String, Output> namesToOutputs;
	protected HashMap<String, Input> namesToInputs;
	
	public Entity(Vec2f position, World w, String name, ArrayList<Shape> s, Map<String, String> properties) {
		collisionShape = s.get(0);
		this.world = w;
		w.addEntity(this);
		this.name = name;
		this.position = position;
	}
	public abstract void onTick(long nanosSincePreviousTick);
	
	public abstract void onDraw(Graphics2D g);

	public void onResize(Vec2i newSize) {
		this.position = new Vec2f(newSize.x*(position.x/world.worldBounds.x), newSize.y*(position.y/world.worldBounds.y));
	}
	public Output getOutput(String sourceOutput) {
		return namesToOutputs.get(sourceOutput);
	}
	public Input getInput(String targetInput) {
		return namesToInputs.get(targetInput);
	}
}

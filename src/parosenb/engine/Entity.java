package parosenb.engine;

import java.awt.Color;
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
	protected final Color color;
	protected HashMap<String, Output> namesToOutputs = new HashMap<String, Output>();
	protected HashMap<String, Input> namesToInputs = new HashMap<String, Input>();
	protected boolean visible = true;
	
	
	public Entity(Vec2f position, World w, String name, ArrayList<Shape> s, Map<String, String> properties) {
		if (s.size() >=1){
			collisionShape = s.get(0);
		}
		this.world = w;
		w.addEntity(this);
		this.name = name;
		this.position = position;
		
		if (properties.get("hasColor")!=null){
			int red = Integer.parseInt(properties.get("red"));
			int blue = Integer.parseInt(properties.get("green"));
			int green = Integer.parseInt(properties.get("blue"));
			this.color = new Color(red, green, blue);
		} else {
			this.color = Color.black;
		}
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}

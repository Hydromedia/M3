package parosenb.m.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.Shape;

public abstract class GameEntity extends PhysicsEntity {

	public GameEntity(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		// TODO Auto-generated constructor stub
	}
	
}
package parosenb.m.game;

import java.util.ArrayList;
import java.util.HashMap;

import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.collision.Shape;

public abstract class GameEntity extends PhysicsEntity {

	public GameEntity(String name, ArrayList<Shape> s,
			HashMap<String, String> properties) {
		super(name, s, properties);
		// TODO Auto-generated constructor stub
	}
	
}
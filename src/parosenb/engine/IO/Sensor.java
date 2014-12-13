package parosenb.engine.IO;

import java.util.ArrayList;
import java.util.Map;

import cs1971.Vec2f;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.collision.Shape;

public class Sensor extends PhysicsEntity {
	
	private Output onSenseEntity = new Output();
	public Sensor(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		this.visible = false;
		this.doesCollisions = false;
		this.isMoveable = false;
		namesToOutputs.put("onSenseEntity", onSenseEntity);
	}
	
	@Override
	public void onCollide(PhysicsEntity e){
		onSenseEntity.run();
	}
	
	

}

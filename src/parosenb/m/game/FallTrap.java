package parosenb.m.game;

import java.util.ArrayList;
import java.util.Map;

import cs1971.Vec2f;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.IO.Trap;
import parosenb.engine.collision.Shape;

public class FallTrap extends Trap{
	private World wo;
	public FallTrap(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		this.wo = w;
		
	}
	
	@Override
	public void onCollide(PhysicsEntity e){
		if (e instanceof PlayerUnit){
			PlayerUnit p = (PlayerUnit) e;
			p.damage(50);
			wo.physicsToRemove.add(this);
		}
	}
	
}

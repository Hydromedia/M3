package parosenb.engine.collision;

import cs1971.Vec2f;
import parosenb.engine.PhysicsEntity;
import parosenb.m.game.TestShape;

//handles collisions
public class Handler {
	
	public Handler() {
		
	}
	
	public static boolean performCollision(PhysicsEntity s, PhysicsEntity e) {
		if (s != e && e.collisionShape != null) {
			Vec2f mtv = s.collisionShape.collides(e.collisionShape);
			if (mtv != null) {
				Vec2f mtv2 = e.collisionShape.collides(s.collisionShape);
				e.position = e.position.plus(mtv.sdiv(2));
				s.position = s.position.plus(mtv2.sdiv(2));
				
				s.applyImpulse(mtv2.smult(Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))));
				e.applyImpulse(mtv.smult(Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))));
				
				s.velocity = new Vec2f(0,0);
				e.velocity = new Vec2f(0,0);
				return true;
			}
		}
		return false;
	}
}

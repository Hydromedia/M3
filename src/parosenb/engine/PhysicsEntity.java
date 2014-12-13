package parosenb.engine;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parosenb.engine.collision.Shape;
import cs1971.Vec2f;


public abstract class PhysicsEntity extends Entity {
	public PhysicsEntity(Vec2f p, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(p, w, name, s, properties);
		w.addPhysicsEntity(this);
//		this.restitution = Float.parseFloat(properties.get("restitution"));
//		this.position = new Vec2f(Float.parseFloat(properties.get("xPos")),
								 // Float.parseFloat(properties.get("yPos")));
	}

	public Vec2f velocity = new Vec2f(0, 0);
	protected boolean isMoveable = true;
	protected float mass = 1;
	protected Vec2f impulse = new Vec2f(0, 0);
	protected Vec2f force = new Vec2f(0, 0);
	protected float restitution = .5f;
	protected boolean doesCollisions = true;

	private Vec2f lastMTV = new Vec2f(0,0);
	
	public void setLastMTV(Vec2f lastMTV){
		this.lastMTV = lastMTV;
	}
	
	public Vec2f getLastMTV(){
		return lastMTV;
	}
	
	public float getMass(){
		return mass;
	} 
	public void setMass(float mass) {
		this.mass = mass;
	}
	public Vec2f getVel(){
		return velocity;
	}
	
	 public void applyForce(Vec2f f) {
		 force = force.plus(f);
	 }
	 public void applyImpulse(Vec2f p) {
		 impulse = impulse.plus(p);
	 }
	 
	 @Override
	 public void onTick(long nanosSincePreviousTick) {
		 if (isMoveable){
			 float millisecondsSincePreviousTick = nanosSincePreviousTick / 1000000;
			 velocity = velocity.plus(force.x/mass + impulse.x/mass, force.y/mass + impulse.y/mass);
			 position = position.plus(velocity.x*millisecondsSincePreviousTick, velocity.y*millisecondsSincePreviousTick);
			 force = new Vec2f(0, 0);
			 impulse = new Vec2f(0, 0);
			 this.collisionShape.updatePosition(position);
		 }
	 }

	@Override
	public void onDraw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	public void onCollide(PhysicsEntity e) {
		//do nothing
	}
	
	public boolean isMoveable() {
		return isMoveable;
	}

	public void setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
	}
	
	public boolean doesCollisions() {
		return doesCollisions;
	}

	public void setDoesCollisions(boolean doesCollisions) {
		this.doesCollisions = doesCollisions;
	}
}

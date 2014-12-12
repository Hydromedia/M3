package parosenb.engine;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parosenb.engine.IO.Connection;
import parosenb.engine.IO.Input;
import parosenb.engine.IO.Output;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Ray;
import parosenb.engine.collision.Shape;
import parosenb.m.game.GameViewport;
import cs1971.CS1971LevelReader;
import cs1971.CS1971LevelReader.InvalidLevelException;
import cs1971.LevelData;
import cs1971.LevelData.ConnectionData;
import cs1971.LevelData.EntityData;
import cs1971.LevelData.ShapeData;
import cs1971.Vec2f;
import cs1971.Vec2i;

public abstract class World {
	public Vec2f worldBounds;
	protected ArrayList<Entity> entities;
	protected ArrayList<PhysicsEntity> physicsEntities;
	public ArrayList<Entity> toAdd = new ArrayList<Entity>();
	public ArrayList<Entity> toRemove = new ArrayList<Entity>();
	public ArrayList<PhysicsEntity> physicsToRemove = new ArrayList<PhysicsEntity>();
	public ArrayList<Ray> raysToRemove = new ArrayList<Ray>();
	public ArrayList<Ray> rays = new ArrayList<Ray>();
	protected Viewport viewport;
	protected HashMap<String, Entity> namesToEntities = new HashMap<String, Entity>();
	protected HashMap<String, Class<?>> availableEntities = new HashMap<String, Class<?>>();
	
	
	public ArrayList<PhysicsEntity> getPhysicsEntities() {
		return physicsEntities;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e){
		toRemove.add(e);
	}
	
	public void addPhysicsEntity(PhysicsEntity e){
		physicsEntities.add(e);
	}
	
	public void removePhysicsEntity(PhysicsEntity e){
		physicsToRemove.add(e);
	}
	
	public Entity getEntity(int index){
		return entities.get(index);
	}
	
	public abstract <T extends PhysicsEntity> void onCollision(T s, T e);
	
	public void collide() {
		for (int i = 0; i < physicsEntities.size(); i++){
			PhysicsEntity s = physicsEntities.get(i);
			for (int j = i; j < physicsEntities.size(); j++){
				PhysicsEntity e = physicsEntities.get(j);
				shape:
				if (s != e && s.collisionShape != null && e.collisionShape != null &&
						(s.collisionShape.collisionGroupMask & e.collisionShape.collisionGroupMask) == 0) {
					Vec2f mtv = s.collisionShape.collides(e.collisionShape);
					Vec2f mtv2 = e.collisionShape.collides(s.collisionShape);
//					if (mtv2 == null) {
//						System.out.println("2null");
//					} if (mtv == null) {
//						System.out.println("1null");
//					}
					
					if (s.getLastMTV() == null){
						s.setLastMTV((mtv2));
					} else if (mtv2 != null) {
						s.setLastMTV(s.getLastMTV().plus(mtv2));
					} if (e.getLastMTV() == null){
						e.setLastMTV((mtv));
					} else if (mtv != null){
						e.setLastMTV(e.getLastMTV().plus(mtv));
					}
					
					
					
					if (mtv != null) {
						if (s.isMoveable && e.isMoveable) {
							s.position = s.position.plus(mtv2.sdiv(2));
							s.collisionShape.updatePosition(s.position);
							e.position = e.position.plus(mtv.sdiv(2));
							e.collisionShape.updatePosition(e.position);
						} else if (s.isMoveable && !e.isMoveable) {
							s.position = s.position.plus(mtv2);
							s.collisionShape.updatePosition(s.position);
						} else if (e.isMoveable && !s.isMoveable) {
							e.position = e.position.plus(mtv);
							e.collisionShape.updatePosition(e.position);
						}
						float COR = (float) Math.sqrt(s.restitution*e.restitution);
						if (mtv2.mag() == 0 || mtv.mag() == 0){
							break shape;
						}
						Vec2f Us = mtv2.normalized().smult(s.velocity.dot(mtv2.normalized()));
						Vec2f Ue = mtv.normalized().smult(e.velocity.dot(mtv.normalized()));
						//Vec2f Vs = (Ue.minus(Us)).smult((e.mass*(1+COR))/s.mass+e.mass).plus(Us);
						//Vec2f Ve = (Ue.minus(Us)).smult((s.mass*(1+COR))/e.mass+s.mass).plus(Ue);
//						Vec2f Vs = (Us.smult(s.mass).plus(Ue.smult(e.mass)).plus(Ue.minus(Us).smult(e.mass*COR))).sdiv(s.mass+e.mass);
//						Vec2f Ve = (Ue.smult(e.mass).plus(Us.smult(s.mass)).plus(Us.minus(Ue).smult(s.mass*COR))).sdiv(e.mass+s.mass);
						Vec2f Is = new Vec2f(0,0), Ie = new Vec2f(0,0);
						if (s.isMoveable && e.isMoveable){
							Is = (Ue.minus(Us)).smult((s.mass*e.mass*(1+COR))/(s.mass+e.mass));
							Ie = (Us.minus(Ue)).smult((e.mass*s.mass*(1+COR))/(e.mass+s.mass));
						} else if (!s.isMoveable && e.isMoveable) {
//							Is = (Ue.minus(Us)).smult(e.mass*(1+COR));
							Ie = (Us.minus(Ue)).smult(e.mass*(1+COR));
						} else if (s.isMoveable && !e.isMoveable){
							Is = (Ue.minus(Us)).smult(s.mass*(1+COR));
//							Ie = (Us.minus(Ue)).smult(s.mass*(1+COR));
						} else {
							//do nothing
							//System.out.println("Should never happen, immovable object collision");
						}
						s.applyImpulse(Is);
						e.applyImpulse(Ie);
						//s.applyImpulse(mtv2.smult(Math.min((Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))), .2f)));
						//e.applyImpulse(mtv.smult(Math.min((Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))), .2f)));
						onCollision(s, e);
					} 
				}
			}
		}
	}
	
	public abstract <T extends PhysicsEntity> void onRayCollision(Ray r, T s, T e, Vec2f closestPoint);
	
	public void collideRays() {
		for (int i = 0; i < rays.size(); i++){
			Ray r = rays.get(i);
			Vec2f closestPointToRay = null;
			int index = 0;
			for (int j = 0; j < physicsEntities.size(); j++){
				PhysicsEntity e = (physicsEntities.get(j));
				Vec2f newPoint = r.collides(physicsEntities.get(j).collisionShape);
				if (newPoint != null && (r.rayCastingGroupMask & e.collisionShape.rayCastingGroupMask) == 0 && r.source != e) {
					if (closestPointToRay == null) {
						closestPointToRay = newPoint;
						index = j;
					} else if (r.position.dist2(closestPointToRay) > r.position.dist2(newPoint)) {
						closestPointToRay = newPoint;
						index = j;
					}
					
				}
			}
			if (physicsEntities.size() > 0 && r.source != physicsEntities.get(index) && 
					(r.rayCastingGroupMask & physicsEntities.get(index).collisionShape.rayCastingGroupMask) == 0){
				onRayCollision(r, r.source, physicsEntities.get(index), closestPointToRay);
			}
		}
	}
	
	public void initializeWorld(String path){
		//"levels/level1.nlf"
		File file = new File(path);
		LevelData leveld = null;
		try {
			leveld = CS1971LevelReader.readLevel(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (InvalidLevelException e) {
			System.out.println("Invalid level.");
		}
		
		//make Connections
		for(ConnectionData d : leveld.getConnections()){
			Entity source = this.namesToEntities.get(d.getSource());
			Output o = source.getOutput(d.getSourceOutput());
			Entity target = this.namesToEntities.get(d.getTarget());
			Input i = target.getInput(d.getTargetInput());
			Connection c = new Connection(i);
			o.connect(c);
		}
		
		//make Entities
		for(EntityData data : leveld.getEntities()) {
			//Set up shapes
			ArrayList<Shape> shapes = new ArrayList<Shape>();
			Vec2f position = null;
			for (ShapeData s : data.getShapes()) {
				if (s.getType() == ShapeData.Type.POLY){
					java.util.List<Vec2f> list = s.getVerts();
					if (list.size() >= 1){
						ArrayList<Vec2f> vertices = new ArrayList<Vec2f>();
						Vec2f startPoint = list.get(0);
						position = startPoint;
						Vec2f oldVert = startPoint;
						for (int i = 1; i < list.size(); i++) {
							vertices.add(oldVert.minus(list.get(i)));
							oldVert = list.get(i);
						}
						Polygon p = new Polygon(new Vec2f(0,0), startPoint, vertices);
						shapes.add(p);
					} else {
						shapes.add(null);
					}
				} else if (s.getType() == ShapeData.Type.CIRCLE) {
					Circle c = new Circle(new Vec2f(0,0), s.getRadius(), s.getCenter());
					position = s.getMin();
					shapes.add(c);
				} else if (s.getType() == ShapeData.Type.BOX) {
					position = s.getMin();
					AAB aab = new AAB(new Vec2f(0,0), s.getMin(), new Vec2f(s.getWidth(), s.getHeight()));
					shapes.add(aab);
				} else {
					System.out.println("Shape not recognized.");
				}
			}
			Map<String, String> properties = null;
			ArrayList<Shape> sps = new ArrayList<Shape>();
			Object test = null;
			try{
				System.out.println(data.getEntityClass());
				System.out.println(shapes);
				System.out.println(data.getProperties());
				System.out.println(data.getName());
				test = availableEntities.get(
						data
						.getEntityClass())
						.getConstructor(
							Vec2f.class,
							World.class,
							String.class,
							sps.getClass(),
							Map.class)
					.newInstance(position, this, data.getName(), shapes, data.getProperties());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (test instanceof Entity){
				Entity e = (Entity) test;
				entities.add(e);
				this.namesToEntities.put(data.getName(), e);
				
			} else {
				System.out.println("Attempted to add an unsupported entity.");
			}
			
			
		}
		
	}
	
	public void setView(Viewport view){
		this.viewport = view;
	}
	
	public void onTick(long nanosSincePreviousTick){
		for (PhysicsEntity e: this.physicsEntities) {
			e.setLastMTV(null);
		}
		for(Entity e: entities) {
			e.onTick(nanosSincePreviousTick);
		}
		collide();
		collideRays();
		for (Entity e: toAdd) {
			entities.add(e);
		}
		toAdd = new ArrayList<Entity>();
		for (Entity e: toRemove) {
			entities.remove(e);
		}
		for (PhysicsEntity e: physicsToRemove) {
			physicsEntities.remove(e);
		}
		physicsToRemove = new ArrayList<PhysicsEntity>();
		toRemove = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		for (Ray r: raysToRemove) {
			rays.remove(r);
		}
		raysToRemove = new ArrayList<Ray>();
	}
	
	public void onDraw(Graphics2D g){
		for(Entity e: entities){
			e.onDraw(g);
		}
	};
	
	public abstract void onKeyTyped(KeyEvent e, Viewport gameView);

	public abstract void onKeyPressed(KeyEvent e, Viewport gameView);

	public abstract void onKeyReleased(KeyEvent e, Viewport gameView);

	public abstract void onMouseClicked(MouseEvent e, Viewport gameView);

	public abstract void onMousePressed(MouseEvent e, Viewport gameView);

	public abstract void onMouseReleased(MouseEvent e, Viewport gameView);

	public abstract void onMouseDragged(MouseEvent e, Viewport gameView);

	public abstract void onMouseMoved(MouseEvent e, Viewport gameView);

	public abstract void onMouseWheelMoved(MouseWheelEvent e, Viewport gameView);
	
	public void onResize(Vec2i newSize) {
		for(Entity e: entities){
			e.onResize(newSize);
		}
		this.worldBounds = new Vec2f(newSize.x, newSize.y);
	}
	
}

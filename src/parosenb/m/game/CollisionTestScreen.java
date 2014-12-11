package parosenb.m.game;

import parosenb.engine.Application;
import parosenb.engine.Entity;
import parosenb.engine.PhysicsEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.Screen;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Circle;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Shape;

public class CollisionTestScreen  extends Screen {
	ArrayList<TestShape> ents = new ArrayList<TestShape>();
	private Application app;
	private TestShape selected = null;
	ArrayList<Vec2f> MTVstartPoints = new ArrayList<Vec2f>();
	ArrayList<Vec2f> MTVvecs = new ArrayList<Vec2f>();
	
	public CollisionTestScreen(Application app, Vec2i screenVector) {
		this.app = app;
		this.screenVector = screenVector;
		TestCircle circle1 = new TestCircle(50, new Vec2f(screenVector.x/4, screenVector.y/4), Color.BLACK, this);
		TestCircle circle2 = new TestCircle(50, new Vec2f(screenVector.x/4, screenVector.y/2), Color.BLACK, this);
		TestAAB aab1 = new TestAAB(new Vec2f(50, 70), new Vec2f(screenVector.x/2, screenVector.y/4), Color.BLACK, this);
		TestAAB aab2 = new TestAAB(new Vec2f(50, 70), new Vec2f(screenVector.x/2, screenVector.y/2), Color.BLACK, this);
		TestAAB aab3 = new TestAAB(new Vec2f(100, 100),  new Vec2f(100, 50), Color.BLACK, this);
		TestPolygon poly1 = new TestPolygon(new Vec2f(160, 160), Color.BLACK, this);
		TestPolygon poly2 = new TestPolygon(new Vec2f(340, 160), Color.BLACK, this);
		ents.add(circle1);
		ents.add(circle2);
		ents.add(aab1);
		ents.add(aab2);
		ents.add(aab3);
		//ents.add(comp1);
		//ents.add(comp2);
		ents.add(poly1);
		ents.add(poly2);
		//Graphics.drawLine();
	}
	
	public void collides(PhysicsEntity s) {
		for (TestShape e : ents) {
			if (s != e && e.collisionShape != null) {
				Vec2f mtv = s.collisionShape.collides(e.collisionShape);
				if (mtv != null) {
					Vec2f mtv2 = e.collisionShape.collides(s.collisionShape);
					//handle static shapes
					//e.position = e.position.plus(mtv.sdiv(2));
					
					MTVstartPoints.add(s.position);
					MTVvecs.add(s.position.plus(mtv2));
					
					MTVstartPoints.add(e.position);
					MTVvecs.add(e.position.plus(mtv));
//					//s.position = s.position.plus(mtv2.sdiv(2));
//					System.out.println("MTV1: " + mtv + " MTV2: " + mtv2 + " Position1: " + s.position + " Position2: " + e.position);
//					if (s.collisionShape instanceof Circle){
//						System.out.println("1: Cricle");
//					} else if (s.collisionShape instanceof Polygon){
//						System.out.println("1: Polygon");
//					} else if (s.collisionShape instanceof AAB){
//						System.out.println("1: AAB");
//					} if (e.collisionShape instanceof Circle){
//						System.out.println("2: Cricle");
//					} else if (e.collisionShape instanceof Polygon){
//						System.out.println("2: Polygon");
//					} else if (e.collisionShape instanceof AAB){
//						System.out.println("2: AAB");
//					}
					//s.applyImpulse(mtv2.pmult(s.velocity));
					//e.applyImpulse(mtv.pmult(e.velocity));
					
					//System.out.println("impulse1: " + mtv2.smult(Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))) + " impulse2: " + mtv.smult(Math.abs(e.velocity.x) + (Math.abs(e.velocity.y))));
					//System.out.println("oldimpulse1: " + mtv.pmult(s.velocity) + " oldimpulse2: " + mtv.pmult(e.velocity));
					
					//s.applyImpulse(mtv2.smult(Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))));//velocity = new Vec2f(-s.velocity.x*2, -s.velocity.y*2);
					//e.applyImpulse(mtv.smult(Math.abs(s.velocity.x) + (Math.abs(s.velocity.y))));
					
					//s.velocity = new Vec2f(0,0);
					//e.velocity = new Vec2f(0,0);
//					s.applyImpulse(mtv2.smult(Math.min(1f, Math.abs(s.velocity.x + s.velocity.y))));
//					e.applyImpulse(mtv.smult(Math.min(1f, Math.abs(s.velocity.x + s.velocity.y))));
					//return true;
				}
			}
		}
		//return false;
	}
	
	@Override
	public void screenTick(long nanosSincePreviousTick) {
		float millisecondsSincePreviousTick = nanosSincePreviousTick / 1000000;
		for(TestShape s : ents){
			s.onTick(nanosSincePreviousTick);
		}
		if (selected != null){
			//selected.updatePosition(millisecondsSincePreviousTick);
		}
	}

	@Override
	public void screenDraw(Graphics2D g) {
		for (TestShape s : ents){
			s.onDraw(g);
		}
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(2f));
		System.out.println(MTVstartPoints.size());
		for (int i = 0 ; i < MTVstartPoints.size(); i++){
			g.drawLine((int) MTVstartPoints.get(i).x, (int)MTVstartPoints.get(i).y,
					(int)MTVvecs.get(i).x,(int) MTVvecs.get(i).y);
		}
		MTVstartPoints = new ArrayList<Vec2f>();
		MTVvecs = new ArrayList<Vec2f>();
	}

	@Override
	public void screenKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenKeyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			app.delScreen();
			app.addScreen(new MenuScreen(app, screenVector));
		}
		if (selected != null) {
			if (e.getKeyChar() == 'w') {
				selected.applyImpulse(new Vec2f(0, -.03f));
			} else if (e.getKeyChar() == 'a'){
				selected.applyImpulse(new Vec2f(-.03f, 0));
			} else if (e.getKeyChar() == 's'){
				selected.applyImpulse(new Vec2f(0, .03f));
			} else if (e.getKeyChar() == 'd'){
				selected.applyImpulse(new Vec2f(.03f, 0));
			}
		}
	}

	@Override
	public void screenKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseClicked(MouseEvent e) {
		for(TestShape s : ents){
			if(s.collisionShape.contains(new Vec2f(e.getPoint().x, e.getPoint().y))) {
				selected = s;
				s.isSelected = true;
			} else {
				s.isSelected = false;
			}
		}
	}

	@Override
	public void screenMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenResize(Vec2i newSize) {
		screenVector = newSize;
	}

}

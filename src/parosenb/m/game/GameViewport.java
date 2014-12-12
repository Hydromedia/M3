package parosenb.m.game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import cs1971.Vec2i;
import parosenb.engine.*;

public class GameViewport extends Viewport {
	private GameWorld world;
	private PlayerUnit _player;
	public Vec2i test = new Vec2i(0,0);
	public GameViewport(GameWorld world){
		this.setWorld(world);
		amountToTranslate = new Vec2i(0, 0);
		amountToZoom = (float) 1;
		transform = new AffineTransform();
	}
	
	
	public void onDraw(Graphics2D g, Vec2i topLeft, Vec2i screenVector, Vec2i viewSize){
		this.topLeft = topLeft;
		transform = new AffineTransform();
		//g.clipRect(topLeft.x, topLeft.y, viewSize.x, viewSize.y);
		//zoom(amountToZoom, new Vec2i((viewSize.x)/2, (viewSize.y)/2));
		zoom(amountToZoom, test);
		//boundTranslation(amountToTranslate, amountToZoom, screenVector);
		translate(topLeft);
		translate(amountToTranslate);
		g.setTransform(transform);
		world.onDraw(topLeft, g);
		//getWorld().onDraw(g, screenVector, viewSize);
		
		//draw static sized images in world
		transform = new AffineTransform();
		//g.clipRect(0, 0, viewSize.x, viewSize.y);
		//g.fillRect(0, 0, screenVector.x, (4*screenVector.y)/5);
		//zoom(amountToZoom, new Vec2i((viewSize.x)/2, (viewSize.y)/2));
		//translate(amountToTranslate);
		g.setTransform(transform);
		//getWorld().drawStatic(g, screenVector, viewSize, amountToTranslate, amountToZoom);
		
	}
	
//	private void boundTranslation(Vec2i amountToTranslate, float amountToZoom, Vec2i screenVector){
//		int scaleFactor = (int) (amountToZoom + 4 )/4;
//		if (amountToTranslate.x > (400*scaleFactor)) {
//			amountToTranslate = new Vec2i((int) (400*scaleFactor), amountToTranslate.y);
//			//System.out.println("left, " + amountToTranslate.x);
//		}
//		//if (amountToTranslate.x < 0 - (getWorld().getRows()*4) - screenVector.x) {
//			//amountToTranslate = new Vec2i(0 - getWorld().getRows()*4 - screenVector.x, amountToTranslate.y);
//			//System.out.println("right, " + amountToTranslate.x);
//		//}
//		if (amountToTranslate.y > 400*scaleFactor) {
//			//amountToTranslate = new Vec2i(amountToTranslate.x, 400*scaleFactor);
//			//System.out.println("up, " + amountToTranslate.y);
//		}
//		//if (amountToTranslate.y < 0 - getWorld().getCols()*(4) - screenVector.y) {
//			//amountToTranslate = new Vec2i(amountToTranslate.x, 0 - getWorld().getCols()*(4) - screenVector.y);
//			//System.out.println("down, " + amountToTranslate.y);
//		//}
//	}
//	
//	public void viewKeyPressed(KeyEvent e) {
//		if (e.getKeyChar() == 'w') {
//			amountToTranslate = new Vec2i(amountToTranslate.x, amountToTranslate.y+30);
//			//System.out.println("w");
//		} else if (e.getKeyChar() == 'a') {
//			amountToTranslate = new Vec2i(amountToTranslate.x+30, amountToTranslate.y);
//			//System.out.println("a");
//		} else if (e.getKeyChar() == 's') {
//			amountToTranslate = new Vec2i(amountToTranslate.x, amountToTranslate.y-30);
//			//System.out.println("s");
//		} else if (e.getKeyChar() == 'd') {
//			amountToTranslate = new Vec2i(amountToTranslate.x-30, amountToTranslate.y);
//			//System.out.println("d");
//		} else {
//			//world.worldKeyPressed(e);
//		}
//	}

	public GameWorld getWorld() {
		return world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}
	
}
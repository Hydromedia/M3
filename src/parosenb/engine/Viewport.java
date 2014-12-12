package parosenb.engine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import parosenb.m.game.GameWorld;
import cs1971.Vec2f;
import cs1971.Vec2i;

public abstract class Viewport {
		public Vec2i stl;
		public Vec2f gtl;
		public Vec2i sViewSize;
		public float scale;
		public Vec2f gSize;
		protected GameWorld world;
		
//		public Vec2i amountToTranslate = new Vec2i(0, 0);
//		public float amountToZoom  = (float) 1;
//		public Vec2i viewSize = new Vec2i(0,0);
//		public Vec2i topLeft = new Vec2i(0,0);
//		public Vec2i test = new Vec2i (0,0);
		
		public Viewport(Vec2i stl, Vec2f gtl, Vec2i sViewSize, Vec2f gSize, float scale, GameWorld world) {
			this.stl = stl;
			this.gtl = gtl;
			this.gSize = gSize;
			this.sViewSize = sViewSize;
			this.scale = scale;
			this.world = world;
		}
		
		public void transform (Graphics2D g) {
			g.translate(-stl.x, -stl.y);
			g.scale(scale, scale);
			g.translate((double) gtl.x, (double) gtl.y);
		}
		
		public void onDraw (Graphics2D g) {
			g.clipRect(stl.x, stl.y, sViewSize.x, sViewSize.y);
			AffineTransform old = g.getTransform();
			transform(g);
			world.onDraw(g);
			g.setTransform(old);
		}
		
	
//	public void translate(Vec2i translation) {
//		transform.translate(translation.x, translation.y);
//	}
	
//	public void addTranslation(Vec2i translation){
//		amountToTranslate = amountToTranslate.plus(translation);
//	}
	
//	public void zoom(float amount, Vec2i viewCenter) {
//		//transform.translate(viewCenter.x, viewCenter.y);
//		transform.scale(amount, amount);
//		//transform.translate(-viewCenter.x, -viewCenter.y);
//	}
	
	public static Point viewToScreen(Vec2i gameViewSize, Vec2i amountToTranslate, float amountToZoom, Point startPoint, Vec2i topLeft){
		return new Point(topLeft.x + (int) (((1/amountToZoom)*(startPoint.x - topLeft.x) - amountToTranslate.x)),
				  topLeft.y + (int) (((1/amountToZoom)*(startPoint.y - topLeft.y) - amountToTranslate.y)));
	}

	public static Point screenToView(Vec2i gameViewSize, Vec2i amountToTranslate, float amountToZoom, Point startPoint, Vec2i topLeft) {
		return new Point( topLeft.x - (int) (((amountToZoom)*(-startPoint.x + topLeft.x - amountToTranslate.x))),
						  topLeft.y - (int) (((amountToZoom)*(-startPoint.y + topLeft.y - amountToTranslate.y))));
	}
}

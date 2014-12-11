package parosenb.engine;

import java.awt.Point;
import java.awt.geom.AffineTransform;

import cs1971.Vec2i;

public abstract class Viewport {
	public AffineTransform transform;
	public Vec2i amountToTranslate = new Vec2i(0, 0);
	public float amountToZoom  = (float) 1;
	public Vec2i viewSize = new Vec2i(0,0);
	public Vec2i topLeft = new Vec2i(0,0);
	
	public void translate(Vec2i translation){
		transform.translate(translation.x, translation.y);
	}
	
	public void zoom(float amount, Vec2i viewCenter) {
		transform.translate(viewCenter.x, viewCenter.y);
		transform.scale(amount, amount);
		transform.translate(-viewCenter.x, -viewCenter.y);
	}
	
	public static Point viewToScreen(Vec2i gameViewSize, Vec2i amountToTranslate, float amountToZoom, Point startPoint, Vec2i topLeft){
		return new Point(topLeft.x + (int) (((1/amountToZoom)*(startPoint.x - topLeft.x) - amountToTranslate.x)),
				  topLeft.y + (int) (((1/amountToZoom)*(startPoint.y - topLeft.y) - amountToTranslate.y)));
	}

	public static Point screenToView(Vec2i gameViewSize, Vec2i amountToTranslate, float amountToZoom, Point startPoint, Vec2i topLeft) {
		return new Point( topLeft.x - (int) (((amountToZoom)*(-startPoint.x + topLeft.x - amountToTranslate.x))),
						  topLeft.y - (int) (((amountToZoom)*(-startPoint.y + topLeft.y - amountToTranslate.y))));
	}
}

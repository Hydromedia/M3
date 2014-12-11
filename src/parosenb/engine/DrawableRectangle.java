package parosenb.engine;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import cs1971.Vec2f;

public abstract class DrawableRectangle {
	
	public Vec2f position;
	public Vec2f size;
	public Color fillColor;
	protected Color borderColor;
	public Rectangle drawRect;
	
	public void draw(Graphics2D graphics){
		drawRect = new Rectangle((int) this.position.x, (int) this.position.y, (int) this.size.x, (int) this.size.y);
		graphics.draw(drawRect);
		graphics.fill(drawRect);
	}
	
	
	public int outcode(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRect(Vec2f position, Vec2f size) {
		this.position = position;
		this.size = size;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void drawText(String text, Graphics2D graphics) {
		float xMid =  position.x + (size.x / 2);
		float yMid =  position.y + (size.y / 2);
        FontMetrics metrics = graphics.getFontMetrics(); 
		float textSize = (float) (metrics.getStringBounds(text, graphics).getWidth());
		graphics.drawString(text, xMid - (textSize / 2), (float) (yMid + (metrics.getStringBounds(text, graphics).getHeight()/3)));
	}
}

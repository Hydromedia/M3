package parosenb.m.game;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.Color;

import cs1971.Vec2f;
import parosenb.engine.DrawableRectangle;

public class MenuButton extends DrawableRectangle {
	private Vec2f arc;
	public boolean isGame;
	public RoundRectangle2D.Float drawRect;
	
	public MenuButton(Vec2f position, Vec2f size, Vec2f arc, Color fillColor, Color borderColor, boolean isGame) {
		this.position = position;
		this.isGame = isGame;
		this.size = size;
		this.arc = arc;
		this.fillColor = fillColor;
		this.borderColor = borderColor;
	}
	
	@Override
	public void draw(Graphics2D graphics){
		drawRect = new RoundRectangle2D.Float(this.position.x, this.position.y, this.size.x, this.size.y, this.arc.x, this.arc.y);
		graphics.setColor(borderColor);
		graphics.fillRoundRect((int) (position.x-(size.x/28)), (int) (position.y -(size.y/15)), (int) (size.x+(size.x/30)), (int) (size.y+(size.y/20)), (int) arc.x, (int) arc.y);
		graphics.setColor(fillColor);
		graphics.fill(drawRect);
	}
}
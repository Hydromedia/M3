package parosenb.m.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Font;
import java.awt.FontMetrics;

import parosenb.engine.Application;
import parosenb.engine.Screen;
import cs1971.Vec2i;

public class WinScreen extends Screen {
	private Application app;
	private Vec2i dim;
	private String winner;
	
	public WinScreen(Application app, Vec2i screenVector, String winner){
		this.app = app;
		this.screenVector = screenVector;
		this.winner = winner;
	}

	@Override
	public void screenTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenDraw(Graphics2D g) {
		int width = screenVector.x, x = 0;
		int height = screenVector.y, y = 0;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		String text1 = winner;
		String text2 = "Wins The Game!";
		String text3 = "Press Space to Play Again";
		float x1 =  x + (width / 2);
		float y1 =  y + (height / 4);
		float x2 =  x + (width / 2);
		float y2 =  y + 2*(height / 4);
		float x3 =  x + (width / 2);
		float y3 =  y + 3*(height / 4);
        FontMetrics metrics = g.getFontMetrics(); 
		float textSize1 = (float) metrics.getStringBounds(text1, g).getWidth();
		float textSize2 = (float) metrics.getStringBounds(text2, g).getWidth();
		float textSize3 = (float) metrics.getStringBounds(text3, g).getWidth();
		g.drawString(text1, x1 - (textSize1 / 2), y1);
		g.drawString(text2, x2 - (textSize2 / 2), y2);
		g.drawString(text3, x3 - (textSize3 / 2), y3);
	}

	@Override
	public void screenKeyTyped(KeyEvent e) {
		if (e.getKeyChar() == ' '){
			app.delScreen();
			app.addScreen(new MenuScreen(app, screenVector));
		}
	}

	@Override
	public void screenKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void screenMouseClicked(MouseEvent e) {
		
		
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
		this.screenVector = newSize;
	}
	
	
}

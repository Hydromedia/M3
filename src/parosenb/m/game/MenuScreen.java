package parosenb.m.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.util.ArrayList;

import parosenb.engine.Application;
import parosenb.engine.Screen;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class MenuScreen extends Screen {
	private Application app;
	public ArrayList<MenuButton> buttons;
	//GameWorld world;
	int counter = 0;
	//GameViewport view;
	
	public MenuScreen(Application app, Vec2i screenVector){
		this.app = app;
		this.screenVector = screenVector;
		//world = new GameWorld(null, 100, 100, 100, app);
		//view = new GameViewport(world);
	}

	@Override
	public void screenTick(long nanosSincePreviousTick) {
		float milliSecondsSincePreviousTick = nanosSincePreviousTick / 1000000;
		counter = counter + ( (int) milliSecondsSincePreviousTick);
		if (counter >= 5000) {
			int test = (int) (Math.random() * 1000);
			//world = new GameWorld(null, (test), 100, 100, app);
			//view.setWorld(world);
			counter = 0;
		}
	}
	
	public void drawTitle(Graphics2D g, String text){
		g.setColor(Color.BLACK);
		float x =  this.screenVector.x/2;
		float y =  this.screenVector.y/6;
		Font font = new Font("Cracked", 1, screenVector.x/15);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(); 
		float textSize = (float) (metrics.getStringBounds(text, g).getWidth());
		g.drawString(text, x - (textSize / 2), y);
	}
	
	public void drawButtons(Graphics2D g){
		MenuButton button2 = new MenuButton(new Vec2f((screenVector.x/2) - ((screenVector.x/3)/2),
				(screenVector.y/2) + (screenVector.y/5)), new Vec2f(((screenVector.x/3)),
				(screenVector.y/5)), new Vec2f(20, 20), Color.LIGHT_GRAY, new Color(100, 100, 100, 200), true);
		button2.draw(g);
		g.setColor(Color.BLACK);
		button2.drawText("Play M1!", g);
		buttons.add(button2);
	}
	
	@Override
	public void screenDraw(Graphics2D g) {
		buttons = new ArrayList<MenuButton>();
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, screenVector.x, screenVector.y);
		//view.onDraw(g, new Vec2i(0, 0), screenVector, screenVector);
		drawButtons(g);
		drawTitle(g, "Welcome to Perry's M!");
	}

	@Override
	public void screenKeyTyped(KeyEvent e) {
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
		for (int i = 0; i < buttons.size(); i++){
			if (buttons.get(i).drawRect.contains(e.getPoint())){
				app.delScreen();
				if (!buttons.get(i).isGame){
					//app.addScreen(new CollisionTestScreen(app, screenVector));
				} else {
					app.addScreen(new GameScreen(app, screenVector));
				}
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
		this.screenVector = newSize;
	}
	
	
}

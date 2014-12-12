package parosenb.m.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.FontMetrics;

import parosenb.engine.Application;
import parosenb.engine.Screen;
import parosenb.engine.Viewport;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class GameScreen extends Screen {
	private Application app;
	private GameWorld gameWorld;
	private GameViewport gameView;
	private int WIDTH = 700;
	private int HEIGHT = 500;
	private final float myScale = .5f;
	private Vec2f gameViewSize;
	public GameScreen(Application app, Vec2i screenVector){
		this.app = app;
		this.screenVector = screenVector;
		this.gameWorld = new GameWorld(new Vec2f(WIDTH, HEIGHT), this);
		gameWorld.initializeWorld("levels/level1.nlf");
		//int size = Math.min(screenVector.x, screenVector.y);
		this.gameView = new GameViewport(new Vec2i(0,0), new Vec2f(0,0), screenVector, new Vec2f(WIDTH, HEIGHT), 1, gameWorld);
		gameWorld.setView(gameView);
		//this.gameView.viewSize = new Vec2i(WIDTH, HEIGHT);
	}

	@Override
	public void screenTick(long nanosSincePreviousTick) {
		gameWorld.onTick(nanosSincePreviousTick);
	}
	
	public Application getApp(){
		return app;
	}
	public void drawText(String text, Graphics2D graphics, Vec2i position, Vec2i size) {
		float xMid =  position.x + (size.x / 2);
		float yMid =  position.y + (size.y / 2);
        FontMetrics metrics = graphics.getFontMetrics(); 
		float textSize = (float) (metrics.getStringBounds(text, graphics).getWidth());
		graphics.drawString(text, xMid - (textSize / 2), (float) (yMid + (metrics.getStringBounds(text, graphics).getHeight()/3)));
	}
	
	@Override
	public void screenDraw(Graphics2D g) {
		gameView.scale = Math.min(this.screenVector.x/((float) WIDTH), this.screenVector.y/((float) HEIGHT))*myScale;
		//gameView.amountToZoom = .4f;
		gameView.onDraw(g, new Vec2i(0,0), screenVector, new Vec2i(WIDTH, HEIGHT));
		//this.gameView.viewSize = new Vec2i((int) (screenVector.x * gameView.amountToZoom),(int) (screenVector.y * gameView.amountToZoom));
		//Vec2i(Math.max(0, (screenVector.x - WIDTH)/2), Math.max(0, (screenVector.y - HEIGHT)/2))
	}

	@Override
	public void screenKeyTyped(KeyEvent e) {
		gameWorld.onKeyTyped(e, gameView);
	}

	@Override
	public void screenKeyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			app.delScreen();
			app.addScreen(new MenuScreen(app, screenVector));
		} else {
			gameWorld.onKeyPressed(e, gameView);
		}
		
	}

	@Override
	public void screenKeyReleased(KeyEvent e) {
		gameWorld.onKeyReleased(e, gameView);
		
	}

	@Override
	public void screenMouseClicked(MouseEvent e) {
		gameWorld.onMouseClicked(e, gameView);
	}

	@Override
	public void screenMousePressed(MouseEvent e) {
		gameWorld.onMousePressed(e, gameView);
	}

	@Override
	public void screenMouseReleased(MouseEvent e) {
		gameWorld.onMouseReleased(e, gameView);
	}
	
	
	
	@Override
	public void screenMouseDragged(MouseEvent e) {
		gameWorld.onMouseDragged(e, gameView);
	}

	@Override
	public void screenMouseMoved(MouseEvent e) {
		gameWorld.onMouseMoved(e, gameView);
	}

	@Override
	public void screenMouseWheelMoved(MouseWheelEvent e) {
		gameWorld.onMouseWheelMoved(e, gameView);
	}

	@Override
	public void screenResize(Vec2i newSize) {
		this.screenVector = newSize;
		gameView.scale = Math.min(this.screenVector.x/((float) WIDTH), this.screenVector.y/((float) HEIGHT))*myScale;
		//int size = Math.min(screenVector.x, screenVector.y);
		gameView.sViewSize = screenVector;
		System.out.println(gameView.scale);
		//this.gameView.viewSize = new Vec2i((int) (screenVector.x * gameView.amountToZoom),(int) (screenVector.y * gameView.amountToZoom));
	}
	
	
}
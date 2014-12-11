package parosenb.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import cs1971.Vec2i;

public abstract class Screen {
	protected Application app;
	public Vec2i screenVector;

	public abstract void screenTick(long nanosSincePreviousTick);
	
	public abstract void screenDraw(Graphics2D g);
	
	public abstract void screenKeyTyped(KeyEvent e);

	public abstract void screenKeyPressed(KeyEvent e);

	public abstract void screenKeyReleased(KeyEvent e);

	public abstract void screenMouseClicked(MouseEvent e);

	public abstract void screenMousePressed(MouseEvent e);

	public abstract void screenMouseReleased(MouseEvent e);

	public abstract void screenMouseDragged(MouseEvent e);

	public abstract void screenMouseMoved(MouseEvent e);

	public abstract void screenMouseWheelMoved(MouseWheelEvent e);
	
	public abstract void screenResize(Vec2i newSize);
}

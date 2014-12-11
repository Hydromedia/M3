package parosenb.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.*;

import cs1971.SwingFrontEnd;
import cs1971.Vec2i;

public class Application extends SwingFrontEnd {
	
	//ScreenList architecture is designed this way in order to allow 
	//for the later use of transparent screens: this is easier with
	//a list architecture than a stack.
	//The screen that is "on top" is the last screen in the list.
	private ArrayList<Screen> screenList = new ArrayList<Screen>();
	
	public Application(String title, boolean fullscreen, Vec2i windowSize) {
		super(title, fullscreen, windowSize);
	}
	
	public void addScreen(Screen screen){
		screenList.add(screen);
		System.out.println("Number of screens: " + screenList.size());
	}
	
	public void delScreen(){
		if(screenList.size() > 0){
			screenList.remove(screenList.size()-1);
		}
	}
	
	@Override
	protected void onTick(long nanosSincePreviousTick) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenTick(nanosSincePreviousTick);
		}
	}

	@Override
	protected void onDraw(Graphics2D g) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenDraw(g);
		}
		//System.out.println("Draw");
	}

	@Override
	protected void onKeyTyped(KeyEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenKeyTyped(e);
		}
		//System.out.println("KeyTyped");
	}

	@Override
	protected void onKeyPressed(KeyEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenKeyPressed(e);
		}
		//System.out.println("KeyPressed");
	}

	@Override
	protected void onKeyReleased(KeyEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenKeyReleased(e);
		}
		//System.out.println("KeyReleased");
	}

	@Override
	protected void onMouseClicked(MouseEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMouseClicked(e);
		}
		//System.out.println("MouseClicked");
	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMousePressed(e);
		}
		//System.out.println("MousePressed");
	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMouseReleased(e);
		}
		//System.out.println("MouseReleased");
	}

	@Override
	protected void onMouseDragged(MouseEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMouseDragged(e);
		}
		//System.out.println("MouseDragged");
	}

	@Override
	protected void onMouseMoved(MouseEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMouseMoved(e);
		}
		//System.out.println("MouseMoved");
	}

	@Override
	protected void onMouseWheelMoved(MouseWheelEvent e) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenMouseWheelMoved(e);
		}
		//System.out.println("MouseWheelMoved");
	}

	@Override
	protected void onResize(Vec2i newSize) {
		if (screenList.size() > 0) {
			(screenList.get(screenList.size()-1)).screenResize(newSize);
		}
		//System.out.println("Resize");
	}

}

package parosenb.m.game;

import java.util.ArrayList;

import parosenb.engine.*;
import parosenb.engine.Spriter.SpriteLoader;
import cs1971.Vec2i;

public class MMain {
	public static void main(String[] args) {
		
		initializeSpriteFiles();
		
		Vec2i windowSize =  new Vec2i(700, 500);
		Application app = new Application("Perry's M1", false, windowSize);
		app.addScreen(new MenuScreen(app, windowSize));
		app.startup(); // begin processing events
	}
	
	public static void initializeSpriteFiles() {
		ArrayList<SpriteFile> files = new ArrayList<SpriteFile>();
		//files.add(new SpriteFile(System.getProperty("user.dir") + "/sprites.png", 32, 1));
		SpriteLoader.initialize(files);
	}
}
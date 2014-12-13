package parosenb.engine.IO;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs1971.Vec2f;
import parosenb.engine.Entity;
import parosenb.engine.World;
import parosenb.engine.collision.Shape;

public class HeartBeat extends Entity {
	
	private HeartBeatInput doHeartBeat = new HeartBeatInput();
	public HeartBeat(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		namesToInputs.put("doHeartBeat", doHeartBeat);
	}
	
	class HeartBeatInput extends Input {
		@Override
		public void run(HashMap<String, String> args) {
			System.out.println("lub dub");
		}
	 }

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}

package parosenb.engine.IO;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs1971.Vec2f;
import parosenb.engine.Entity;
import parosenb.engine.World;
import parosenb.engine.collision.Shape;

public class Relay extends Entity {
	
	private Output onSignalRecieved = new Output();
	private RelayOpenInput doRelayOpen = new RelayOpenInput();
	private RelayCloseInput doRelayClose = new RelayCloseInput();
	private RelaySendInput doRelaySend = new RelaySendInput();
	private boolean isOpen = false;
	public Relay(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		namesToInputs.put("doRelayOpen", doRelayOpen);
		namesToInputs.put("doRelayClose", doRelayClose);
		namesToInputs.put("doRelaySend", doRelaySend);
		namesToOutputs.put("onSignalRecieved", onSignalRecieved);
	}
	
	class RelayOpenInput extends Input {
		@Override
		public void run(HashMap<String, String> args) {
			System.out.println("Relay is open");
			isOpen = true;
		}
	 }
	class RelayCloseInput extends Input {
		@Override
		public void run(HashMap<String, String> args) {
			System.out.println("Relay is closed");
			isOpen = false;
		}
	 }
	class RelaySendInput extends Input {
		@Override
		public void run(HashMap<String, String> args) {
			if(isOpen){
				System.out.println("Sending");
				onSignalRecieved.run();
			}
		}
	 }

	@Override
	public void onTick(long nanosSincePreviousTick) {
		
	}

	@Override
	public void onDraw(Graphics2D g) {
		
	}
	
}

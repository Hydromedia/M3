package parosenb.engine.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parosenb.engine.PhysicsEntity;
import parosenb.engine.World;
import parosenb.engine.IO.Relay.RelayCloseInput;
import parosenb.engine.IO.Relay.RelayOpenInput;
import parosenb.engine.IO.Relay.RelaySendInput;
import parosenb.engine.collision.Shape;
import cs1971.Vec2f;

public class Trap extends PhysicsEntity {
	private TrapActivateInput doTrapActivate = new TrapActivateInput();
	private boolean isOpen = false;
	public Trap(Vec2f position, World w, String name, ArrayList<Shape> s,
			Map<String, String> properties) {
		super(position, w, name, s, properties);
		this.isMoveable = false;
		namesToInputs.put("doTrapActivate", doTrapActivate);
	}
	
	class TrapActivateInput extends Input {
		@Override
		public void run(HashMap<String, String> args) {
			System.out.println("Trap Activated");
			setMoveable(true);
		}
	 }
}

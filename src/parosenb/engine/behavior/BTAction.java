package parosenb.engine.behavior;


public abstract class BTAction implements BTNode {
	
	@Override
	public abstract Status update(float seconds);
	
	@Override
	public abstract void reset();

}

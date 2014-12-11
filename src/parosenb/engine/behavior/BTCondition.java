package parosenb.engine.behavior;


public abstract class BTCondition implements BTNode{

	@Override
	public abstract Status update(float seconds);
	
	@Override
	public abstract void reset();

}

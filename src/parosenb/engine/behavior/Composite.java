package parosenb.engine.behavior;

import java.util.ArrayList;

public abstract class Composite implements BTNode {
	
	protected ArrayList<BTNode> children;
	protected BTNode lastRunning;
	protected int runIndex;
	
	public Composite () {
		this.children = new ArrayList<BTNode>();
	}
	public ArrayList<BTNode> getChildren(){
		return this.children;
	}
	
	public void addChild(BTNode node){
		children.add(node);
	}
	
	@Override
	public abstract Status update(float seconds);
	
	@Override
	public abstract void reset();

}
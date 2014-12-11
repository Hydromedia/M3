package parosenb.engine.behavior;


public class BTSelector extends Composite {

	@Override
	public Status update(float seconds) {
		if (!children.isEmpty()){
			for (int i = 0; i < children.size(); i++){
				Status status = children.get(i).update(seconds);
				if (status == Status.FAIL){
					lastRunning = null;
				} else if (status == Status.RUNNING) {
					runIndex = i;
					lastRunning = children.get(i);
					return Status.RUNNING;
				} else if (status == Status.SUCCESS) {
					lastRunning = null;
					return Status.SUCCESS;
					//success
				}
			}
			return Status.FAIL;
		} else {
			//should never happen
			return null;
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//reset all children
	}

}

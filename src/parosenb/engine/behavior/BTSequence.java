package parosenb.engine.behavior;


public class BTSequence extends Composite {

	@Override
	public Status update(float seconds) {
		if (!children.isEmpty()){
			if (lastRunning != null) {
				for (int i = runIndex; i < children.size(); i++){
					Status status = children.get(i).update(seconds);
					if (status == Status.FAIL){
						lastRunning = null;
						return Status.FAIL;
					} else if (status == Status.RUNNING) {
						runIndex = i;
						lastRunning = children.get(i);
						return Status.RUNNING;
					} else if (status == Status.SUCCESS) {
						lastRunning = null;
						//success
					}
				}
			}
			for (int i = 0; i < children.size(); i++){
				Status status = children.get(i).update(seconds);
				if (status == Status.FAIL){
					lastRunning = null;
					return Status.FAIL;
				} else if (status == Status.RUNNING) {
					runIndex = i;
					lastRunning = children.get(i);
					return Status.RUNNING;
				} else if (status == Status.SUCCESS) {
					lastRunning = null;
					//success
				}
			}
			return Status.SUCCESS;
		}
		else {
			//should never get here?
			return null; 
		}
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//turn last running to null
	}

}

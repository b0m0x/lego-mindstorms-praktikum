package behaviour;

import basis.RobotState;
import basis.SensorArm.POSITION;

public class SensorArmTestBehaviour implements RobotBehaviour {
	private int i = 0;
	
	public void init(RobotState r) {
		
	}
	
	public void update(RobotState r) {
		if (!r.isSensorArmMoving()) {
			switch ((i++) % 2) {
				case 0:
					r.setSensorArmPosition(POSITION.RIGHT);
					break;
				case 1:
					r.setSensorArmPosition(POSITION.LINE_FOLLOW);
					break;
			}
			
		}
		//r.setSensorArmPosition(POSITION.LINE_FOLLOW);
	}

	public boolean isNextLevel() {
		// TODO Auto-generated method stub
		return false;
	}

}

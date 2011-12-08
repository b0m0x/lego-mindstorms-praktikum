package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;

public class SensorArmTestBehaviour implements RobotBehaviour {
	private int i = 0;
	
	public void init(RobotState r) {
		
	}
	
	public void update(RobotState r) {
		if (!r.isSensorArmMoving()) {
			switch ((i++) % 2) {
				case 0:
					r.setSensorArmPosition(SensorArmPosition.POSITION_LABYRINTH);
					break;
				case 1:
					r.setSensorArmPosition(SensorArmPosition.POSITION_LINE_FOLLOW);
					break;
			}
			
		}
		//r.setSensorArmPosition(SensorArmPosition.POSITION_LINE_FOLLOW);
	}

}

package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;

public class SensorArmTestBehaviour implements RobotBehaviour {

	public void update(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_LABYRINTH);
		try {
		Thread.sleep(1000);
		
		r.setSensorArmPosition(SensorArmPosition.POSITION_LINE_FOLLOW);
		Thread.sleep(1000);
		} catch (Exception e) {
			//lala
		}
	}

}

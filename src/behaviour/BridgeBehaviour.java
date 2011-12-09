package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;

public class BridgeBehaviour implements RobotBehaviour {

	private static final int COLOR_BRIDGE = 40;
	private static final int COLOR_OFFTRACK = 20;
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_BRIDGE);
		r.addBehaviour(new WallFollowBehaviour(10));
	}

	public void update(RobotState r) {
		//int wallDist = r.getUltraSonic();
		
		
		int sensor = r.getLightSensor();
		if (sensor <= COLOR_OFFTRACK) {
			r.bendLeft(30);
		} else {
			r.forward(30);
		}

	}

	public boolean isNextLevel() {
		// TODO Auto-generated method stub
		return false;
	}

}

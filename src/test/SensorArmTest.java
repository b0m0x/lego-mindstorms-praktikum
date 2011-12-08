package test;

import basis.RobotState;
import behaviour.LineFollowBehaviour;
import behaviour.SensorArmTestBehaviour;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		//r.addBehaviour(new LineFollowBehaviour());
		r.driveForward(50);
		while(true) {
			r.update();
		}
	}
}

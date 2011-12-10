package test;

import basis.RobotState;
import basis.SensorArm.POSITION;
import behaviour.LabyrinthBlockingBehaviour;

public class CalibrationTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		RobotState r = RobotState.getInstance();
		r.addBehaviour(new LabyrinthBlockingBehaviour());
		r.init();
		
		//r.setSensorArmPosition(POSITION.FRONT);
		r.setSensorArmPosition(POSITION.RIGHT);
	}
}
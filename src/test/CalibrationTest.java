package test;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.LabyrinthBlockingBehaviour;

public class CalibrationTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		RobotState r = RobotState.getInstance();
		r.addBehaviour(new LabyrinthBlockingBehaviour());
		r.init();
		
		//r.setSensorArmPosition(SensorArmPosition.FRONT);
		r.setSensorArmPosition(SensorArmPosition.RIGHT);
	}
}
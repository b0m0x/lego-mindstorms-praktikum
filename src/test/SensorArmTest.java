package test;

import basis.RobotState;
import behaviour.SensorArmTestBehaviour;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		r.addBehaviour(new SensorArmTestBehaviour());
		
		while(true) {
			r.update();
		}
	}
}

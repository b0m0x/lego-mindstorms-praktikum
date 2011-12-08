package test;

import basis.RobotState;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		//r.addBehaviour(new LineFollowBehaviour());
		r.init();
		r.forward(50);
		while(true) {
			r.update();
		}
	}
}

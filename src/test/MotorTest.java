package test;

import basis.RobotState;
import behaviour.EngineTestBehaviour;

public class MotorTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		r.addBehaviour(new EngineTestBehaviour());
		
		while(true) {
			r.update();
		}
	}
}

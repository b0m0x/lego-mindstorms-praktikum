package test;

import behaviour.BridgeBehaviour;
import behaviour.LineFollowBehaviour;
import basis.RobotState;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		//r.addBehaviour(new LabyrinthBehaviour());
		r.addBehaviour(new BridgeBehaviour());
		r.init();
		r.forward(10);
		while(true) {
			r.update();
		}
	}
}

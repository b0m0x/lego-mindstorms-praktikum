package test;

import behaviour.BridgeBehaviour;
import basis.RobotState;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		//r.addBehaviour(new LabyrinthBehaviour());
		//r.addBehaviour(new BridgeBehaviour());
		r.init();
		//r.forward(20);
		while(r.isSensorArmMoving()) {
			r.update();
		}
	}
}

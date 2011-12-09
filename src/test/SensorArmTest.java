package test;

import behaviour.BridgeBehaviour;
import behaviour.LabyrinthBehaviour;
import basis.RobotState;
import behaviour.SensorArmTestBehaviour;

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

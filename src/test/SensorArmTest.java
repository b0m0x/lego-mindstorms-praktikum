package test;

import behaviour.LabyrinthBehaviour;
import basis.RobotState;
import behaviour.SensorArmTestBehaviour;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		r.addBehaviour(new LabyrinthBehaviour());
		r.init();
		
		
		while(true) {
			r.update();
		}
	}
}

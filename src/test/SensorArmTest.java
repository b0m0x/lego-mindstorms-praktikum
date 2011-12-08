package test;

import basis.LabyrinthBehaviour;
import basis.RobotState;
import behaviour.SensorArmTestBehaviour;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		r.addBehaviour(new LabyrinthBehaviour());
		r.init();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true) {
			r.update();
		}
	}
}

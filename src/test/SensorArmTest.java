package test;

import behaviour.BridgeBehaviour;
import behaviour.LineFollowBehaviour;
import behaviour.WallFollowBehaviour;
import basis.RobotState;

public class SensorArmTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		
		//r.addBehaviour(new SensorArmTestBehaviour());
		//r.addBehaviour(new LabyrinthBehaviour());
		//r.addBehaviour(new BridgeBehaviour());
		r.addBehaviour(new WallFollowBehaviour(15));
		r.init();
		//r.forward(20);
		while(true) {
			r.update();
		}
	}
}

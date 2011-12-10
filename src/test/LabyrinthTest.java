package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class LabyrinthTest {
	public static void main (String[] aArg) throws Exception {
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
		r.addBehaviour(new LineFollowBehaviour());
		r.init();
		
		while(true) {
			r.update();
		}
		// r.driveForward(10);
	}
}
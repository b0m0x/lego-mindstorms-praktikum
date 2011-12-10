package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class LabyrinthTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
		//r.addBehaviour(new LineFollowBehaviour());
		r.addBehaviour(new LabyrinthBlockingBehaviour());
		r.init();
		
		while(!Button.ENTER.isPressed()) {
			r.update();
		}
		// r.driveForward(10);
	}
}
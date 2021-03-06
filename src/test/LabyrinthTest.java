package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class LabyrinthTest {
	public static void main (String[] aArg) throws Exception {
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
		r.addBehaviour(new WallFollowBehaviour(10));
		r.addBehaviour(new BumpOffWallAndRotateBehaviour());
		r.init();
		
		while(!Button.ENTER.isPressed()) {
			r.update();
		}
		// r.driveForward(10);
	}
}
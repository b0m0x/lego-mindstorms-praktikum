package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class LabyrinthTest {
	public static void main (String[] aArg) throws Exception {
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
<<<<<<< HEAD
		r.addBehaviour(new LineFollowBehaviour());
=======
		
		r.addBehaviour(new LabyrinthBlockingBehaviour());
		//r.addBehaviour(new WallFollowBehaviour(10));

>>>>>>> 5947ba551158c01f08e61b814171f1653cbb8c50
		r.init();
		
		while(true) {
			r.update();
		}
		// r.driveForward(10);
	}
}
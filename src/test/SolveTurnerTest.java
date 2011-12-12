package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class SolveTurnerTest {
	public static void main (String[] aArg) throws Exception {
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
		r.addBehaviour(new TurnTableBehaviour());
		r.init();
		
		while(true) {
			r.update();
			
		}
		// r.driveForward(10);
	}
}
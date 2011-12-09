package test;

import basis.RobotState;
import behaviour.*;
import lejos.nxt.*;

public class HelloWorld {
	public static void main (String[] aArg) throws Exception {
		RobotState r = RobotState.getInstance();
		//r.addBehaviour(new DriveForwardAndStopBehaviour());
		//r.addBehaviour(new EngineTestBehaviour());
		r.addBehaviour(new LabyrinthBehaviour());
		r.init();
	
		// r.driveForward(10);
	}
}
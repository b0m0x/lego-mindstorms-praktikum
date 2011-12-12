package behaviour;

import lejos.nxt.Sound;
import helper.H;
import basis.RobotState;

public class TurnTableBehaviour implements RobotBehaviour {

	private RobotState robot;
	private final int LIGHT = 60;
	private boolean fertig = false;
	private LineFollowBehaviour liniensucher;
	
	public void init(RobotState r) {
		robot = r;
		
	}

	public void update(RobotState r) {
		if ( isLight() ) {
			robot.halt();
			robot.backwardBlocking(50, 500);
			robot.rotate(180);
			robot.backwardBlocking(100, 1000);
			robot.halt();
			H.sleep(3300);
			robot.forwardBlocking(100, 1000);
			fertig = true;
		} else {
			
			// TODO Liniensuche
			// if (fertig)
		}
	}
	
	private boolean isLight() {
		int color = robot.getLightSensor();
		return LIGHT <= color;
	}

}

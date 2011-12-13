package behaviour;

import lejos.nxt.Sound;
import helper.Eieruhr;
import helper.H;
import basis.RobotState;

public class TurnTableBehaviour implements RobotBehaviour {

	private RobotState robot;
	private final int LIGHT = 60;
	private boolean fertig = false;
	private LineFollowBehaviour liniensucher;
	
	public void init(RobotState r) {
		robot = r;
		robot.forward(20);
	}

	public void update(RobotState r) {
		if ( isLight() ) {
			robot.halt();
			Eieruhr timer = new Eieruhr(100);
			//robot.backwardBlocking(50, 200);
			robot.rotate(180);
			robot.backwardBlocking(100, 1000);
			robot.halt();
			int counter = 2;
			while (counter > 0) {
				if ( isLight() && timer.isFinished() ) {
					Sound.buzz();
					counter--;
				}
			}
			//H.sleep(3300);
			
			robot.forwardBlocking(100, 1000);
//			fertig = true;
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

package behaviour;

import lejos.nxt.Sound;
import helper.Eieruhr;
import helper.H;
import basis.RobotState;

public class TurnTableBehaviour implements RobotBehaviour {

	private RobotState robot;
	private final int LIGHT = 60;
	private boolean fertig = false;
	//private LineFollowBehaviour liniensucher = new LineFollowBehaviour();
	
	public void init(RobotState r) {
		robot = r;
		robot.forward(20);
		//robot.bend(0.1f);
		//liniensucher.init(robot);
	}

	public void update(RobotState r) {
		//liniensucher.update(robot);
		if ( isLight() ) {
			robot.halt();
			//robot.backwardBlocking(50, 200);
			robot.rotate(180);
			robot.backwardBlocking(100, 1000);
			robot.halt();
			Eieruhr timer = new Eieruhr((int) (2700 +  300 * (Math.random() - 0.5f)) );
			while (!timer.isFinished()) {}
			
			robot.forward(100);
			Eieruhr timer2 = new Eieruhr(4000);
			while (!timer2.isFinished()) {}
			robot.changeToNextLevel();
		}
	}
	
	private boolean isLight() {
		int color = robot.getLightSensor();
		return LIGHT <= color;
	}

}

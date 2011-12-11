package behaviour;

import helper.H;
import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.POSITION;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;
	private final int NORMAL_SPEED = 50;
	private RobotState robot;
	
	public WallFollowBehaviour(int distance) {
		WALL_DISTANCE = distance;
	}
	
	public void init(RobotState r) {
		robot = r;
		robot.forward( NORMAL_SPEED );
	}

	public void update(RobotState r) {
		if ( robot.crashedIntoWall() ) {
			wallContact();
		} else {
			follow();
		}
	}
	
	private void follow() {
		int dist = robot.getUltraSonic();
		H.p("dist:", dist);
		if (dist == 255) {
<<<<<<< HEAD
			robot.bend(0.5f);
		} else {
			float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
			robot.bend(strength);
=======
			/*
			r.forwardBlocking(50, 500);
			r.halt();
			r.rotate(90);
			r.forwardBlocking(50, 1000);
			r.halt();
			r.getUltraSonic();
			r.forward(50);*/
			r.bend(0.5f);
			return;
>>>>>>> master
		}
	}
	
	private void wallContact() {
		robot.halt();
		robot.rotate(-90);
		robot.halt();
		robot.forward( NORMAL_SPEED );
	}
}


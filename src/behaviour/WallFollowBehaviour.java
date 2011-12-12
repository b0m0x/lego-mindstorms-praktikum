package behaviour;

import basis.RobotState;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;
	private final int NORMAL_SPEED = 50;
	private RobotState robot;
<<<<<<< HEAD
	
=======
	private int lastDist;

>>>>>>> 8ebfc6924ea4453c4b8e88ff5f7fdb2d454d38e4
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
<<<<<<< HEAD
		H.p("dist:", dist);
		if (dist == 255) {

			robot.bend(0.5f);
		} else {
			float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
			robot.bend(strength);
=======
		if (dist == lastDist) {
			return;
		}
		if (dist == 255) {
			robot.bend(0.5f);
>>>>>>> 8ebfc6924ea4453c4b8e88ff5f7fdb2d454d38e4
			return;
		} else {
			float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
			robot.bend(strength);
		}
<<<<<<< HEAD
=======
		lastDist = dist;
>>>>>>> 8ebfc6924ea4453c4b8e88ff5f7fdb2d454d38e4
	}
	
	private void wallContact() {
		robot.halt();
		robot.rotate(-90);
		robot.halt();
		robot.forward( NORMAL_SPEED );
	}
}


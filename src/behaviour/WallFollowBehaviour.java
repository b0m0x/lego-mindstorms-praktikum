package behaviour;

import basis.RobotState;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;
	private final int NORMAL_SPEED = 50;
	private RobotState robot;
	private int lastDist;

	
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

		if (dist == lastDist) {
			return;
		}
		if (dist == 255) {
			robot.bend(0.5f);
			return;
		} else {
			float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
			robot.bend(strength);
		}
	}
	
	private void wallContact() {
		robot.halt();
		robot.rotate(-90);
		robot.halt();
		robot.forward( NORMAL_SPEED );
	}
}


package behaviour;

import basis.RobotState;

public class BumpOffWallAndRotateBehaviour implements RobotBehaviour {

	public static final int NORMAL_SPEED = 50;
	
	public void init(RobotState r) {
		// TODO Auto-generated method stub

	}

	public void update(RobotState r) {
		if (r.crashedIntoWall()) {
			r.backwardBlocking(50, 500);
			r.halt();
			r.rotate(-90);
			r.halt();
			r.forward( NORMAL_SPEED );
		}

	}

}

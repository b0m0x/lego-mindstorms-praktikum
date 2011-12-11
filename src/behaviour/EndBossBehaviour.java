package behaviour;

import basis.RobotState;

public class EndBossBehaviour implements RobotBehaviour {

	public void init(RobotState r) {
		// TODO Auto-generated method stub

	}

	public void update(RobotState r) {
		while(!r.isMoving()) {
			r.forward(100);
		}

	}

}

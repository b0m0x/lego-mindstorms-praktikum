package behaviour;

import basis.RobotState;

public class HangingBridgeBehaviour implements RobotBehaviour {
	private RobotBehaviour wallFollower;
	private boolean isOnTop;
	
	public void init(RobotState r) {
		wallFollower = new WallFollowBehaviour(15);
		wallFollower.init(r);
		isOnTop = false;
	}

	public void update(RobotState r) {
		if (!isOnTop && r.getUltraSonic() == 255) {
			isOnTop = true;
			r.halt();
			r.forward(100);
		}
		if (!isOnTop) {
			wallFollower.update(r);
		}
		if (isOnTop && r.getUltraSonic() != 255) { //angekommen
			isOnTop = false;
		}		
	}

}

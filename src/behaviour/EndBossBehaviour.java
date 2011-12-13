package behaviour;

import helper.Eieruhr;
import basis.RobotState;

public class EndBossBehaviour implements RobotBehaviour {
	
	Eieruhr timer = new Eieruhr(120000);
	private boolean first = true;
	WallFollowBehaviour wallfollower = new WallFollowBehaviour(8);
	XMASSearchBehaviour xmass = new XMASSearchBehaviour();
	
	public void init(RobotState r) {
		wallfollower.init(r);
		xmass.init(r);
	}

	public void update(RobotState r) {
		if (first) timer.reset();
		if ( !timer.isFinished() ) {
			wallfollower.update(r);
		} else {
			xmass.update(r);
		}
	}

}

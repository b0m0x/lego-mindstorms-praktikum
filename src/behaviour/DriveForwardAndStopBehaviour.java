package behaviour;
import basis.RobotState;

public class DriveForwardAndStopBehaviour implements RobotBehaviour {

	public void update(RobotState r) {
		if (r.getUltraSonic() <= 35) {
			if (r.isMoving()) {
				r.stop();
			}
		} else {
			if (!r.isMoving())
				r.forward(0.6f);
		}
	}

	public void init(RobotState r) {
		// TODO Auto-generated method stub
		
	}

}

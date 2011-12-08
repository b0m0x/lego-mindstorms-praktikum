package behaviour;
import basis.RobotState;

public class DriveForwardAndStopBehaviour implements RobotBehaviour {

	public void update(RobotState r) {
		if (r.getUltraSonic() <= 35) {
			if (r.isMoving()) {
				r.halt();
			}
		} else {
			if (!r.isMoving())
				r.forward(60);
		}
	}

	public void init(RobotState r) {
		// TODO Auto-generated method stub
		
	}

}

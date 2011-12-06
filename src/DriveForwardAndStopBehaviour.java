public class DriveForwardAndStopBehaviour implements RobotBehaviour {

	public void update(RobotState r) {
		if (r.getUltraSonic() <= 35) {
			if (r.isMoving()) {
				r.halt();
			}
		} else {
			if (!r.isMoving())
				r.driveForward(60);
		}
	}

}

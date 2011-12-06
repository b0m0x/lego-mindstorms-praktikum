
public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	public void update(RobotState r) {
		if (start == 0) {
			start = System.currentTimeMillis();
			r.driveForward(60);
		}
		long time = System.currentTimeMillis();
		if (time >= start + 5000) {
			r.driveCurveRight(50);
		}
		
		if (time >= start + 10000) {
			r.driveCurveLeft(50);
		}
		
		if (time >= start + 15000) {
			r.driveForward(50);
		}
		
	}

}

package behaviour;
import basis.RobotState;


public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	public void update(RobotState r) {
		
		r.driveForward(50);
		r.driveCurveRight(10);
		try {
			Thread.sleep(2000);
		
		r.driveCurveRight(30);
		Thread.sleep(2000);
		r.driveCurveRight(60);
		Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
}

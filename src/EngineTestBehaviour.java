
public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	public void update(RobotState r) {
		
		for(int i = 0; i <= 3; i++) {
			r.driveDistance(5000, 30, true);
			r.rotateR(90);
			
		}
		
	}

}

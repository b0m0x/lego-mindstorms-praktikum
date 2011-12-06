
public class LineFollowBehaviour implements RobotBehaviour {
	private final static int COLOR_LINE = 800;
	private final static int COLOR_GROUND = 200;
	
	private int direction;
	private boolean lineLock;
	
	public LineFollowBehaviour() {
		direction = -1;
		lineLock = false;
	}
	
	public void update(RobotState r) {
		int value = r.getLightSensor();
		
		if (value >= COLOR_LINE) {
			lineLock = true; //we got the line
			return;	//keep rollin'
		} else if (value <= COLOR_GROUND && lineLock) {			
			// change direction!
			direction *= -1;
			lineLock = false; //we lost the line!
			
			if (direction == 1) { //right
				r.driveCurveRight(50);
			} else { //left
				r.driveCurveLeft(50);
			}
		} else {
			//uhhm... ?!
			throw new UnsupportedOperationException("We have nor line nor ground, we're flooating in space! :)");
		}
	}

}

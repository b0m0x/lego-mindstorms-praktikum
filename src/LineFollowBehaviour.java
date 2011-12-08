
public class LineFollowBehaviour implements RobotBehaviour {
	private final static int COLOR_LINE = 40;
	private final static int COLOR_GROUND = 30;
	
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
			//System.out.println("Line!!");
			return;	//keep rollin'
		} else if (value <= COLOR_GROUND  && lineLock) {			
			// we see ground, change direction to find our line again!
			direction *= -1;
			lineLock = false; //we lost the line!
			
			r.driveForward(20);
			if (direction == 1) { //right
				r.driveCurveRight(50);
				System.out.println("Driving right");
			} else { //left
				r.driveCurveLeft(50);
				System.out.println("Driving Left");
			}
		} else {
			//uhhm... ?!
			//System.out.println("I'm a Mars Rover!!! (" + value + ", " + lineLock + ")");
		}
	}

}

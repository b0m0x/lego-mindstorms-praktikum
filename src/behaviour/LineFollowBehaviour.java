package behaviour;
import basis.RobotState;


public class LineFollowBehaviour implements RobotBehaviour {
	private final static int COLOR_LINE = 40;
	private final static int COLOR_GROUND = 30;
	
	private int direction;
	private boolean lineLock;
	
	public LineFollowBehaviour() {
		direction = -1;
		lineLock = false;
	}
	
	public void init(RobotState r) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(RobotState r) {
		int value = r.getLightSensor();
		
		if (value >= COLOR_LINE && lineLock == false) {
			lineLock = true; //we got the line
			//System.out.println("Line!!");
			r.driveForward(30);
			return;	//keep rollin'
		} else if (value <= COLOR_GROUND  && lineLock) {			
			// we see ground, change direction to find our line again!
			direction *= -1;
			lineLock = false; //we lost the line!
			
			r.driveForward(20);
			if (direction == 1) { //right
				r.driveCurveRight(50, 300);
				System.out.println("Driving right");
			} else { //left
				r.driveCurveLeft(50, 300);
				System.out.println("Driving Left");
			}
		} else {
			if (value <= COLOR_GROUND && !r.isMoving()) {
				r.driveBackward(60, 200);
			}
		}
	}

	

}

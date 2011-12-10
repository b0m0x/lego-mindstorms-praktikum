package behaviour;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;


public class LineFollowBehaviour implements RobotBehaviour {
	private final static int COLOR_LINE = 40;
	private final static int COLOR_GROUND = 30;
	
	private RobotState r;
	private int direction;
	private boolean lineSearching;
	
	public LineFollowBehaviour() {
		lineSearching = true;
		direction = -1;
	}
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_LINE_FOLLOW);		
		this.r = r;
		r.forward(50);
	}
	
	public boolean isOnLine() {
		int value = r.getLightSensor();
		if (value >= COLOR_LINE) {
			return true;
		}
		return false;
	}
	
	public boolean isOffLine() {
		int value = r.getLightSensor();
		if (value <= COLOR_GROUND) {
			return true;
		}
		return false;
	}
	
	public void update(RobotState r) {
		if (lineSearching && isOnLine()) {
			//line found again
			r.halt();
			r.rotate(180 * direction);
			r.forward(60);
			r.bend(0.6f);
			direction *= -1;
			lineSearching = false;
		} else if (!lineSearching && isOffLine()) {
			lineSearching = true;
		}
		
		/*int value = r.getLightSensor();
		System.out.println(value);
		if (value >= COLOR_LINE && lineLock == false) {
			lineLock = true; //we got the line
			//System.out.println("Line!!");
			r.forward(30);
			return;	//keep rollin'
		} else if (value <= COLOR_GROUND  && lineLock) {			
			// we see ground, change direction to find our line again!
			direction *= -1;
			lineLock = false; //we lost the line!
			
			r.bend(direction * 0.6f);
		} else {
			if (value <= COLOR_GROUND && !r.isMoving()) {
				r.backward(60, 200);
			}
		}*/
		
		
		
	}

	

}

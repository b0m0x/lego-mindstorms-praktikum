package behaviour;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;


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
		r.setSensorArmPosition(SensorArmPosition.POSITION_LINE_FOLLOW);		
	}
	
	public void update(RobotState r) {
		int value = r.getLightSensor();
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
		}
	}

	

}

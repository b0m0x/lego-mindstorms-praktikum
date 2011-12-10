package behaviour;

import basis.RobotState;
import basis.SensorArm.POSITION;

public class LineDirectFollowBehaviour {
	
	private RobotState robot;
	private boolean lineSearching;
	
	private boolean avoidObstacle;
	private WallFollowBehaviour wallFollower;
	
	public LineDirectFollowBehaviour() {
		lineSearching = true;
		avoidObstacle = false;
		wallFollower = new WallFollowBehaviour(10);
	}
	
	public void init(RobotState r) {
		robot = r;
		robot.forward(50);
		robot.setSensorArmPosition(POSITION.LINE_FOLLOW);		
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
		if (avoidObstacle) {
			wallFollower.update(r);
			if (isOnLine()) {
				avoidObstacle = false;
				init(r);
			}
			return;
		}
		if (!avoidObstacle && r.crashedIntoWall()) {
			r.backwardBlocking(50, 200);
			r.rotate(-90);
			avoidObstacle = true;
			wallFollower.init(r);
		}
		
		if (lineSearching && isOnLine()) {
			//line found again
			r.halt();
			r.rotate(180);
			r.forward(50);
			r.bend(0.6f);
			lineSearching = false;
		} else if (!lineSearching && isOffLine()) {
			lineSearching = true;
		}
				
	}
}

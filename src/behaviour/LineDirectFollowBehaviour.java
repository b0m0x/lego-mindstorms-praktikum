package behaviour;

import helper.Eieruhr;
import lejos.nxt.LightSensor;
import basis.Config;
import basis.RobotState;
import basis.SensorArm;
import basis.SensorArm.POSITION;

public class LineDirectFollowBehaviour {
	
	private SensorArm arm;
	private RobotState robot;
	private boolean onLine = false;
	private Eieruhr memory = new Eieruhr(1000);
	private boolean armMovingRight;
	public LineDirectFollowBehaviour() {
	}
	
	public void init(RobotState r) {
		robot = r;
		robot.forward(50);		
		arm = robot.getSensorArm();
		arm.setPosition(POSITION.LEFT, false);
		search();
	}

	public void update(RobotState r) {
		follow();
	}
	
	private void follow() {
		if ( onLine != isLine() ) {
			adjustPath();
			toggleArmDirection();
		}
	}
	
	private void search() {
		
	}
	
	private void toggleArmDirection() {
		if (armMovingRight) {
			arm.setPosition(POSITION.LEFT, false);
			armMovingRight = false;
		} else {
			arm.setPosition(POSITION.FRONT, false);
			armMovingRight = true;
		}
	}
	
	private void adjustPath() {
		arm.getPositionFloat();
		POSITION.FRONT.getValue();
	}
	
	public boolean isLine() {
		int color = robot.getLightSensor();
		if (color >= Config.COLOR_BRIGHT) {
			memory.reset();
			this.onLine = true;
			return true;
		} else {
			this.onLine = false;
			return false;
		}
	}
}

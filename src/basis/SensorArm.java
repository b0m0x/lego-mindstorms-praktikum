package basis;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class SensorArm {
	enum SensorPosition {
		POSITION_LINE_FOLLOW,
		POSITION_LABYRINTH,
		POSITION_HILL
	}
	private final NXTRegulatedMotor SENSOR_MOTOR = Motor.B;
	

	public SensorArm() {
		SENSOR_MOTOR.resetTachoCount();
	}
	
	public void setPosition(SensorPosition p) {
		int absAngle = getPositionAngle(p) - SENSOR_MOTOR.getTachoCount();
		SENSOR_MOTOR.rotateTo(absAngle, true);
	}
	
	public void recalibrate() {
		SENSOR_MOTOR.setSpeed(100);
		SENSOR_MOTOR.forward();
		while (!SENSOR_MOTOR.isStalled()) {
			//däumchen drehen
		}
		SENSOR_MOTOR.stop();
		SENSOR_MOTOR.resetTachoCount();
	}
	
	
	private int getPositionAngle(SensorPosition p) {
		switch(p) {
			case POSITION_LINE_FOLLOW:
			case POSITION_HILL:
				//TODO: INSERT CORRECT VALUE
				return 0;
			case POSITION_LABYRINTH:
				return 120;
			default: 
				return 0;
		}
	}
}

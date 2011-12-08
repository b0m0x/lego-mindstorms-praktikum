package basis;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class SensorArm {
	public enum SensorArmPosition {
		POSITION_LINE_FOLLOW,
		POSITION_LABYRINTH,
		POSITION_HILL
	}
	private final NXTRegulatedMotor SENSOR_MOTOR = Motor.B;
	private boolean rotating;
	private int rotateAngle;
	

	public SensorArm() {
		rotating = false;
		rotateAngle = 0;
		SENSOR_MOTOR.resetTachoCount();
		SENSOR_MOTOR.setSpeed(100);
		//recalibrate();
	}
	
	public void setPosition(SensorArmPosition p) {
		rotateAngle = getPositionAngle(p);
		rotating = true;
		int tacho = SENSOR_MOTOR.getTachoCount();
		
		if (tacho < rotateAngle) {
			SENSOR_MOTOR.forward();
		} else {
			SENSOR_MOTOR.backward();
		}
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
	
	public boolean isMoving() {
		return SENSOR_MOTOR.isMoving();
	}
	
	
	private int getPositionAngle(SensorArmPosition p) {
		switch(p) {
			case POSITION_LINE_FOLLOW:
			case POSITION_HILL:
				//TODO: INSERT CORRECT VALUE
				return 0;
			case POSITION_LABYRINTH:
				return -120;
			default: 
				return 0;
		}
	}
	
	public void update() {
		if (rotating) {
			int tacho = SENSOR_MOTOR.getTachoCount();
			int speed = SENSOR_MOTOR.getRotationSpeed();
			if (speed < 0 && tacho <= rotateAngle ||
				speed > 0 && tacho >= rotateAngle) {
				rotating = false;
				SENSOR_MOTOR.stop();
			}
		}
		if (SENSOR_MOTOR.isStalled()) {
			//whoops
			System.out.println("SENSORARM STALLED!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.exit(0);
		}
	}
}

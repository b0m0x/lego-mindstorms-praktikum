package basis;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class SensorArm {
	public enum SensorArmPosition {
		POSITION_LINE_FOLLOW,
		POSITION_LABYRINTH,
		POSITION_HILL,
		POSITION_BRIDGE,
		POSITION_FRONT,
		POSITION_LEFT,
		POSITION_RIGHT
	}
	private final NXTRegulatedMotor SENSOR_MOTOR = Motor.B;
	private boolean rotating;
	private int rotateAngle;
	

	public SensorArm() {
		rotating = false;
		rotateAngle = 0;
		//SENSOR_MOTOR.resetTachoCount();
		recalibrate();
		SENSOR_MOTOR.setSpeed(100);
		
	}
	
	public void setPosition(SensorArmPosition p) {
		if (isMoving()) {
			return;
		}
		rotateAngle = getPositionAngle(p);
		rotating = true;
		int tacho = SENSOR_MOTOR.getTachoCount();
		
		if (tacho < rotateAngle) {
			SENSOR_MOTOR.forward();
		} else {
			SENSOR_MOTOR.backward();
		}
	}
	
	public void setPositionBlocking(SensorArmPosition p) {
		if (isMoving()) {
			return;
		}
		rotateAngle = getPositionAngle(p);
		int tacho = SENSOR_MOTOR.getTachoCount();
		
		if (tacho < rotateAngle) {
			SENSOR_MOTOR.forward();
			while (tacho < rotateAngle) {
				tacho = SENSOR_MOTOR.getTachoCount();
				
			}
		} else {
			SENSOR_MOTOR.backward();
			while (tacho > rotateAngle) {
				tacho = SENSOR_MOTOR.getTachoCount();
			}
		}
		SENSOR_MOTOR.stop();
	}
	
	/**
	 * recalibrates the sensor arm by moving it to the start position.
	 * caution: blocking!
	 */
	public void recalibrate() {
		System.out.println("Calibrating now");
		SENSOR_MOTOR.setSpeed( SENSOR_MOTOR.getMaxSpeed() );
		SENSOR_MOTOR.forward();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//		}
		while (!SENSOR_MOTOR.isStalled() && SENSOR_MOTOR.isMoving()) {}
		SENSOR_MOTOR.stop();
		SENSOR_MOTOR.resetTachoCount();
		
		System.out.println("Finished");
	}
	
	public boolean isMoving() {
		return SENSOR_MOTOR.isMoving();
	}
	
	
	private int getPositionAngle(SensorArmPosition p) {
		switch(p) {
			case POSITION_LINE_FOLLOW:
			case POSITION_HILL:
				//TODO: INSERT CORRECT VALUE
				return -40;
			case POSITION_LABYRINTH:
			case POSITION_BRIDGE:
			case POSITION_RIGHT:
				return -240;
			case POSITION_FRONT:
				return -150;
			case POSITION_LEFT:
				return -20;
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

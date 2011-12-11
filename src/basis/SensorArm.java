package basis;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

public class SensorArm {
	private final NXTRegulatedMotor SENSOR_MOTOR = Config.SENSOR_MOTOR;
	private boolean rotating;
	private int rotateAngle;
	
	private final int RANGE_BOTTOM = 20; 
	private final int RANGE_TOP = 240;
	private int currentPosition = 0;
	
	public static enum POSITION {
		
		LINE_FOLLOW(-40),
		HILL(20), //TODO: INSERT CORRECT VALUE
		BRIDGE(0),
		RIGHT(-240),
		FRONT(-150),
		LEFT(0);
		
		private int value;
		private POSITION(int v) { value = v; }
		public int getValue() { return value; }
	}
	
	public SensorArm() {
		rotating = false;
		rotateAngle = 0;
		//SENSOR_MOTOR.resetTachoCount();
		//recalibrate();
		SENSOR_MOTOR.setSpeed(100);
	}
	
	public void setPosition(int position, boolean blocking) {
		if (blocking) {
			setPositionBlocking(position);
			return;
		}
		if (isMoving()) return;
		
		rotating = true;
		int tacho = SENSOR_MOTOR.getTachoCount();
		
		if (tacho < position) {
			SENSOR_MOTOR.forward();
		} else {
			SENSOR_MOTOR.backward();
		}
		currentPosition = position;
	}
	
	private void setPositionBlocking(int position) {
		if (isMoving()) return;
		
		int tacho = SENSOR_MOTOR.getTachoCount();
		
		if (tacho < position) {
			SENSOR_MOTOR.forward();
			while (tacho < position) {
				tacho = SENSOR_MOTOR.getTachoCount();
				
			}
		} else {
			SENSOR_MOTOR.backward();
			while (tacho > position) {
				tacho = SENSOR_MOTOR.getTachoCount();
			}
		}
		SENSOR_MOTOR.stop();
		currentPosition = position;
	}
	
	public void setPosition(POSITION p, boolean blocking) {
		setPosition( p.getValue(), blocking );
	}
	
	
	// @param p [0..1]
	public void setPosition(float position, boolean blocking) {
		int newPos = (int) ( (this.RANGE_TOP - this.RANGE_BOTTOM) * position + RANGE_BOTTOM );
		setPosition(newPos, blocking);
	}
	
	public int getPosition() {
		return currentPosition;
	}
	
	public float getPositionFloat() {
		return (RANGE_TOP - RANGE_BOTTOM) / (float)(currentPosition - RANGE_BOTTOM);
	}
	
	/**
	 * recalibrates the sensor arm by moving it to the start position.
	 * caution: blocking!
	 */
	public void recalibrate() {
		System.out.println("Calibrating now");
		SENSOR_MOTOR.setSpeed( SENSOR_MOTOR.getMaxSpeed() );
		SENSOR_MOTOR.forward();

		while ( !SENSOR_MOTOR.isStalled() && SENSOR_MOTOR.isMoving() ) {}
		SENSOR_MOTOR.stop();
		SENSOR_MOTOR.resetTachoCount();
		
		System.out.println("Finished");
	}
	
	public boolean isMoving() {
		return SENSOR_MOTOR.isMoving();
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
			Sound.buzz();
			System.exit(0);
		}
	}
}

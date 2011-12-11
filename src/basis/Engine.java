package basis;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * 
 * Motor A: rechts
 * Motor C: links
 */
public class Engine {
	
	private final NXTRegulatedMotor LEFT = Config.LEFT_MOTOR; 
	private final NXTRegulatedMotor RIGHT = Config.RIGHT_MOTOR;
	private final int ROTATION_SPEED = 300;
	
	//private final int DISTANCE_PER_DEGREE = RADUMPFANG / 360; //mm
	private float UEBERSETZUNG = (90/(300f));
	
	public final int MAX_SPEED = 900;
	private boolean turning = false;
	
	//for distance count
	private boolean driveMaxDist;
	private int maxTachoCount;
	
	public Engine() {
		//LEFT.setAcceleration(8000);
		//RIGHT.setAcceleration(8000);
	}
	
	/**
	 * Stellt Geschindigkeit ein.
	 * 
	 * @param v Geschwindigkeit
	 * 		0 = stehen
	 * 		1 = volle Geschwindigkeit
	 */
	private void setSpeed(float v) {
		if (v < 0 || v > 1) throw new IllegalArgumentException();
		
		int motor_speed = (int) (MAX_SPEED * v);
		
		LEFT.setSpeed(motor_speed);
		RIGHT.setSpeed(motor_speed);	
	}
	
	public void backward(float v) {
		setSpeed(v);
		resetTacho();
		LEFT.backward();
		RIGHT.backward();
	}
	
	public void forward(float v) {
		setSpeed(v);
		resetTacho();
		LEFT.forward();
		RIGHT.forward();
	}
	
	public void stop() {
		setSpeed(0);
		resetTacho();
		LEFT.stop();
		RIGHT.stop();
	}
	
	public void turnLeft(float degree) {
		resetTacho();
		setSpeed(0.3f);
		LEFT.rotate((int)(degree * UEBERSETZUNG * (-1)), true);
		RIGHT.rotate((int)(degree * UEBERSETZUNG), true);
		turning = true;
	}
	
	public void setMaxDist(int tachoCount) {
		maxTachoCount = Math.abs(tachoCount);
		driveMaxDist = true;
	}
	
	/**
	 * Lenken
	 * 	 e =  0: Roboter f�hrt grade aus
	 * 	 e =  1: Roboter f�hrt nach rechts
	 *   e = -1: Roboter f�hrt nach links
	 * @param e Auslenkung [-1..1] 
	 */
	public void bend(float e) {
		float damping_factor = 0.2f;
		if (e < -1f || 1f < e) throw new IllegalArgumentException();
		float speed = (RIGHT.getSpeed() + LEFT.getSpeed()) / 2f;
		
		int left_speed = (int) Math.abs(speed * (1f + e) + damping_factor);
		
		int right_speed = (int) Math.abs(speed * (1f - e) + damping_factor);
		
		//resetTacho();
		RIGHT.setSpeed(right_speed);
		LEFT.setSpeed(left_speed);
		//RIGHT.backward();
		//LEFT.forward();
	}
	
	/**
	 * Linkskurve
	 * @see bend
	 * @param p Auslenkung [0..1] 
	 */
	public void bendLeft(float e) {
		if (e < 0f || 1f < e) throw new IllegalArgumentException();
		bend(e * (-1));
	}
	
	/**
	 * Rechtskurve
	 * @see bend
	 * @param p Auslenkung [0..1] 
	 */
	public void bendRight(float e) {
		if (e < 0 && 1 < e) throw new IllegalArgumentException();
		bend(e);
	}
	
	/**
	 * @return true wenn der Roboter in Bewegung ist
	 */
	public boolean isMoving() {
		return LEFT.isMoving() || RIGHT.isMoving();
	}
	
	private void resetTacho() {
		LEFT.resetTachoCount();
		RIGHT.resetTachoCount();
		turning = false;
	}
	
	public void driveDistance(int distance, int speed, boolean flt) {
		throw new UnsupportedOperationException();
	/*
		double realDistance = 0.0;
		
		resetTacho();
		setSpeed(speed);
		RIGHT.rotate((distance / DISTANCE_PER_DEGREE), true);
		LEFT.rotate((distance / DISTANCE_PER_DEGREE));
		
		//�berpr�fe ob die zur�ckgelegte Distanz der Zieldistanz entspricht
		while (realDistance <= distance) {
			realDistance = Math.abs(RIGHT.getTachoCount()) * DISTANCE_PER_DEGREE;
		}
		
		//bei boolean flt = true, wird der Motor nicht sofort gestoppt sondern dreht sich aus
		if(flt) {
			RIGHT.flt();
			LEFT.flt();
		} else {
			stop();
		}
	*/
	}
	
	public void rotateBlocking(int degrees) {
		resetTacho();
		LEFT.setSpeed(ROTATION_SPEED);
		RIGHT.setSpeed(ROTATION_SPEED);
		
		if (degrees < 0) {
			LEFT.backward();
			RIGHT.forward();
		} else {
			LEFT.forward();
			RIGHT.backward();
		}
		degrees = Math.abs(degrees);
		while (Math.abs(LEFT.getTachoCount()) * UEBERSETZUNG <= degrees) {
			//drehe däumchen
		}
		stop();
	}
	
	public void checkTurning() {
		if (turning && !isMoving()) turning = false;
	}
	
	public void update() {
		checkTurning();
		if (driveMaxDist) {
			System.out.println("" + LEFT.getTachoCount() + "/" + maxTachoCount);
			if (Math.abs(LEFT.getTachoCount()) > maxTachoCount) {
				stop();
				driveMaxDist = false;
			}
		}
		//if ( isMoving() );
	}
}

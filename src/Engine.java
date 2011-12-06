import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * 
 * Motor A: rechts
 * Motor C: links
 */
public class Engine {
	
	private final NXTRegulatedMotor LEFT = Motor.C; 
	private final NXTRegulatedMotor RIGHT = Motor.A;
	private final int QUARTER_TURN = 100;
	private final int DISTANCE_PER_DEGREE = 7;
	
	public final int MAX_SPEED = 900;
	//private boolean turning = false;
	
	
	public Engine() {
		
	}
	
	/**
	 * Lege Geschindigkeit fest.
	 * Die Geschwindigkeit wird dabei als Buchteil der Maximalgeschwindigkeit festgelegt.
	 * @param v Geschwindigkeit in Prozent [0,100]
	 */
	private void setSpeed(int v) {
		if (v < 0 && v > 100) throw new IllegalArgumentException();
		
		float motor_speed = MAX_SPEED * v / 100;
		
		LEFT.setSpeed(motor_speed);
		RIGHT.setSpeed(motor_speed);	
	}
	
	public void forward(int v) {
		setSpeed(v);
		resetTacho();
		LEFT.forward();
		RIGHT.forward();
	}
	
	public void backward(int v) {
		setSpeed(v);
		resetTacho();
		LEFT.backward();
		RIGHT.backward();
	}
	
	public void stop() {
		setSpeed(0);
		resetTacho();
		LEFT.stop();
		RIGHT.stop();
	}

	public void turnLeft(float degree) {
		setSpeed(30);
		resetTacho();
		LEFT.rotate((int)(degree * -1.1), true);
		RIGHT.rotate((int)(degree * 1.1), true);
		//turning = true;
	}
	
	/**
	 * Gibt an wieviel Prozent das linke Rad langsamer sein soll als das rechte.
	 *
	 * Bei 100% blockiert das linke Rad.
	 * Bei 0% f�hrt der Roboter grade aus.
	 * 
	 * @param p st�rke der Kurve [0, 100]
	 */
	public void bendLeft(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		float right_speed = RIGHT.getSpeed();
		float left_speed = right_speed * p / 100f;
		
		resetTacho();
		RIGHT.setSpeed(right_speed);
		LEFT.setSpeed(left_speed);
		RIGHT.forward();
		LEFT.forward();
	}
	
	/**
	 * Gibt an wieviel Prozent das rechte Rad langsamer sein soll als das linke.
	 *
	 * Bei 100% blockiert das rechte Rad.
	 * Bei 0% f�hrt der Roboter grade aus.
	 * 
	 * @param p st�rke der Kurve [0, 100]
	 */
	public void bendRight(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		float left_speed = LEFT.getSpeed();
		float right_speed = left_speed * p / 100f;
		resetTacho();
		RIGHT.setSpeed(right_speed);
		LEFT.setSpeed(left_speed);
		RIGHT.forward();
		LEFT.forward();
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
		//turning = false;
	}
	
	public void driveDistance(int distance, int speed, boolean flt) {
		double realDistance = 0.0;
		
		resetTacho();
		setSpeed(speed);
		RIGHT.rotate((distance / DISTANCE_PER_DEGREE), true);
		LEFT.rotate((distance / DISTANCE_PER_DEGREE));
		
		//�berpr�ft ob die zur�ckgelegte Distanz der Zieldistanz entspricht
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
	}
	
	public void checkTurning() {
		/*
		if (turning) {
			int count = LEFT.getTachoCount() + RIGHT.getTachoCount();
			if (count == QUARTER_TURN) stop();
		}
		*/
	}
	
	public void update() {
		//checkTurning();
		//if ( isMoving() );
	}
}

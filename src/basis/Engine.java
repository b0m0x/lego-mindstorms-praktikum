package basis;
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

	//private final int DISTANCE_PER_DEGREE = RADUMPFANG / 360; //mm
	private float UEBERSETZUNG = 1.05f;
	
	public final int MAX_SPEED = 900;
	private boolean turning = false;
	
	//for distance count
	private boolean driveMaxDist;
	private int maxTachoCount;
	
	public Engine() {
		
	}
	
	/**
	 * Stellt Geschindigkeit ein.
	 * 
	 * @param v Geschwindigkeit
	 * 		0 = stehen
	 * 		1 = volle Geschwindigkeit
	 */
	private void setSpeed(float v) {
		if (v < 0 && v > 1) throw new IllegalArgumentException();
		
		int motor_speed = (int) (MAX_SPEED * v);
		
		LEFT.setSpeed(motor_speed);
		RIGHT.setSpeed(motor_speed);	
	}
	
	public void backward(float v) {
		setSpeed(v);
		resetTacho();
		LEFT.forward();
		RIGHT.forward();
	}
	
	public void forward(float v) {
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
		resetTacho();
		setSpeed(0.3f);
		LEFT.rotate((int)(degree * UEBERSETZUNG * (-1)), true);
		RIGHT.rotate((int)(degree * UEBERSETZUNG), true);
		turning = true;
	}
	
	public void setMaxDist(int tachoCount) {
		maxTachoCount = tachoCount;
		driveMaxDist = true;
	}
	
	/**
	 * Gibt an wieviel Prozent das linke Rad langsamer sein soll als das rechte.
	 *
	 * Bei 100% blockiert das linke Rad.
	 * Bei 0% fŠhrt der Roboter grade aus.
	 * 
	 * @param p stŠrke der Kurve [0, 100]
	 */
	public void bendLeft(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		float right_speed = RIGHT.getSpeed();
		float left_speed = right_speed * p / 100f;
		
		resetTacho();
		RIGHT.setSpeed(right_speed);
		LEFT.setSpeed(left_speed);
		RIGHT.backward();
		LEFT.forward();
	}
	
	/**
	 * Gibt an wieviel Prozent das rechte Rad langsamer sein soll als das linke.
	 *
	 * Bei 100% blockiert das rechte Rad.
	 * Bei 0% fŸhrt der Roboter grade aus.
	 * 
	 * @param p stŠrke der Kurve [0, 100]
	 */
	public void bendRight(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		float left_speed = LEFT.getSpeed();
		float right_speed = left_speed * p / 100f;
		resetTacho();
		RIGHT.setSpeed(right_speed);
		LEFT.setSpeed(left_speed);
		RIGHT.forward();
		LEFT.backward();
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
		
		//ï¿½berprï¿½fe ob die zurï¿½ckgelegte Distanz der Zieldistanz entspricht
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
		LEFT.setSpeed(300);
		RIGHT.setSpeed(300);
		
		if (degrees > 0) {
			LEFT.backward();
			RIGHT.forward();
		} else {
			LEFT.forward();
			RIGHT.backward();
		}
		degrees = Math.abs(degrees);
		while (Math.abs(LEFT.getTachoCount()) <= UEBERSETZUNG * degrees) {
			//drehe dÃ¤umchen
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

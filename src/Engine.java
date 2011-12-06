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
	
	public final int MAX_SPEED = 900;
	private boolean turning = false;
	
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
		LEFT.forward();
		RIGHT.forward();
		resetTacho();
	}
	
	public void backward(int v) {
		setSpeed(v);
		LEFT.backward();
		RIGHT.backward();
		resetTacho();
	}
	
	public void stop() {
		setSpeed(0);
		LEFT.stop();
		RIGHT.stop();
		resetTacho();
	}
	
	public void turn() {
		setSpeed(100);
		LEFT.forward();
		RIGHT.backward();
		resetTacho();
		turning = true;
	}
	
	/**
	 * Gibt an wieviel Prozent das linke Rad langsamer sein soll als das rechte.
	 *
	 * Bei 100% blockiert das linke Rad.
	 * Bei 0% fährt der Roboter grade aus.
	 * 
	 * @param p stärke der Kurve [0, 100]
	 */
	public void bendLeft(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		
		float left_speed = RIGHT.getSpeed() * p / 100f;
		LEFT.setSpeed(left_speed);
		resetTacho();
	}
	
	/**
	 * Gibt an wieviel Prozent das rechte Rad langsamer sein soll als das linke.
	 *
	 * Bei 100% blockiert das rechte Rad.
	 * Bei 0% fährt der Roboter grade aus.
	 * 
	 * @param p stärke der Kurve [0, 100]
	 */
	public void bendRight(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		
		float right_speed = LEFT.getSpeed() * p / 100f;
		RIGHT.setSpeed(right_speed);
		resetTacho();
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
	
	private void correctDirection() {
		
	}
	
	public void checkTurning() {
		if (turning) {
			int count = LEFT.getTachoCount() + RIGHT.getTachoCount();
			if (count == 100) stop();
		}
	}
	
	public void update() {
		checkTurning();
		if ( isMoving() ) correctDirection();
	}
}

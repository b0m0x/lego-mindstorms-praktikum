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
	
	public int MAX_SPEED = 900;
//	private int speed = 0; // [-100%, 100%]

	
	public Engine() {
		
	}
	
	/**
	 * Lege Geschindigkeit fest.
	 * Die Geschwindigkeit wird dabei als Buchteil der Maximalgeschwindigkeit festgelegt.
	 * @param v Geschwindigkeit in Prozent [-100,100]
	 */
	private void setSpeed(int v) {
		float motor_speed = MAX_SPEED * v / 100;
		if (motor_speed < 0) motor_speed *= -1;
		
		LEFT.setSpeed(motor_speed);
		RIGHT.setSpeed(motor_speed);	
	}
	
	public void forward() {
		setSpeed(100);
		LEFT.forward();
		RIGHT.forward();
	}
	
	public void backward() {
		setSpeed(-100);
		LEFT.backward();
		RIGHT.backward();
	}
	
	public void stop() {
		setSpeed(0);
		LEFT.stop();
		RIGHT.stop();
	}
	
	public void turn() {
		setSpeed(100);
		LEFT.forward();
		RIGHT.backward();
	}
	
	public void bendLeft(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		
		float left_speed = RIGHT.getSpeed() * p / 100;
		LEFT.setSpeed(left_speed);
	}
	
	public void bendRight(int p) {
		if (p < 0 && p > 100) throw new IllegalArgumentException();
		
		float right_speed = LEFT.getSpeed() * p / 100;
		RIGHT.setSpeed(right_speed);
	}
	
	
	
	public void update() {
		
	}
}

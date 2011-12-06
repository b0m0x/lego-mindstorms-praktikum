import lejos.nxt.Motor;


public class Engine {
	
	public float MAX_SPEED = Motor.A.getMaxSpeed();
	public int speed = 0; // [-100%, 100%]
	
	private int left = 0;
	
	public Engine() {
		
	}
	
	/**
	 * Lege Geschindigkeit fest.
	 * Die Geschwindigkeit wird dabei als Buchteil der Maximalgeschwindigkeit festgelegt.
	 * @param v Geschwindigkeit in Prozent [-100,100]
	 */
	public void setSpeed(int v) {
		float motor_speed = MAX_SPEED * v / 100;
		if (motor_speed < 0) motor_speed *= -1;
		
		Motor.A.setSpeed(motor_speed);
		Motor.B.setSpeed(motor_speed);	
	}
	
	public void forward() {
		setSpeed(100);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void backward() {
		setSpeed(-100);
		Motor.A.backward();
		Motor.B.backward();
	}
	
	public void stop() {
		setSpeed(0);
		Motor.A.stop();
		Motor.B.stop();
	}
	
	public void turn() {
		setSpeed(100);
		Motor.A.forward();
		Motor.B.backward();
	}
	
	public void update() {
		
	}
}

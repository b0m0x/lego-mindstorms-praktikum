import lejos.nxt.Motor;


public class Engin {
	
	public float MAX_SPEED = Motor.A.getMaxSpeed();
	public int speed = 0; // [-100%, 100%]
	
	private int left = 0;
	
	public Engin() {
		
	}
	
	/**
	 * Lege Geschindigkeit fest
	 * @param v Geschwindigkeit in Prozent [-100,100]
	 */
	public void setSpeed(int v) {
		
	}
	
	public void forward(int v) {
		
	}
	
	public void forward() {
		
	}
	
	public void stop() {
		Motor.A.setSpeed(0);
		Motor.B.setSpeed(0);
	}
	
	public void turn(float drehung) {
		Motor.A.setSpeed(MAX_SPEED);
		Motor.B.setSpeed(MAX_SPEED);
		
		Motor.A.forward();
		Motor.B.backward();
	}
	
	public void update() {
		
	}
}

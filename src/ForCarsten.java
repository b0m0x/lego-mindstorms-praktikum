import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class ForCarsten {
	static NXTRegulatedMotor Right = Motor.A;
	static NXTRegulatedMotor Left = Motor.C;
	//Distanz die pro Grad vom Rad zurückgelegt wird, in Millimeter
	private final int DISTANCE_PER_DEGREE = 7;
	
	//Distance in millimeter
	//lässt den Roboter Millimeter genau fahren. 
	//boolean flt entscheidet, wie der Roboter gestoppt wird
	public void driveDistance(int distance, int speed, boolean flt) {
		double realDistance = 0.0;
		
		Right.resetTachoCount();
		Right.setSpeed(speed);
		Left.resetTachoCount();
		Left.setSpeed(speed);
		Right.rotate((distance / DISTANCE_PER_DEGREE), true);
		Left.rotate((distance / DISTANCE_PER_DEGREE));
		
		//Überprüft ob die zurückgelegte Distanz der Zieldistanz entspricht
		while (realDistance <= distance) {
			realDistance = Math.abs(Right.getTachoCount()) * DISTANCE_PER_DEGREE;
		}
		
		//bei boolean flt = true, wird der Motor nicht sofort gestoppt sondern dreht sich aus
		if(flt) {
			Right.flt();
			Left.flt();
		} else {
			Right.stop();
			Left.stop();
		}
	}
}

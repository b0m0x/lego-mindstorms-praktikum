package behaviour;

import helper.Eieruhr;
import helper.H;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import basis.Config;
import basis.RobotState;
import basis.SensorArm;

/**
 * Der Roboter Schwenkt mit seinem Arm von links nach rechts. Falls er die Linie sieht kehrt die
 * schwenkrichtung um und die Fahrtrichtung des Roboters wird anhand der Auslenkung des Armes angepasst.
 * Ist er Arm weit links fährt der Roboter nach link und umgekehrt.
 * 
 * @author Jupiter
 *
 */
public class LineDirectFollowBehaviour implements RobotBehaviour {
	
	private RobotState robot;
	private boolean onLine = false;
	private Eieruhr memory = new Eieruhr(2000);
	private boolean armMovingRight;
	private final int MIN = -180; // right
	private final int MAX = -5; // left
	private boolean searching = true;
	
	private final int MAX_SPEED = 30;
	private final int MIN_SPEED = 25;
	private final NXTRegulatedMotor ARM = Config.SENSOR_MOTOR;
	
	public LineDirectFollowBehaviour() {}
	
	public void init(RobotState r) {
		robot = r;		
		//missLine();
		ARM.resetTachoCount();
		ARM.setSpeed( 500 );
		ARM.forward();
	}
	
	
	private int motorDirection = 0;
	
	public void update(RobotState r) {
		boolean finished = false;
		robot.forward(MIN_SPEED);
		while (!finished) {
			armSchwenkung();
			if ( !memory.isFinished() || isLine() ) {
//				boolean lokalOnLine = onLine;
//				boolean isLine = isLine();
//				if ( lokalOnLine != isLine) {
//					adjustPath();
//					//if (!(isLine && armMovingRight)) 
//					toggleArmDirection();
//				};
				if ( isLine() ) {
					noLineLevel = 0;
					adjustPath();
					ARM.stop();
					motorDirection = 0;
				} else {
					littleSearch();
				}
			} else {
				missLine();
			};
		}
	}
	
	private int noLineLevel = 0;
	private Eieruhr noLineTimeout = new Eieruhr(120);
	
	private void littleSearch() {
		if (!noLineTimeout.isFinished()) return;
		
		switch (motorDirection) {
		case 0:
			ARM.forward();
			motorDirection = -1;
			noLineTimeout.reset();
			break;
		case -1:
			ARM.backward();
			motorDirection = 1;
			noLineTimeout.reset();
			break;
		case 1:
			//
		}
	}

	private void missLine() {
		Sound.beep();
//		robot.halt();
//		robot.backward(50);
//		while ( !isLine() ) {}
//		memory.reset();
		// TODO erweitern
	}
	
	private void toggleArmDirection() {
		if (armMovingRight) {
			ARM.forward();
			armMovingRight = false;
		} else {
			ARM.backward();
			armMovingRight = true;
		}
	}
	
	
	/**
	 * Ändert die Bewegungsrichtung des Armes, falls dieser seinen Bewegungsspielraum überschreitet.
	 * Diese Funktion sorgt für das allgemeine Wedeln des Armes.
	 */
	private void armSchwenkung() {
		int tacho = ARM.getTachoCount();
		if (tacho <= MIN) {
			ARM.forward();
			armMovingRight = false;
		} else if(tacho >= MAX) {
			ARM.backward();
			armMovingRight = true;
		}
	}
	
	/**
	 * Hier wird die Bewegungsrichtung des Roboters dem Verlauf der Linie angepasst
	 */
	private void adjustPath() {
		float rpos = getRelativeArmPosition();
		float middle = 0.55f;
		if (rpos < middle) {
			robot.bend(-0.45f); //left
		} if (rpos > middle) {
			robot.bend(0.6f); //right
		}
	}
	
	/**
	 * Gibt die Armposition im Bewegungsraum des Armes zurück.
	 * @return Armposition [0..1]
	 */
	private float getRelativeArmPosition() {
		return (ARM.getTachoCount() - MAX) / (float)(MIN - MAX);
	}
	
	/**
	 * @return true wenn der Sensor eine Linie entdeckt
	 */
	public boolean isLine() {
		int color = robot.getLightSensor();
		if (color >= Config.COLOR_BRIGHT) {
			memory.reset();
			this.onLine = true;
			return true;
		} else {
			this.onLine = false;
			return false;
		}
	}
	
}

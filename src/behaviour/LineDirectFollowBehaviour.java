package behaviour;

import helper.Eieruhr;
import helper.H;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import basis.Config;
import basis.RobotState;

/**
 * Der Roboter Schwenkt mit seinem Arm von links nach rechts. Falls er die Linie sieht kehrt die
 * schwenkrichtung um und die Fahrtrichtung des Roboters wird anhand der Auslenkung des Armes angepasst.
 * Ist er Arm weit links f�hrt der Roboter nach link und umgekehrt.
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

	private final NXTRegulatedMotor ARM = Config.SENSOR_MOTOR;
	
	private boolean armHasLine = false;
	private float ARM_RIGHT_BOUND = 1f;
	private float ARM_LEFT_BOUND = 0f;
	
	public void init(RobotState r) {
		robot = r;		
		ARM.resetTachoCount();
		ARM.setSpeed( 50 );
		ARM.forward();
		robot.forward(15);
	}
	
	private void keepArmOnLine() {
		boolean isOnLine = isLine();
		if (armHasLine && !isOnLine) {
			armHasLine = false;
			float armPos = getRelativeArmPosition();
			if (armMovingRight) {
				ARM_RIGHT_BOUND  = armPos;
				ARM.forward();
				armMovingRight = false;
			} else {
				ARM_LEFT_BOUND = armPos;
				ARM.backward();
				armMovingRight = true;
			}
		} else if (!armHasLine && isOnLine) {
			armHasLine = true;
		}
	}
	
	
	public void update(RobotState r) {
		armSchwenkung();
		keepArmOnLine();
		isLine(); // onLine
		robot.bend(1.5f * ((ARM_LEFT_BOUND + ARM_RIGHT_BOUND) / 2 - 0.5f));
	}
	

	private void missLine() {
		Sound.beep();
		// TODO erweitern
	}
	
	/**
	 * �ndert die Bewegungsrichtung des Armes, falls dieser seinen Bewegungsspielraum �berschreitet.
	 * Diese Funktion sorgt f�r das allgemeine Wedeln des Armes.
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
	 * Gibt die Armposition im Bewegungsraum des Armes zur�ck.
	 * @return Armposition [0..1]
	 */
	private float getRelativeArmPosition() {
		return (ARM.getTachoCount() - MAX) / (float)(MIN - MAX);
	}
	
	
	private boolean isLine() {
		int color = robot.getLightSensor();
		if (color >= Config.COLOR_BRIGHT) {
			memory.reset();
			this.onLine = true;
		} else {
			this.onLine = false;
		}
		return this.onLine;
	}
	
}

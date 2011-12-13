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
	private boolean searching = true;
	
	private final int MAX_SPEED = 30;
	private final int MIN_SPEED = 25;
	private final NXTRegulatedMotor ARM = Config.SENSOR_MOTOR;
	private boolean start = true;
	private final float MIDDLE_ARM_POS = 0.55f;
	
	private boolean armHasLine = false;
	private float ARM_RIGHT_BOUND = 1f;
	private float ARM_LEFT_BOUND = 0f;
	
	public void init(RobotState r) {
		robot = r;		
		//missLine();
		ARM.resetTachoCount();
		ARM.setSpeed( 150 );
		ARM.forward();
		r.forward(30);
	}
	
	private void keepArmOnLine() {
		boolean isOnLine = isLine();
		if (armHasLine && !isOnLine) {
			armHasLine = false;
			float armPos = getRelativeArmPosition();
			if (armMovingRight) {
				ARM_RIGHT_BOUND  = armPos;
				ARM.forward();
			} else {
				ARM_LEFT_BOUND = armPos;
				ARM.backward();
			}
		} else if (!armHasLine && isOnLine) {
			armHasLine = true;
		}
	}
	
	
	public void update(RobotState r) {
		/*if ( start ) {
			start(); 
			return;
		}*/
		
		armSchwenkung();
		keepArmOnLine();
		isLine(); // onLine
		robot.bend(1.5f * ((ARM_LEFT_BOUND + ARM_RIGHT_BOUND) / 2 - 0.5f));
		/*
		if ( true || !memory.isFinished() || onLine ) {
			if ( onLine ) {
			} else {
				this.armToDefaultBlocking();
				if (isLineRight) {
					robot.bend(0.5f);
				} else {
					robot.bend(-0.5f);
				}
			}
		} else {
			missLine();
		}
		*/
		
	}
	
	private void start() {
		ARM.stop();
		adjustRobotAndArm();
		start = false;
	}
	
	private void adjustRobotAndArm() {
		int direction = armToDefaultBlocking();
		if (direction == 0) return;
		
		robot.forward(30);
		if (direction == 0) {
			H.sleep(500);
		} else if (direction == -1) {
			// move left
			robot.bend(0.7f);
			while ( !isLine() ) {}
			robot.halt();
		} else {
			// move right
			robot.bend(-0.7f);
			while ( !isLine() ) {}
			robot.halt();
		}
	}
	
	/**
	 * @return -1 if arm turned left, 0 if no turn, 1 if right turn
	 */
	private int armToDefaultBlocking() {
		int direction = 0;
		float pos = getRelativeArmPosition();
		
		if (pos == MIDDLE_ARM_POS) return 0;
		if (pos < MIDDLE_ARM_POS){
			H.p("left");
			direction = -1;
			ARM.backward();
			while (getRelativeArmPosition() < MIDDLE_ARM_POS) {}
		} else {
			direction = 1;
			ARM.forward();
			while (getRelativeArmPosition() > MIDDLE_ARM_POS) {}
		}
		ARM.stop();
		
		return direction;
	}
	


	private Eieruhr noLineTimeout = new Eieruhr(120);
	
	private void littleSearch() {
		if (!noLineTimeout.isFinished()) return;
		
//		switch (motorDirection) {
//		case 0:
//			ARM.forward();
//			motorDirection = -1;
//			noLineTimeout.reset();
//			break;
//		case -1:
//			ARM.backward();
//			motorDirection = 1;
//			noLineTimeout.reset();
//			break;
//		case 1:
//			//
//		}
	}

	private void missLine() {
		Sound.beep();
//		robot.halt();
//		robot.backward(50);
//		while ( !isLine() ) {}
//		memory.reset();
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
	 * Hier wird die Bewegungsrichtung des Roboters dem Verlauf der Linie angepasst
	 */
	private void adjustPath() {
		float rpos = getRelativeArmPosition();
		if (rpos < MIDDLE_ARM_POS) {
			robot.bend(-0.45f); //left
		} if (rpos > MIDDLE_ARM_POS) {
			robot.bend(0.6f); //right
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

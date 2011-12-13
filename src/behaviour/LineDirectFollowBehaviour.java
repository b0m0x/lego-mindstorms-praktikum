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
	private final static int NORMAL_SPEED = 25;
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
	private float lastBend;
	private boolean rightMax;
	private boolean leftMax;
	private boolean levelStart = true;
	
	private RobotBehaviour wallFollower;
	private boolean avoidObstacle = false;
	
	public void init(RobotState r) {
		robot = r;
		ARM.resetTachoCount();
		ARM.setSpeed( 200 );
		ARM.forward();
		robot.forward(NORMAL_SPEED);
		memory.reset();
		leftMax = rightMax = false;
		wallFollower = new WallFollowBehaviour(10);
	}
	
	private void start() {
		robot.forwardBlocking(100, 1000);
		robot.rotate(-90);
		robot.forward(NORMAL_SPEED);
		while (!isLine()) {}
	}

	private void keepArmOnLine() {
		boolean isOnLine = isLine();
		if (armHasLine && !isOnLine) {
			armHasLine = false;
			toggleArmDirection();
			leftMax = rightMax = false;
		} else if (!armHasLine && isOnLine) {
			armHasLine = true;
		}
		if (armHasLine) {
			float armPos = getRelativeArmPosition();
			if (armPos > ARM_RIGHT_BOUND) {
				ARM_RIGHT_BOUND = armPos;
			} else if (armPos < ARM_LEFT_BOUND) {
				ARM_LEFT_BOUND = armPos;
			}
		}
	}
	
	
	private void toggleArmDirection() {
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
	}

	public void update(RobotState r) {
		if (levelStart) {
			start();
			levelStart = false;
			return;
		}
		if (avoidObstacle) {
			wallFollower.update(r);
			if (isLine()) {
				avoidObstacle = false;
				robot.rotate(-60);
				ARM.forward();
				robot.forward(NORMAL_SPEED);
				armMovingRight = false;
			}
			return;
		}
		if (!avoidObstacle && robot.crashedIntoWall()) {
			ARM.stop();
			robot.backwardBlocking(NORMAL_SPEED, 1000);
			robot.rotate(-90);
			wallFollower.init(robot);
			avoidObstacle = true;
			return;
		}
		if (memory.isFinished()) {
			memory.reset();
			robot.forward(NORMAL_SPEED);
		}
		if (leftMax && rightMax) {
			robot.halt();
			ARM.stop();
			robot.backwardBlocking(30, 1500);
			robot.forward(20);
			leftMax = rightMax = false;
		}
		armSchwenkung();
		keepArmOnLine();
		float strength = 1.5f * ((ARM_LEFT_BOUND + ARM_RIGHT_BOUND) / 2f - 0.5f);
		if (Math.abs(strength - lastBend) > 0.0001f) {
			if (Config.LEFT_MOTOR.getSpeed() + Config.LEFT_MOTOR.getSpeed() < 2 * NORMAL_SPEED) {
				robot.forward(NORMAL_SPEED);
			}
			robot.bend(strength);
			lastBend = strength;
		}
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
			rightMax = true;
		} else if(tacho >= MAX) {
			ARM.backward();
			armMovingRight = true;
			leftMax = true;
		}
	}
	
	/**
	 * Gibt die Armposition im Bewegungsraum des Armes zur�ck.
	 * @return Armposition [0..1]
	 */
	private float getRelativeArmPosition() {
		return 1f - (float) (Math.cos( ARM.getTachoCount() * Math.PI / 180f) / 2f + 0.5f);
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

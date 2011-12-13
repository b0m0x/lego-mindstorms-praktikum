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
 * Ist er Arm weit links fährt der Roboter nach link und umgekehrt.
 * 
 * @author Jupiter
 *
 */
public class LineDirectFollowBehaviour implements RobotBehaviour {
	
	private RobotState robot;
	private boolean onLine = false;
	private Eieruhr memory = new Eieruhr(1000);
	private boolean armMovingRight;
	private final int MIN = -180; // right
	private final int MAX = -5; // left
	private boolean searching = true;
	
	private final int MAX_SPEED = 30;
	private final int MIN_SPEED = 15;
	private final NXTRegulatedMotor ARM = Config.SENSOR_MOTOR;
	private WallFollowBehaviour wallFollower = new WallFollowBehaviour(10);
	private BumpOffWallAndRotateBehaviour bumper = new BumpOffWallAndRotateBehaviour();
	
	public LineDirectFollowBehaviour() {}
	
	public void init(RobotState r) {
		robot = r;		
		//missLine();
		ARM.resetTachoCount();
		ARM.setSpeed( 200 );
		ARM.forward();
		wallFollower.init(r);
		bumper.init(r);
		robot.forward(MIN_SPEED);
	}
	
	
	private int motorDirection = 0;
	
	public void update(RobotState r) {
		if ( robot.crashedIntoWall() ) {
			ARM.stop();
			Sound.buzz();
			crashed();
			ARM.forward();
			robot.forward(MIN_SPEED);
			return;
		}
		
		armSchwenkung();
		if ( !memory.isFinished() || isLine() ) {
			boolean lokalOnLine = onLine;
			boolean isLine = isLine();
			if ( lokalOnLine != isLine) {
				adjustPath();
				//if (!(isLine && armMovingRight)) 
				if (!armMovingRight && isLine) toggleArmDirection();
				if (!isLine) toggleArmDirection();
			};

		} else {
			//missLine();
			H.sleep(200); 
		};
	}
	
	private void crashed() {
		bumper.update(robot);
		while (!isLine()) {
			this.wallFollower.update(robot);
		}
	}
	
	private boolean backward = false;
	private boolean backwardFirst = true;
	private void missLine() {
//		//Sound.beep();
//		if (backwardFirst) {
//			backwardFirst = false;
//			backward = true;
//			robot.halt();
//			
//			//robot.bend(0);
//			Sound.buzz();
//			//Config.A.
//			//while(!isLine()) {}
//			robot.backward(20);
//			
//		}
		ARM.stop();
		Sound.buzz();
		robot.halt();
		robot.backward(10);
		while ( !isLine() ) {}
		robot.halt();
		ARM.forward();
		robot.forward(MIN_SPEED);
		 //TODO erweitern
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
		float middle = 0.6f;
		if (rpos < middle) {
			robot.bend(-0.60f); //left
		} if (rpos > middle) {
			robot.bend(0.50f); //right
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

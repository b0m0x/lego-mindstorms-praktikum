package behaviour;

import helper.Eieruhr;
import helper.H;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import basis.Config;
import basis.RobotState;
import basis.SensorArm;
import basis.SensorArm.POSITION;

/**
 * Der Roboter Schwenkt mit seinem Arm von links nach rechts. Falls er die Linie sieht kehrt die
 * schwenkrichtung um und die Fahrtrichtung des Roboters wird anhand der Auslenkung des Armes angepasst.
 * Ist er Arm weit links fährt der Roboter nach link und umgekehrt.
 * 
 * @author Jupiter
 *
 */
public class LineDirectFollowBehaviour implements RobotBehaviour {
	
	private SensorArm arm;
	private RobotState robot;
	private boolean onLine = false;
	private Eieruhr memory = new Eieruhr(1000);
	private boolean armMovingRight;
	private final int MIN = -180; // right
	private final int MAX = -5; // left
	private boolean searching = true;
	
	private final int MAX_SPEED = 30;
	private final int MIN_SPEED = 15;
	
	public LineDirectFollowBehaviour() {}
	
	public void init(RobotState r) {
		robot = r;		
		arm = robot.getSensorArm();
		//missLine();
		Config.SENSOR_MOTOR.resetTachoCount();
		Config.SENSOR_MOTOR.setSpeed( 500 );
		Config.SENSOR_MOTOR.forward();
		H.p(Config.SENSOR_MOTOR.getMaxSpeed());
	}
	
	public void update(RobotState r) {
		boolean finished = false;
		robot.forward(MIN_SPEED);
		while (!finished) {
			armSchwenkung();
			
			if ( true || !memory.isFinished() ) {
				if ( onLine != isLine() ) {
					adjustPath();
					toggleArmDirection();
				};
			} else {
				missLine();
			};
		}
	}
	
	private void missLine() {
		Sound.buzz();
//		robot.halt();
//		robot.backward(50);
//		while ( !isLine() ) {}
//		memory.reset();
		// TODO erweitern
	}
	
	private void toggleArmDirection() {
		if (armMovingRight) {
			Config.SENSOR_MOTOR.forward();
			armMovingRight = false;
		} else {
			Config.SENSOR_MOTOR.backward();
			armMovingRight = true;
		}
	}
	
	
	/**
	 * Ändert die Bewegungsrichtung des Armes, falls dieser seinen Bewegungsspielraum überschreitet.
	 * Diese Funktion sorgt für das allgemeine Wedeln des Armes.
	 */
	private void armSchwenkung() {
		int tacho = Config.SENSOR_MOTOR.getTachoCount();
		if (tacho <= MIN) {
			Config.SENSOR_MOTOR.forward();
			armMovingRight = false;
		} else if(tacho >= MAX) {
			Config.SENSOR_MOTOR.backward();
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
			robot.bend(-0.4f); //left
		} if (rpos > middle) {
			robot.bend(0.4f); //right
		}
	}
	
	/**
	 * Gibt die Armposition im Bewegungsraum des Armes zurück.
	 * @return Armposition [0..1]
	 */
	private float getRelativeArmPosition() {
		return (Config.SENSOR_MOTOR.getTachoCount() - MAX) / (float)(MIN - MAX);
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

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
 * Ist er Arm weit links f�hrt der Roboter nach link und umgekehrt.
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
	
	public LineDirectFollowBehaviour() {}
	
	public void init(RobotState r) {
		robot = r;		
		arm = robot.getSensorArm();
		//missLine();
		Config.SENSOR_MOTOR.resetTachoCount();
		Config.SENSOR_MOTOR.setSpeed( 200 );
		Config.SENSOR_MOTOR.forward();
		H.p(Config.SENSOR_MOTOR.getMaxSpeed());
	}
	
	public void update(RobotState r) {
		boolean finished = false;
		robot.forward(15);
		while (!finished) {
			armSchwenkung();
			H.sleep(100);
			
			memory.reset(); // TODO
			// !memory.isFinished()
			if ( true ) {
				if ( onLine != isLine() ) {
					adjustPath();
					toggleArmDirection();
				};
			} else {
				//missLine();
				Sound.buzz();
			};
		}
	}
	
	private void missLine() {
		robot.halt();
		robot.backward(50);
		while ( !isLine() ) {}
		memory.reset();
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
	 * �ndert die Bewegungsrichtung des Armes, falls dieser seinen Bewegungsspielraum �berschreitet.
	 * Diese Funktion sorgt f�r das allgemeine Wedeln des Armes.
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
		float ungenauigkeit = 0.0f;
		
		float rpos = getRelativeArmPosition();
		H.p("#", rpos);
		float middle = 0.55f;
		if (rpos > middle + ungenauigkeit) {
			robot.bend(-0.7f); //right
		} if (rpos < middle - ungenauigkeit) {
			robot.bend(0.7f); //left
		} else {
			robot.bend(0);
		}
	}
	
	/**
	 * Gibt die Armposition im Bewegungsraum des Armes zur�ck.
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

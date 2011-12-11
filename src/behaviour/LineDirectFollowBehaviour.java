package behaviour;

import helper.Eieruhr;
import lejos.nxt.LightSensor;
import basis.Config;
import basis.RobotState;
import basis.SensorArm;
import basis.SensorArm.POSITION;

/**
 * Der Roboter Schwenkt mit seinem Arm von links nach rechts. Falls er die Linie sieht kehrt die
 * schwenkrichtung um und die Fahrtrichtung des Roboters wird anhand der Auslenkung des Armes angepasst.
 * Ist er Arm weit links fŠhrt der Roboter nach link und umgekehrt.
 * 
 * @author Jupiter
 *
 */
public class LineDirectFollowBehaviour {
	
	private SensorArm arm;
	private RobotState robot;
	private boolean onLine = false;
	private Eieruhr memory = new Eieruhr(700);
	private boolean armMovingRight;
	
	private final int MAX_AUSLENKUNG = POSITION.FRONT.getValue();
	private final int MIN_AUSLENKUNG = POSITION.LEFT.getValue();
	private boolean searching = true;
	
	public LineDirectFollowBehaviour() {
	}
	
	public void init(RobotState r) {
		robot = r;
		robot.forward(50);		
		arm = robot.getSensorArm();
		arm.setPosition(POSITION.LEFT, false);
		search();
	}
	
	//TODO was bei maximalauslenkung
	
	public void update(RobotState r) {
		if (!memory.isFinished() && !searching) {
			follow();
		} else {
			search();
		};
	}
	
	private void follow() {
		if ( onLine != isLine() ) {
			adjustPath();
			toggleArmDirection();
		}
	}
	
	private void search() {
		//TODO implement
		searching = false;
	}
	
	private void toggleArmDirection() {
		if (armMovingRight) {
			arm.setPosition(POSITION.LEFT, false);
			armMovingRight = false;
		} else {
			arm.setPosition(POSITION.FRONT, false);
			armMovingRight = true;
		}
	}
	
	private void adjustPath() {
		float ungenauigkeit = 0.1f;
		
		float rpos = getRelativePosition();
		if (rpos > 0.5f + ungenauigkeit) {
			robot.bend(0.2f); //right
		} if (rpos < 0.5f - ungenauigkeit) {
			robot.bend(-0.2f); //left
		} else {
			robot.bend(0);
		}
	}
	
	private float getRelativePosition() {
		// TODO auf Richtigkeit ŸberprŸfen
		return (arm.getPosition() - MIN_AUSLENKUNG) / (float)(MAX_AUSLENKUNG - MIN_AUSLENKUNG);
	}

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

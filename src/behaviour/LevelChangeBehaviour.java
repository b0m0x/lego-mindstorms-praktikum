package behaviour;

import basis.Config;
import basis.RobotState;

public class LevelChangeBehaviour implements RobotBehaviour {
	
	private final static int DELAY = 5000; //5 sek abstand zwischen messungen 
	private final static int COLOR_CODE = 45; 
	private final static int COLOR_GROUND = 25;
	private long lastCodeSeen;
	private boolean codeLock;
	
	public void init(RobotState r) {
		lastCodeSeen = System.currentTimeMillis();
		codeLock = true;
	}

	public void update(RobotState r) {
		
		if (System.currentTimeMillis() > lastCodeSeen + DELAY) {
			int value = r.getLightSensor();
			if (value >= COLOR_CODE && !codeLock) {
				if (checkFor2ndLine(r)) {
					changeToNextLevel(r);
				}
			}
			if (codeLock && value <= COLOR_GROUND) {
				codeLock = false;
			}
		}
	}
	
	/**
	 * checks if there is a second line (means levelchange)
	 * caution: blocks
	 * @return
	 */
	private boolean checkFor2ndLine(RobotState r) {
		r.forward(30, 500);
		while (r.isMoving()) {
			int value = r.getLightSensor();
			if (codeLock && value <= COLOR_GROUND) {
				codeLock = false;
			}
			if (!codeLock && value >= COLOR_CODE) { //2nd Code found
				changeToNextLevel(r);
				codeLock = true;
				return true;
			}
		}
		//get back
		r.backward(30, 500);
		return false;
	}

	/** 
	 * changes to the next level
	 */
	private void changeToNextLevel(RobotState r) {
		r.clearBehaviours();
		Config.currentBehaviour++;
		RobotBehaviour newBehaviour = Config.behaviours[Config.currentBehaviour];
		System.out.println("Levelchange to " + Config.menuItems[Config.currentBehaviour]);
		r.addBehaviour(newBehaviour);
		r.init();
		if (!(newBehaviour instanceof LineFollowBehaviour)) {
			r.addBehaviour(this);
		}
	}
}

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
				if (Config.currentBehaviour % 2 == 0) {
					//every 2nd levelchang is only 1 line
					changeToNextLevel(r);
				} else if (checkFor2ndLine(r)) {
					//the others are 2
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
				codeLock = true;
				return true;
			}
		}
		//get back
		r.backwardBlocking(30, 500);
		r.forward(50);
		return false;
	}

	/** 
	 * changes to the next level
	 */
	private void changeToNextLevel(RobotState r) {
		r.clearBehaviours();
		Config.currentBehaviour++;
		for (RobotBehaviour newBehaviour : Config.behaviours[Config.currentBehaviour]) {
			r.addBehaviour(newBehaviour);
		}
		System.out.println("Levelchange to " + Config.menuItems[Config.currentBehaviour]);
		
		r.init();
	}
}

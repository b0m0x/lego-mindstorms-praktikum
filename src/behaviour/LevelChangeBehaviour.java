package behaviour;

import helper.Eieruhr;
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
		int value = r.getLightSensor();		
		if (System.currentTimeMillis() > lastCodeSeen + DELAY) {			
			if (value >= COLOR_CODE && !codeLock) {
				codeLock = true;
				if (Config.currentBehaviour % 2 == 0) {
					//every 2nd levelchang is only 1 line
					r.changeToNextLevel();
				} else if (checkFor2ndLine(r)) {
					//the others are 2
					r.changeToNextLevel();
				}
				lastCodeSeen = System.currentTimeMillis();
			}			
		}
		if (codeLock && value <= COLOR_GROUND) {
			codeLock = false;
		}
	}
	
	/**
	 * checks if there is a second line (means levelchange)
	 * caution: blocks
	 * @return
	 */
	private boolean checkFor2ndLine(RobotState r) {
		r.halt();
		Eieruhr t = new Eieruhr(3000);
		r.forward(30);
		while (!t.isFinished()) {
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

}

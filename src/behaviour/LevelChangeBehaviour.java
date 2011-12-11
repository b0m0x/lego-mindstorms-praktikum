package behaviour;

import basis.Config;
import basis.RobotState;

public class LevelChangeBehaviour implements RobotBehaviour {
	
	private final static int DELAY = 5000; //5 sek abstand zwischen messungen 
	private final static int COLOR_CODE = 40; 
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
				r.clearBehaviours();
				Config.currentBehaviour++;
				r.addBehaviour(Config.behaviours[Config.currentBehaviour]);
				r.init();
				r.addBehaviour(this);
				
			}			
		}
	}
}

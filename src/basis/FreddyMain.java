package basis;

import behaviour.LevelChangeBehaviour;
import behaviour.LineFollowBehaviour;
import behaviour.RobotBehaviour;
import lejos.util.TextMenu;

public class FreddyMain {
	
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		TextMenu menu = new TextMenu(Config.menuItems);
		Config.currentBehaviour = menu.select();
		RobotBehaviour b = Config.behaviours[Config.currentBehaviour]; 
		r.addBehaviour(b);
		if (!(b instanceof LineFollowBehaviour)) {
			r.addBehaviour(new LevelChangeBehaviour());
		}
		r.init();
		while (true) {
			r.update();
		}
		
	}
}
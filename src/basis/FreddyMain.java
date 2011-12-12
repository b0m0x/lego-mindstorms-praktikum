package basis;

import behaviour.RobotBehaviour;
import lejos.nxt.comm.RConsole;
import lejos.util.TextMenu;

public class FreddyMain {
	
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		TextMenu menu = new TextMenu(Config.menuItems);
		Config.currentBehaviour = menu.select();
		//if User selects 0, bluetooth start
		if(Config.currentBehaviour == 0) {
			BTRaceStartClient btraceclient = new BTRaceStartClient();	 
			//btraceclient.enterLevel();
		}
		for (RobotBehaviour b : Config.behaviours[Config.currentBehaviour]) { 
			r.addBehaviour(b);
		}
		r.init();
		while (true) {
			r.update();
		}
		
	}
}
package basis;

import behaviour.BridgeBehaviour;
import behaviour.EndBossBehaviour;
import behaviour.GateBehaviour;
import behaviour.LabyrinthBehaviour;
import behaviour.LineFollowBehaviour;
import behaviour.RobotBehaviour;
import behaviour.TurnTableBehaviour;
import behaviour.WallFollowBehaviour;
import behaviour.XMASSearchBehaviour;
import lejos.util.TextMenu;

public class FreddyMain {
	
	private final static String[] menuItems = {
			"Labyrinth",
			"Hill",
			"Line", 
			"Hanging Bridge",
			"line2",
			"Gate",
			"XMAS Seach",
			"Bridge",
			"Line3",
			"Rolls",
			"Line4",
			"Gate",
			"Line5",
			"TurnTable",
			"Endboss"			
	};
	
	private final static RobotBehaviour[] behaviours = {
			new LabyrinthBehaviour(),
			new WallFollowBehaviour(20),
			new LineFollowBehaviour(),
			new BridgeBehaviour(),
			new LineFollowBehaviour(),
			new GateBehaviour(),
			new XMASSearchBehaviour(),
			new BridgeBehaviour(),
			new LineFollowBehaviour(),
			new WallFollowBehaviour(10),
			new LineFollowBehaviour(),
			new GateBehaviour(),
			new LineFollowBehaviour(),
			new TurnTableBehaviour(),
			new EndBossBehaviour()
	};
	
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		TextMenu menu = new TextMenu(menuItems);
		int sel = menu.select();
		r.addBehaviour(behaviours[sel]);
		r.init();
		while (true) {
			r.update();
		}
		
	}
}
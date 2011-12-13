package test;

import lejos.nxt.Button;
import basis.RobotState;
import behaviour.TurnTableBehaviour;

public class TurnTableTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		r.addBehaviour(new TurnTableBehaviour());
		
		r.init();
		while( !Button.ENTER.isPressed() ) {
			r.update();
		}
	}
}
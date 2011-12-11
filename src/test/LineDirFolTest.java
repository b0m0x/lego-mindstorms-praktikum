package test;

import lejos.nxt.Button;
import basis.RobotState;
import behaviour.LineDirectFollowBehaviour;

public class LineDirFolTest {
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		r.addBehaviour(new LineDirectFollowBehaviour());
		
		r.init();
		while( !Button.ENTER.isPressed() ) {
			r.update();
		}
	}
}
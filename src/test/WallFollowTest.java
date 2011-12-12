package test;

import lejos.nxt.Button;
import basis.Config;
import basis.RobotState;
import behaviour.WallFollowBehaviour;

public class WallFollowTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		RobotState r = RobotState.getInstance();

		//r.addBehaviour(new WallFollowBehaviour(10));
		r.init();
		r.forward(50);
		
		int i = 0;
		while(!Button.ENTER.isPressed()) {
			r.getUltraSonic();
			r.bend((i - 60)  / 125f );
			i++;
			i%=125;
		}
		
	}
}
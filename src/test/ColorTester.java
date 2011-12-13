package test;

import helper.H;
import lejos.nxt.Button;
import basis.RobotState;

public class ColorTester {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		RobotState r = RobotState.getInstance();
		r.init();
		
		while (!Button.ENTER.isPressed()) {
			int color = r.getLightSensor();
			H.p("color:", color);
		}
	}
}
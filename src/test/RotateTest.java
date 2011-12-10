package test;

import basis.RobotState;;

public class RotateTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("RotateTest:");
		RobotState r = RobotState.getInstance();
		
		r.rotate90(-1);
	}
}
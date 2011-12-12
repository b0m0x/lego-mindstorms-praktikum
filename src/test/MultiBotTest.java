package test;

import lejos.nxt.Button;
import basis.*;
import behaviour.MultiBotBehaviour;

public class MultiBotTest {
	
	
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		r.addBehaviour(new MultiBotBehaviour(3));
		r.init();
		
		while(true) { 
			r.update();
			}
		}
	
	
}

package test;

import basis.*;

public class MultiBotTest {
	
	
	public static void main(String[] args) {
		RobotState r = RobotState.getInstance();
		MultiBotStrategy m = new MultiBotStrategy();
		
		while(true) {
		if(r.crashedIntoWall()) {
			System.out.println(m.crashedIntoBot);
			m.setCrashedIntoBot();
			}
		
		if(!r.isMoving()) {
		r.forward(20);
		m.setStrategy(3);
			}
		
		if(m.crashedIntoBot) {
			m.policy(r);
			m.crashedIntoBot = false;
			}
		}
	}
	
}

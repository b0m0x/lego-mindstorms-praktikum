package behaviour;

import basis.MultiBotStrategy;
import basis.RobotState;

public class MultiBotTestBehaviour implements RobotBehaviour {
	
	private MultiBotStrategy m;


	public void init(RobotState r) {
		 m = new MultiBotStrategy();
		 m.setStrategy(1);
		
	}

	public void update(RobotState r) {
		// TODO Auto-generated method stub
		if(!r.isMoving()) {
			r.forward(30);
		}
		
		if(r.crashedIntoWall()) {
			m.crashedIntoBot = true;
		}
		
		if(m.crashedIntoBot) {
			m.policy(r);
			m.crashedIntoBot = false;
		}
		
		
	}
		
		
	}
	

package behaviour;

import basis.RobotState;
import basis.SensorArm.POSITION;

public class BridgeBehaviour implements RobotBehaviour {
	private static final int BRIDGE_SPEED = 30;
	
	private static final int COLOR_BRIDGE = 31;
	private static final int COLOR_OFFTRACK = 25;
	
	private boolean isOnEdge;
	
	private boolean reachedTop;
	
	private RobotBehaviour wallFollower;
	
	public BridgeBehaviour() {
		wallFollower = new WallFollowBehaviour(5);
		isOnEdge = reachedTop = false;
	}	
	
	public void init(RobotState r) {
		r.setSensorArmPosition(POSITION.FRONT);
		r.forward(10);
		wallFollower.init(r);
	}

	public void update(RobotState r) {
		if (!reachedTop && r.getUltraSonic() == 255) {
			reachedTop = true;
			r.forwardBlocking(50, 1500);
		}
		if (!reachedTop) {
			wallFollower.update(r);
			return;
		}
		//int wallDist = r.getUltraSonic();
		int sensor = r.getLightSensor();
		System.out.println(sensor);
		if (sensor <= COLOR_OFFTRACK && !isOnEdge) {
			//r.halt();
			r.forward(BRIDGE_SPEED);
			r.bend(-0.5f);
			isOnEdge = true;
			System.out.println("Weg da");
		} else if (sensor >= COLOR_BRIDGE && isOnEdge) {
			r.forward(BRIDGE_SPEED);
			r.bend(0.5f);
			isOnEdge = false;
			System.out.println("safe");
		}

	}

}

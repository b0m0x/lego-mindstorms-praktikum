package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;

public class BridgeBehaviour implements RobotBehaviour {

	private static final int COLOR_BRIDGE = 34;
	private static final int COLOR_OFFTRACK = 27;
	
	boolean isOnEdge;
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		//r.addBehaviour(new WallFollowBehaviour(10));
		isOnEdge = false;
		//System.out.println("Init");
		r.forward(10);
	}

	public void update(RobotState r) {
		//int wallDist = r.getUltraSonic();
		int sensor = r.getLightSensor();
		System.out.println(sensor);
		if (sensor <= COLOR_OFFTRACK && !isOnEdge) {
			//r.halt();
			r.forward(20);
			r.bend(-0.5f);
			isOnEdge = true;
			System.out.println("Weg da");
		} else if (sensor >= COLOR_BRIDGE && isOnEdge) {
			r.forward(20);
			r.bend(0.5f);
			isOnEdge = false;
			System.out.println("safe");
		}

	}

}

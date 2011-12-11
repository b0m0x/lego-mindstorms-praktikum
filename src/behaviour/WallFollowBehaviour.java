package behaviour;

import basis.RobotState;
import basis.SensorArm.POSITION;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;
	private int lastDist;

	public WallFollowBehaviour(int distance) {
		WALL_DISTANCE = distance;
		lastDist = 256;
	}
	
	public void init(RobotState r) {
		r.forward(50);
	}

	public void update(RobotState r) {
		int dist = r.getUltraSonic();
		if (dist == lastDist) {
			return;
		}
		if (dist == 255) {
			r.bend(0.5f);
			return;
		}
		float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
		r.bend(strength);
		lastDist = dist;
	}

}

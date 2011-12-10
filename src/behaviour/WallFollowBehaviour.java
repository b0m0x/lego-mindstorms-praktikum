package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;

	public WallFollowBehaviour(int distance) {
		WALL_DISTANCE = distance;
	}
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_LABYRINTH);
		r.forward(50);
		
	}

	public void update(RobotState r) {
		if (r.isSensorArmMoving()) {
			return;
		}
		int dist = r.getUltraSonic();
		if (dist == 255) {
			r.halt();
			r.rotate(45);
			r.forward(50);
			return;
		}
		float strength = Math.min(Math.max((dist - WALL_DISTANCE) / 30.f, -1f), 1f);		
		r.bend(strength);
	}

	public boolean isNextLevel() {
		// TODO Auto-generated method stub
		return false;
	}

}

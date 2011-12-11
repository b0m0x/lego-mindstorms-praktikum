package behaviour;

import basis.RobotState;
import basis.SensorArm.POSITION;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;

	public WallFollowBehaviour(int distance) {
		WALL_DISTANCE = distance;
	}
	
	public void init(RobotState r) {
		//r.setSensorArmPosition(POSITION.RIGHT);
		r.forward(50);
	}

	public void update(RobotState r) {
		if (r.isSensorArmMoving()) {
			return;
		}
		int dist = r.getUltraSonic();
		if (dist == 255) {
			/*
			r.forwardBlocking(50, 500);
			r.halt();
			r.rotate(90);
			r.forwardBlocking(50, 1000);
			r.halt();
			r.getUltraSonic();
			r.forward(50);*/
			r.bend(0.5f);
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

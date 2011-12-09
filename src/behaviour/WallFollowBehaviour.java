package behaviour;

import basis.RobotState;

public class WallFollowBehaviour implements RobotBehaviour {
	private final int WALL_DISTANCE;

	public WallFollowBehaviour(int distance) {
		WALL_DISTANCE = distance;
	}
	
	public void init(RobotState r) {
		// TODO Auto-generated method stub
		
	}

	public void update(RobotState r) {
		int dist = r.getUltraSonic();
		if (dist == 255) {
			r.removeBehaviour(this);
		}
		r.bend(dist / WALL_DISTANCE - 1);		
	}

	public boolean isNextLevel() {
		// TODO Auto-generated method stub
		return false;
	}

}

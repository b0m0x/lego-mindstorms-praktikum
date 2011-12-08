package behaviour;

import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;

public class LabyrinthBehaviour implements RobotBehaviour {
	private static final int WALL_DISTANCE = 10;
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_LABYRINTH);
		r.forward(30);
	}

	public void update(RobotState r) {
		int realWallDist = r.getUltraSonic();
		
		if (realWallDist > WALL_DISTANCE) { //entfernt, lenke nach rechts
			r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		} else {
			r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		}
		
	}

}

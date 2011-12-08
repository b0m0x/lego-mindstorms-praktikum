package behaviour;

import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;

public class LabyrinthBehaviour implements RobotBehaviour {
	private static final int WALL_DISTANCE = 10;
	
	enum pos { front, right };
	
	private pos akt_pos;
	private pos next_pos;
	
	private RobotState state;
	public void init(RobotState r) {
		state = r;
		
		r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		r.halt();
		akt_pos = pos.front;
	}

	public void update(RobotState r) {
		//int realWallDist = r.getUltraSonic();
		
		switch (akt_pos) {
		case front: front();
		case right: right();
		default: 
			System.out.println("bababa");
		}
		
		/*
		r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		r.isSensorArmMoving();
		if (realWallDist > WALL_DISTANCE) { //entfernt, lenke nach rechts
			r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		} else {
			r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		}
		*/
	}
	
	private void front() {
		int entfernung = state.getUltraSonic();
		if (entfernung < 10) Sound.buzz();
		//next_pos = pos.right;
	}
	
	private void right() {
		
	}

}

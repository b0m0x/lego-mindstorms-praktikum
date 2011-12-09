package behaviour;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBehaviour implements RobotBehaviour {
	
	//private static final int WALL_DISTANCE = 10;
	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 15;
	
	enum pos {right, front};
	private pos akt_pos;
	RobotState state;
	Eieruhr uhr = new Eieruhr(2000);
	Messwerte messwerte = new Messwerte();
	
	public void init(RobotState r) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		r.forward(30);
		state = r;
		akt_pos = pos.front;
		uhr.reset();
	}
	
	public void update(RobotState r) {
	
		if (!state.isSensorArmMoving()) {
			int distanz = state.getUltraSonic();
			messwerte.add(distanz);
			
			if (uhr.isFinished()) nextAction();
		}
		
		
		/*
		int realWallDist = r.getUltraSonic();
		
		if (realWallDist > WALL_DISTANCE) { //entfernt, lenke nach rechts
		r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		} else {
		r.bendLeft(30 * WALL_DISTANCE / realWallDist);
		}
		*/
	
	}
	
	private void nextAction() {
		int distanz = messwerte.getAverage();
		messwerte.clear();
		
		switch(akt_pos) {
			case front: front(distanz);
			case right: right(distanz);
		}
	}
	
	public void front(int distanz) {
		if (distanz > FRONT_DISTANZ) {
			state.setSensorArmPosition(SensorArmPosition.POSITION_RIGHT);
			akt_pos = pos.right;
		} else {
			Sound.beep();
		}
	}
	
	public void right(int distanz) {
		if (distanz > RIGHT_DISTANZ) {
			state.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
			akt_pos = pos.front;
		} else {
			Sound.beep();
		}
	}

	@Override
	public boolean isNextLevel() {
		// TODO Auto-generated method stub
		return false;
	}

}
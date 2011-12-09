package behaviour;


import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBehaviour implements RobotBehaviour {
	
	private int debugging_counter = 0;
	
	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 15;
	private enum pos {right, front};
	
	private pos akt_pos;
	private RobotState state;
	private Eieruhr uhr = new Eieruhr(2000);
	private Messwerte messwerte = new Messwerte(50);
	
	
	public void init(RobotState r) {
		System.out.println("Start Labyrinth!");
		
		r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		//r.forward(30);
		state = r;
		akt_pos = pos.front;
		uhr.reset();
	}
	
	public void update(RobotState r) {
		if (!state.isSensorArmMoving()) {
			int distanz = state.getUltraSonic();
			//System.out.println(debuging_counter++ + " " + distanz);
			messwerte.add(distanz);
			
			if (uhr.isFinished()) nextAction();
		}
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

}
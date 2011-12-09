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
	private enum pos {right, front, start};
	
	private pos akt_pos;
	private RobotState state;
	private Eieruhr uhr = new Eieruhr(100);
	private Messwerte messwerte = new Messwerte(50);
	
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		
		//r.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
		state = r; 
		akt_pos = pos.start;
		uhr.reset();
		state.forward(100);
	}
	
	public void update(RobotState r) {
		if (!state.isSensorArmMoving()) {
			int distanz = state.getUltraSonic();
			//System.out.println(debugging_counter++ + " " + distanz);
			messwerte.add(distanz);
			
			if (uhr.isFinished()) nextAction();
			
		}
	}
	
	private void nextAction() {
		H.p("next action");
		int distanz = messwerte.getAverage();
		messwerte.clear();
		
		H.p("ausgabe", distanz);
		if (distanz < FRONT_DISTANZ) {
			Sound.beep();
			state.halt();
		}
		/*
		switch(akt_pos) {
			case front: front(distanz); break;
			case right: right(distanz); break;
			case start: start(distanz); break;
		}
		*/
	}
	
	private void start(int distanz) {
		if (distanz > 30) {
			state.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
			state.rotate(45);
			state.forward(50);
			akt_pos = pos.front;
		} else {
			state.forward(50);
			akt_pos = pos.right;
		}
	}
	
	boolean xxx = true;
	
	public void front(int distanz) {
		H.p("front:", distanz);
		if (distanz > FRONT_DISTANZ) {
			if (distanz >= 255 ) {
				state.rotate(45);
			} else {
				
			}
			//state.setSensorArmPosition(SensorArmPosition.POSITION_RIGHT);
			//akt_pos = pos.right;
		} else {
			Sound.beep();
			state.halt();
			H.p("" + distanz);
			H.haltstop("front stop");
		}
	}
	
	public void right(int distanz) {
		H.p("right: " + distanz);
		if (distanz > RIGHT_DISTANZ) {
			//state.setSensorArmPosition(SensorArmPosition.POSITION_FRONT);
			//akt_pos = pos.front;
		} else {
			Sound.beep();
			state.halt();
			H.p("" + distanz);
			H.haltstop("right stop");
		}
	}
}

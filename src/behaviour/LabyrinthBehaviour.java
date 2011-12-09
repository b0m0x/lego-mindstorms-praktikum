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
	private enum pos {right, front, start, keineSicht};
	
	private pos akt_pos;
	private RobotState state;
	private Eieruhr uhr = new Eieruhr(100);
	private Messwerte messwerte = new Messwerte(50);
	
	private int keinLandInSicht = 0;
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		
		r.setSensorArmPosition(SensorArmPosition.POSITION_RIGHT);
		state = r; 
		akt_pos = pos.right;
		uhr.reset();
	}
	
	public void update(RobotState r) {
		if (state.crashedIntoWall()) {
			Sound.beep();
			H.p("wall wall wall");
			wallContact();
		} else if (!state.isSensorArmMoving()) {
			int distanz = state.getUltraSonic();
			nextAction(distanz);
		}
	}
	
	private void nextAction(int distanz) {
		switch(akt_pos) {
			case front: front(distanz); break;
			case right: right(distanz); break;
			case start: start(distanz); break;
			case keineSicht: keinSichtkontakt(distanz); break;
		}
	}
	
	private void start(int distanz) {
//		if (distanz > 30) {
////			state.rotate(45);
////			state.forward(50);
//			akt_pos = pos.right;
//		} else {
//			state.forward(50);
//			akt_pos = pos.right;
//		}
	}
	
	private void wallContact() {
		Sound.beep();
		Sound.beep();
		Sound.beep();
		state.halt();
	}
	
	public void right(int distanz) {
		H.p("right: " + distanz);
		
		if (distanz >= 255) {
			akt_pos = pos.keineSicht;
			return;
		}
		
		if (distanz < RIGHT_DISTANZ) {
			state.bend(-0.5f);
			state.forward(50);
		} else {
			state.bend(0.5f);
			state.forward(100);
		}
	}
	
	private void keinSichtkontakt(int distanz) {
		if (distanz < 255) {
			akt_pos = pos.right;
			return;
		}
		
		if (keinLandInSicht < 4) {
			state.rotate(320);
			Sound.buzz();
			keinLandInSicht++;
		} else {
			state.forward(100);
			keinLandInSicht = 0;
			akt_pos = pos.front;
			forward_uhr.reset();
		}
	}
	
	private Eieruhr forward_uhr = new Eieruhr(1000);
	
	public void front(int distanz) {
		r.setSensorArmPosition(SensorArmPosition.POSITION_RIGHT);
	}
	

}

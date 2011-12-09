package behaviour;


import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBehaviour implements RobotBehaviour {
	
	//private int debugging_counter = 0;
	
	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 15;
	private enum pos {right, front, start, keineSicht};
	
	private pos akt_pos;
	private RobotState state;
	private Eieruhr uhr = new Eieruhr(100);
	//private Messwerte messwerte = new Messwerte(50);
	private SensorArmPosition arm_pos;
	private int keinLandInSicht = 0;
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		
		setArmPos(SensorArmPosition.POSITION_RIGHT);
		state = r; 
		akt_pos = pos.right;
		uhr.reset();
	}
	
	public void update(RobotState r) {
		if ( isFrontWall() ) {
			wallContact();
		} else if (!state.isSensorArmMoving()) {
			int distanz = state.getUltraSonic();
			switch(akt_pos) {
			case front: front(distanz); break;
			case right: right(distanz); break;
			case start: start(distanz); break;
			case keineSicht: keinSichtkontakt(distanz); break;
			}
		}
	}

	private void start(int distanz) {}
	
	private void wallContact() {
		Sound.beep();
		Sound.beep();
		Sound.beep();
		state.halt();
		H.haltstop("waaaaaaand");
	}
	
	private boolean isFrontWall() {
		return
			state.crashedIntoWall()
			|| (
				SensorArmPosition.POSITION_RIGHT == arm_pos
				&& state.getUltraSonic() > FRONT_DISTANZ
			);
	}
	
	private void setArmPos(SensorArmPosition pos) {
		state.setSensorArmPosition(SensorArmPosition.POSITION_RIGHT);
		this.arm_pos = pos;
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
			state.rotate(90);
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
		setArmPos(SensorArmPosition.POSITION_FRONT);
	}
	

}

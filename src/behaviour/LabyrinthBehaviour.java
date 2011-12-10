package behaviour;


import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBehaviour implements RobotBehaviour {
	

	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 15;
	private enum STATES {searchingWall, followingWall, searchingWallFront, corner};
	private enum POSITIONS {front, right};
	
	private RobotState robot;
	private STATES currentState;
	private final int GRAD90 = 330*3;
	private POSITIONS armPos;
	private int trials = 0;
	
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		robot = r;
		currentState = STATES.searchingWall;
	}
	
	Eieruhr puffer = new Eieruhr(5000);
	public void update(RobotState r) {
		if ( isWallInFront() ) {
			wallContact();
		} else if ( !robot.isSensorArmMoving() && puffer.isFinished()) {
			puffer.reset();
			int distanz = robot.getUltraSonic();
			switch(currentState) {
			case searchingWallFront: searchingWallFront(distanz); break;
			case followingWall: followingWall(distanz); break;
			case searchingWall: searchingWall(distanz); break;
			case corner: corner(); break;
			}
		}
	}
	
	public void followingWall(int distanz) {
		Sound.twoBeeps();
		if (armPos != POSITIONS.right) { this.setArmPos(POSITIONS.right); return; }
		//if ( isInfinit(distanz) ) { currentState = STATES.searchingWall; return; }
		H.p("right: " + distanz);
		
		if ( isInfinit(distanz) ) {
			currentState = STATES.corner;
		} else {
//			if (distanz < RIGHT_DISTANZ-5) {
//				robot.bend(-0.5f);
//				robot.forward(50);
//			} else if(distanz > RIGHT_DISTANZ+5) {
//				robot.bend(0.5f);
//				robot.forward(50);
//			} else {
//				robot.forward(50);
//			}
			robot.forward(50);
		}
	}
	
	private Eieruhr forward_uhr = new Eieruhr(1000);
	
	private void searchingWall(int distanz) {
		Sound.beep();
		if (armPos != POSITIONS.front) { this.setArmPos(POSITIONS.front); return; }
		//if ( isInfinit(distanz) ) { currentState = STATES.followingWall; return; }
		H.p("searching wall");
		if (trials < 4) {
			robot.rotate(GRAD90);
			Sound.buzz();
			trials++;
		} else {
			this.currentState = STATES.searchingWallFront;
		}
	}
	
	public void searchingWallFront(int distanz) {
		Sound.buzz();
		if (armPos != POSITIONS.front) { this.setArmPos(POSITIONS.front); return; }
		robot.forward(50);
	}
	
	Eieruhr conrner_uhr = new Eieruhr(1000);
	int corner = 0;
	private void corner() {
		if (!conrner_uhr.isFinished()) return;
		switch (corner) {
		case 0: //before
			conrner_uhr.reset();
			robot.forward(50);
			corner++;
			break;
		case 1: //in
			robot.rotate(GRAD90);
			conrner_uhr.reset();
			robot.forward(50);
			corner++;
			break;
		case 2: //out 
			currentState = STATES.followingWall;
			corner = 0;
			break;
		}
	}
	
	private void wallContact() {
		robot.rotate(-GRAD90);
		this.currentState = STATES.followingWall;
	}
	
	private boolean isWallInFront() {
//		H.p(robot.getUltraSonic(), POSITIONS.front == armPos ? "true" : "false", 
//				//robot.getUltraSonic() < FRONT_DISTANZ ? "true" : "false", 
//						armPos);
		H.p(this.currentState);
		return
			robot.crashedIntoWall()
			|| (
					POSITIONS.front == armPos
				&& robot.getUltraSonic() < FRONT_DISTANZ
			);
	}
	
	private void setArmPos(POSITIONS pos) {
		SensorArmPosition sap = 
				pos == POSITIONS.front ? 
				SensorArmPosition.FRONT : SensorArmPosition.RIGHT;
		
		robot.setSensorArmPosition(sap);
		armPos = pos;
	}
	
	private boolean isInfinit(int distanz) {
		return distanz >= 255;
	}

}

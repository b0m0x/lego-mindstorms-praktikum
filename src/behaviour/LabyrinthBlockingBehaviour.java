package behaviour;


import lejos.nxt.Motor;
import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBlockingBehaviour implements RobotBehaviour {
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
		next( STATES.searchingWall );
	}
	
	public void update(RobotState r) {
		if ( isWallInFront() ) {
			wallContact();
		} else {
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
		ensureArmPos( POSITIONS.right );
		
		H.p("right: " + distanz);
		
		if ( isInfinit(distanz) ) {
			next( STATES.corner );
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
	
	private void searchingWall(int distanz) {
		Sound.beep();
		H.p("searching wall");
		ensureArmPos( POSITIONS.front );
		
		if ( !isInfinit(distanz) ) { 
			trials = 0;
			next( STATES.followingWall ); 
		} else if (trials < 4) {
			robot.rotate(GRAD90);
			Sound.buzz();
			trials++;
		} else {
			trials = 0;
			next( STATES.searchingWallFront );
		}
	}
	
	public void searchingWallFront(int distanz) {
		Sound.buzz();
		ensureArmPos( POSITIONS.front );
		robot.forward(50);
	}
	
	private void corner() {
		blockingForward(50, 1000);
		robot.rotate(GRAD90);
		blockingForward(50, 1000);
		
		next( STATES.followingWall );
	}
	
	private void wallContact() {
		robot.rotate(-GRAD90);
		next( STATES.followingWall );
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
	

	
	private boolean isInfinit(int distanz) {
		return distanz >= 255;
	}
	
	private void next(STATES state) {
		currentState = state;
	}
	
	private void blockingForward(int speed, int duration) {
		Eieruhr conrner_uhr = new Eieruhr(duration);
		robot.forward(speed);
		while (!conrner_uhr.isFinished()) {}
	}
	
	private void setArmPos(POSITIONS pos) {
		SensorArmPosition sap = 
				pos == POSITIONS.front ? 
				SensorArmPosition.POSITION_FRONT : SensorArmPosition.POSITION_RIGHT;
		
		robot.setSensorArmPosition(sap);
		armPos = pos;
	}
	
	private void ensureArmPos(POSITIONS pos) {
		while (isArmMoving()) {}
		if (armPos != POSITIONS.front) { 
			setArmPos(pos);
			while ( isArmMoving() ) {}
			Sound.beep();
		}
	}
	
	private boolean isArmMoving() {
		return Motor.B.isMoving() && !Motor.B.isStalled();
	}
}


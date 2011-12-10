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
		next( STATES.followingWall );
	}
	
	public boolean first = false;
	public void update(RobotState r) {
//		if ( isWallInFront() ) {
//			wallContact();
//		} else {
			int distanz = robot.getUltraSonic();
			switch(currentState) {
			case searchingWallFront: searchingWallFront(distanz); break;
			case followingWall: followingWall(distanz); break;
			case searchingWall: searchingWall(distanz); break;
			case corner: corner(); break;
			}
		//}
	}
	
	public void followingWall(int distanz) {
		H.p("right: " + distanz);
		//Sound.twoBeeps();
		ensureArmPos( POSITIONS.right );
		
		if ( distanz > 30 ) {
			next( STATES.corner );
		} else {
			if (!robot.isMoving()) robot.forward(50);
		}
	}
	
	private void searchingWall(int distanz) {
		H.p("searching wall");
		Sound.beep();
		ensureArmPos( POSITIONS.front );
		
		if ( !isInfinit(distanz) || trials >= 4 ) { 
			trials = 0;
			next( STATES.searchingWallFront );
		} else {
			robot.rotate(GRAD90);
			Sound.buzz();
			trials++;
		}
	}
	
	public void searchingWallFront(int distanz) {
		Sound.buzz();
		ensureArmPos( POSITIONS.front );
		if (!robot.isMoving()) robot.forward(50);
	}
	
	private void corner() {
		if (first == true) { first = false; return; };
		H.p("corner");
		blockingForward(50, 800);
		robot.rotate(GRAD90);
		blockingForward(50, 1000);
		
		next( STATES.followingWall );
	}
	
	private void wallContact() {
		Sound.buzz();
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
	
	private void ensureArmPos(POSITIONS pos) {
		if (armPos != pos) { 
			SensorArmPosition sap = 
					pos == POSITIONS.front ? 
					SensorArmPosition.POSITION_FRONT : SensorArmPosition.POSITION_RIGHT;
		
			robot.setSensorArmPosition(sap);
			armPos = pos;
			Sound.beep();
		}
	}
	
	private boolean isArmMoving() {
		return Motor.B.isMoving() && !Motor.B.isStalled();
	}
}


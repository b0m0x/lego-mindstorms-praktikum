package behaviour;


import lejos.nxt.Motor;
import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.SensorArmPosition;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBlockingBehaviour implements RobotBehaviour {
	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 10;
	private enum STATES {searchingWall, followingWall, searchingWallFront, corner};
	private enum POSITIONS {front, right};
	
	private RobotState robot;
	private STATES currentState;
	private POSITIONS armPos;
	private int trials = 0;
	
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		robot = r;
		next( STATES.followingWall );
	}
	
	public boolean first = false;
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
		H.p("right: " + distanz);
		ensureArmPos( POSITIONS.right );
		
		if ( distanz > 30 ) {
			next( STATES.corner );
		} else {
			forward(50);
			float dir = (distanz-RIGHT_DISTANZ) / (float) RIGHT_DISTANZ;
			robot.bend(dir);
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
			robot.rotate90(1);
			Sound.buzz();
			trials++;
		}
	}
	
	public void searchingWallFront(int distanz) {
		Sound.buzz();
		ensureArmPos( POSITIONS.front );
		forward(50);
	}
	
	private void corner() {
		if (first == true) { first = false; return; };
		H.p("corner");
		robot.forwardBlocking(50, 900);
		robot.rotate90(1);
		robot.forwardBlocking(50, 1000);
		
		next( STATES.followingWall );
	}
	
	private void wallContact() {
		Sound.buzz();
		robot.rotate90(-1);
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
	
	private void forward(int speed) {
		if (!robot.isMoving()) robot.forward(speed);
	}
}


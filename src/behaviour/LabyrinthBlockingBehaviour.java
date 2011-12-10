package behaviour;


import lejos.nxt.Motor;
import lejos.nxt.Sound;
import basis.RobotState;
import basis.SensorArm.POSITION;
import behaviour.RobotBehaviour;
import helper.*;

public class LabyrinthBlockingBehaviour implements RobotBehaviour {
	private final int FRONT_DISTANZ = 30;
	private final int RIGHT_DISTANZ = 7;
	private enum STATES {searchingWall, followingWall, searchingWallFront, corner};
	private enum POSITIONS {front, right};
	
	private RobotState robot;
	private STATES currentState;
	private POSITIONS armPos;
	private int trials = 0;
	private boolean first = false;
	
	public void init(RobotState r) {
		H.p("Start Labyrinth!");
		robot = r;
		next( STATES.followingWall );
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
	
	private float lastDir = 0;
	private  void followingWall(int distanz) {
		H.p("right: " + distanz);
		ensureArmPos( POSITIONS.right );
		
		if ( distanz > 30 ) {
			next( STATES.corner );
		} else {
			forward(50);
			//float dist = (float) (Math.cos(90 * lastDir) * distanz);
//			float dir = (dist-RIGHT_DISTANZ) / dist;
			
			// dir > 0 = rechts
			float dir = 0;
			if (distanz > 11) {
				dir = 0.1f;
			} else if (distanz < 9){
				dir = -0.1f;
			}
			
			lastDir = dir;
			H.p("dist:", distanz, dir);
			robot.bend(-dir);
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
		robot.halt();
		robot.forwardBlocking(50, 200);
		robot.halt();
		robot.rotate90(-1);
		robot.halt();
		robot.forwardBlocking(50, 1500);
		
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
			POSITION sap = 
					pos == POSITIONS.front ? 
					POSITION.FRONT : POSITION.RIGHT;
		
			//robot.setPOSITION(sap);
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


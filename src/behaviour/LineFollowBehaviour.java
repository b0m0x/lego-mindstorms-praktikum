package behaviour;
import helper.Eieruhr;
import basis.RobotState;
import basis.SensorArm.POSITION;


public class LineFollowBehaviour implements RobotBehaviour {
	private final static int LINE_FOLLOW_SPEED = 50;
	
	private final static int COLOR_LINE = 40;
	private final static int COLOR_GROUND = 30;
	
	private RobotState r;
	private boolean lineSearching;
	
	private boolean avoidObstacle;
	private RobotBehaviour wallFollower;

	private Eieruhr lineEndTime;
	
	public LineFollowBehaviour() {
		lineEndTime = new Eieruhr(2500);
		lineSearching = true;
		avoidObstacle = false;
		wallFollower = new WallFollowBehaviour(10);
	}
	
	public void init(RobotState r) {
		r.setSensorArmPosition(POSITION.LINE_FOLLOW);		
		this.r = r;
		r.forward(LINE_FOLLOW_SPEED);
	}
	
	public boolean isOnLine() {
		int value = r.getLightSensor();
		if (value >= COLOR_LINE) {
			return true;
		}
		return false;
	}
	
	public boolean isOffLine() {
		int value = r.getLightSensor();
		if (value <= COLOR_GROUND) {
			return true;
		}
		return false;
	}
	
	public void update(RobotState r) {
		if (avoidObstacle) {
			wallFollower.update(r);
			if (isOnLine()) {
				init(r);
				avoidObstacle = false;				
				lineSearching = true;
				r.halt();
				r.rotate(-180);
				r.forward(LINE_FOLLOW_SPEED);
				r.bend(0.6f);
			}
			return;
		}
		if (!avoidObstacle && r.crashedIntoWall()) {
			System.out.println("getting back");
			r.backwardBlocking(50, 1000);
			r.halt();
			System.out.println("Rotating left");
			r.rotate(-90);
			avoidObstacle = true;
			wallFollower.init(r);
		}
		
		if (lineSearching && isOnLine()) {
			//line found again
			r.halt();
			r.rotate(180);
			r.forward(LINE_FOLLOW_SPEED);
			r.bend(0.6f);
			lineSearching = false;
			if (lineEndTime.isFinished()) {
				r.rotate(-90);
				r.forward(30);
				r.addBehaviour(new LevelChangeBehaviour());
				return;
			}
		} else if (!lineSearching && isOffLine()) {
			lineSearching = true;
			lineEndTime.reset();
		}
				
	}
}

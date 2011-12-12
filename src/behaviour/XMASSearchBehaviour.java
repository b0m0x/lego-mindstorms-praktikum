package behaviour;

import basis.RobotState;

public class XMASSearchBehaviour implements RobotBehaviour {

	private RobotState r;
	private RobotBehaviour wallFollower;
	private boolean searchForXMas;
	
	public void init(RobotState r) {
		wallFollower = new WallFollowBehaviour(10);
		wallFollower.init(r);
		searchForXMas = false;
		this.r = r;
	}

	public void update(RobotState r) {
		if (r.crashedIntoWall()) {
			searchForXMas = !searchForXMas;
			if (!searchForXMas) {
				r.backwardBlocking(50, 1000);
				turnSideToWall();
				r.forward(50);
			} else {
				r.rotate(-120 - (int) (Math.random() * 50));
				r.forward(100);
			}
		}
		if (!searchForXMas) {
			wallFollower.update(r);
		}
		
	}

	private void turnSideToWall() {
		r.rotateNonBlocking();
		int lastUsValue = r.getUltraSonic();
		while (lastUsValue >= r.getUltraSonic()) { 
			lastUsValue = r.getUltraSonic();
		}
		r.halt();
		r.forward(50);
	}

}

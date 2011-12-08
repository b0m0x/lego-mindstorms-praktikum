package behaviour;
import basis.RobotState;


public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	
	public void init(RobotState r) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(RobotState r) {
<<<<<<< HEAD
		
=======
<<<<<<< HEAD

		r.forward(0.5f);
		r.bendRight(10);

=======
>>>>>>> 504a856945c0e74ebfed2b40160522351244e235
		/*
>>>>>>> e3ad0d3b0fabb28e3a967a2652a47e84211ebdfc
		r.driveForward(50);
		r.driveCurveRight(10);
		try {
			Thread.sleep(2000);
		
		r.driveCurveRight(30);
		Thread.sleep(2000);
		r.driveCurveRight(60);
		Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
	
}

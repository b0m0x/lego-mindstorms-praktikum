package behaviour;
import basis.RobotState;


public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	public void update(RobotState r) {
<<<<<<< HEAD
		r.forward(0.5f);
		r.bendRight(10);


=======
>>>>>>> 0d2da6203a41f4b6ba42525ff4a339ef2def6340
		/*
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
		}*/
		r.rotate(90);
		r.rotate(-90);
	}
}

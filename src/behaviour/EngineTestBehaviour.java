package behaviour;
import basis.RobotState;


public class EngineTestBehaviour implements RobotBehaviour {
	long start = 0;
	public void update(RobotState r) {

		r.forward(0.5f);
		r.bendRight(10);

		r.rotate(90);
		r.rotate(-90);
	}
}

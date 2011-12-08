package behaviour;
import basis.RobotState;


public interface RobotBehaviour {
	public void init(RobotState r);
	public void update(RobotState r);
}

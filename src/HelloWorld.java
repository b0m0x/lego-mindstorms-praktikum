import lejos.nxt.*;

/**
 * $Id: HelloWorld.java 1587 2008-05-02 17:19:41Z lgriffiths $
 * 
 * @author Lawrie Griffiths
 * 
 */
public class HelloWorld
{
  public static void main (String[] aArg)
  throws Exception
  {
     RobotState r = RobotState.getInstance();
     r.addBehaviour(new RobotBehaviour() {
		int i;
		public void update(RobotState r) {
			if (r.getUltraSonic() <= 10) {
				r.halt();
			} else {
				r.driveForward(10);
			}
		}
	});
     r.driveForward(10);
     while(true) {
    	 r.update();
    	 Thread.sleep(100);
     }
  }
}

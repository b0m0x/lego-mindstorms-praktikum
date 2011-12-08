package test;
import lejos.nxt.*;
import basis.RobotState;

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
     //r.addBehaviour(new DriveForwardAndStopBehaviour());
     //r.addBehaviour(new EngineTestBehaviour());
     //r.addBehaviour(new LineFollowBehaviour());
     r.forward(50);
     while(true) {
    	 r.update();
     }
  }
}
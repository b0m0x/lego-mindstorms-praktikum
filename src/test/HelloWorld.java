package test;
import lejos.nxt.*;
import basis.RobotState;
import behaviour.LabyrinthBehaviour;

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
	 Sound.buzz();
	 Sound.buzz();
	 Sound.buzz();
  	 /*
     RobotState r = RobotState.getInstance();
     //r.addBehaviour(new DriveForwardAndStopBehaviour());
     //r.addBehaviour(new EngineTestBehaviour());
     //r.addBehaviour(new LineFollowBehaviour());
     r.addBehaviour(new LabyrinthBehaviour());
     r.init();
     // r.forward(50);
     while(!Button.ESCAPE.isPressed()) { 
    	 r.update();
     }
     */
  }
}
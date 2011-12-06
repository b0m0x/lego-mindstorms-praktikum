import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.Motor;


public class MotorTest {

	public static void rotate7200()
	   {
	        Motor.A.forward();
	        int count = 0;
	        while( count < 100000 )count = Motor.A.getTachoCount();         
	        Motor.A.stop();
//	       Motor.A.flt();
	        LCD.drawInt(count , 0, 1);
	        while(Motor.A.getSpeed()>0);
	        LCD.drawInt(Motor.A.getTachoCount(), 7,1 );    
	        Button.waitForPress();
	        LCD.clear();
	   }
	
  public static void main (String[] aArg) {
	  Button.ESCAPE.addButtonListener(new ButtonListener() {
		  public void buttonPressed(Button b)
		  {		 
			  LCD.drawString("Program stop", 0, 7);
		  }

		public void buttonReleased(Button b) {
			System.exit(0);
			
		}
	  });
	rotate7200();
  
	  
  }
}


package test;

import lejos.nxt.Sound;
import helper.Eieruhr;
import helper.H;

public class EieruhrTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		
		Eieruhr eieruhr = new Eieruhr(500);
		while ( !eieruhr.isFinished() ) {};
		Sound.beep();
	}
}

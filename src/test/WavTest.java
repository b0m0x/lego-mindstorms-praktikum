package test;

import java.io.File;
import lejos.nxt.Sound;

public class WavTest {
	public static void main (String[] aArg) throws Exception {
		System.out.println("Test:");
		
		Sound.playSample( new File("r2d2wst3.wav") );
		
	}
}

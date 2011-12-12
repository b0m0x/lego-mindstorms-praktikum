package test;

import basis.GateCommon;
import basis.GateControl;
import lejos.nxt.Button;


public class GateControlTest {
	public static void main(String[] args) {
		GateControl gateControl = new GateControl();
		
		System.out.println("Press button to open");
		Button.waitForPress();
		
		while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_2));
		gateControl.openGate();
		gateControl.disconnectFromGate();
		
		System.out.println("Gate opened");
		Button.waitForPress();
	}
}

package behaviour;

import basis.Config;
import basis.GateCommon;
import basis.GateControl;
import basis.RobotState;

public class GateBehaviour implements RobotBehaviour {
	
	boolean signal_send;
	GateControl gateControl;

	//noch ohne einreihen
	public void init(RobotState r) {
		r.halt();
		signal_send = false;
		gateControl = new GateControl();
		
	}

	public void update(RobotState r) {
		if (!signal_send && Config.currentBehaviour == 6) {
			while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_1));
			gateControl.openGate();
			gateControl.disconnectFromGate();
			
			System.out.println("Gate 1 opened");
			r.forward(30);
			signal_send = true;
		}
		else if (!signal_send && Config.currentBehaviour == 12 ) {
			while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_2));
			gateControl.openGate();
			gateControl.disconnectFromGate();
			
			System.out.println("Gate 2 opened");
			r.forward(30);
			signal_send = true;
		}

	}

}

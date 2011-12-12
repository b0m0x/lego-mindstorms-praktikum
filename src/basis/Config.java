package basis;

import behaviour.BridgeBehaviour;
import behaviour.EndBossBehaviour;
import behaviour.GateBehaviour;
import behaviour.HangingBridgeBehaviour;
import behaviour.LabyrinthBehaviour;
import behaviour.LineFollowBehaviour;
import behaviour.RobotBehaviour;
import behaviour.TurnTableBehaviour;
import behaviour.WallFollowBehaviour;
import behaviour.XMASSearchBehaviour;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public final class Config {
	public final static int COLOR_BRIGHT = 40;
	public final static int COLOR_BLACK = 30;
	
	public final static NXTRegulatedMotor SENSOR_MOTOR = Motor.B;
	public final static NXTRegulatedMotor LEFT_MOTOR = Motor.C;
	public final static NXTRegulatedMotor RIGHT_MOTOR = Motor.A;
	
	public final static SensorPort ULTRASONIC_PORT = SensorPort.S1;
	public final static SensorPort LIGHTSENSOR_PORT = SensorPort.S2;
	public final static SensorPort BUMPSENSOR_PORT = SensorPort.S4;
	
	
	
	public final static String[] menuItems = {
		"Labyrinth",
		"Hill",
		"Line", 
		"Hanging Bridge",
		"line2",
		"Gate",
		"XMAS Seach",
		"Bridge",
		"Line3",
		"Rolls",
		"Line4",
		"Gate",
		"Line5",
		"TurnTable",
		"Endboss"			
	};

	public final static RobotBehaviour[] behaviours = {
		new WallFollowBehaviour(10),
		new WallFollowBehaviour(20),
		new LineFollowBehaviour(),
		new HangingBridgeBehaviour(),
		new LineFollowBehaviour(),
		new GateBehaviour(),
		new XMASSearchBehaviour(),
		new BridgeBehaviour(),
		new LineFollowBehaviour(),
		new WallFollowBehaviour(10),
		new LineFollowBehaviour(),
		new GateBehaviour(),
		new LineFollowBehaviour(),
		new TurnTableBehaviour(),
		new EndBossBehaviour()
	};
	public static int currentBehaviour = 0;
}

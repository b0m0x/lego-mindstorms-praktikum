package basis;

import behaviour.BridgeBehaviour;
import behaviour.BumpOffWallAndRotateBehaviour;
import behaviour.EndBossBehaviour;
import behaviour.GateBehaviour;
import behaviour.HangingBridgeBehaviour;
import behaviour.LevelChangeBehaviour;
import behaviour.LineDirectFollowBehaviour;
import behaviour.LineFollowBehaviour;
import behaviour.MultiBotBehaviour;
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
	
	public final static SensorPort ULTRASONIC_PORT = SensorPort.S2;
	public final static SensorPort LIGHTSENSOR_PORT = SensorPort.S1;
	public final static SensorPort BUMPSENSOR_PORT = SensorPort.S4;
	
	
	
	public final static String[] menuItems = {
		"Labyrinth",
		"Hill",
		"Labyrinth",
		"Line", 
		"Rolls",		
		"line2",
		"Gate",
		"XMAS Search",
		"Bridge",
		"Line3",
		"Hanging Bridge",
		"Line4",
		"Gate",
		"Line5",
		"TurnTable",
		"Endboss"			
	};
	private static RobotBehaviour levelChanger = new LevelChangeBehaviour();
	public final static RobotBehaviour[][] behaviours = {
		{ new BumpOffWallAndRotateBehaviour(), new WallFollowBehaviour(10), levelChanger},
		{ new WallFollowBehaviour(20), levelChanger},
		{ new BumpOffWallAndRotateBehaviour(), new WallFollowBehaviour(10), levelChanger},
		{ new LineFollowBehaviour() },
		{ new HangingBridgeBehaviour(), levelChanger }, //Rolls - full speed ahead!
		{ new LineFollowBehaviour(), new MultiBotBehaviour(3)},
		{ new GateBehaviour(), new MultiBotBehaviour(3), levelChanger },
		{ new XMASSearchBehaviour(), levelChanger },
		{ new BridgeBehaviour(), levelChanger },
		{ new LineFollowBehaviour(), new MultiBotBehaviour(3) },
		{ new HangingBridgeBehaviour(), levelChanger},
		{ new LineFollowBehaviour(), new MultiBotBehaviour(3) },
		{ new GateBehaviour(), new MultiBotBehaviour(3), levelChanger },
		{ new LineFollowBehaviour(), new MultiBotBehaviour(3) },
		{ new TurnTableBehaviour(), levelChanger },
		{ new EndBossBehaviour() }
	};
	public static int currentBehaviour = 0;
}

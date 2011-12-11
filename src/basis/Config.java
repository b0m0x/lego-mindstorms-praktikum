package basis;

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
	public final static SensorPort BUMPSENSOR_PORT = SensorPort.S3;
}

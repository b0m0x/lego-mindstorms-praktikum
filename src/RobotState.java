import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class RobotState {
	private final static SensorPort ULTRASONIC_PORT = SensorPort.S1;
	private final static SensorPort LIGHTSENSOR_PORT = SensorPort.S2;
	
	private static RobotState instance;
	
	private List<RobotBehaviour> behaviours = new ArrayList<RobotBehaviour>();
	private UltrasonicSensor usSensor;
	private LightSensor lightSensor;
	private SensorSample lastUsSample;
	private SensorSample lastLightSensorSample;
	
	private RobotState() {
		usSensor = new UltrasonicSensor(ULTRASONIC_PORT);
		lightSensor = new LightSensor(LIGHTSENSOR_PORT);
		lastUsSample = new SensorSample(usSensor.getDistance());
		lastLightSensorSample = new SensorSample(lightSensor.readValue());
	};
	
	public static RobotState getInstance() {
		if (instance == null) {
			instance = new RobotState();
		}
		return instance;
	}
	
	public void addBehaviour(RobotBehaviour behaviour) {
		behaviours.add(behaviour);
	}
	
	public void removeBehaviour(RobotBehaviour behaviour) {
		behaviours.remove(behaviour);
	}
	
	public void clearBehaviour(RobotBehaviour behaviour) {
		behaviours.clear();
	}
	
	public int getUltraSonic() {
		//We need a 200ms delay between sensor polls
		if (lastUsSample.getTime() + 200 >= System.currentTimeMillis() ) {
			lastUsSample = new SensorSample(usSensor.getDistance());
		}
		return lastUsSample.getValue();		
	}
	
	public int getLightSensor() {
		//We need a 50ms delay between sensor polls
		if (lastLightSensorSample.getTime() + 50 >= System.currentTimeMillis() ) {
			lastLightSensorSample = new SensorSample(lightSensor.getLightValue());
		}
		return lastLightSensorSample.getValue();		
	}
	
	public void update() {
		for (RobotBehaviour b : behaviours) {
			b.update(this);
		}
	}
}

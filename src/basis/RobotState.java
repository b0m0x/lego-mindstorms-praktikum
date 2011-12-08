package basis;
import java.util.ArrayList;
import java.util.List;

import behaviour.RobotBehaviour;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
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
	
	private Engine engine;
	//private SensorArm sArm;

	private RobotState() {
		usSensor = new UltrasonicSensor(ULTRASONIC_PORT);
		lightSensor = new LightSensor(LIGHTSENSOR_PORT);
		lastUsSample = new SensorSample(usSensor.getDistance());
		lastLightSensorSample = new SensorSample(lightSensor.getLightValue());
		
		engine = new Engine();
		
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			
			public void buttonReleased(Button arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void buttonPressed(Button arg0) {
				System.exit(0);
			}
		});
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
		if ((lastUsSample.getTime() + 200) <= System.currentTimeMillis() ) {
			lastUsSample = new SensorSample(usSensor.getDistance());
		}
		return lastUsSample.getValue();
	}
	
	public int getLightSensor() {
		//We need a 50ms delay between sensor polls
		if (lastLightSensorSample.getTime() + 50 <= System.currentTimeMillis() ) {
			lastLightSensorSample = new SensorSample(lightSensor.getLightValue());
		}
		return lastLightSensorSample.getValue();
	}
	
	
	public void stop() {
		engine.stop();
	}
	
	public void forward(float speed) {
		engine.forward(speed);
	}
	
	public void forward(float speed, int dist) {
		engine.forward(speed);
		engine.setMaxDist(dist);
	}
	
	
	public void backward(float speed) {
		engine.backward(speed);
	}
	
	public void backward(float speed, int dist) {
		engine.backward(speed);
		engine.setMaxDist(dist);
	}
	
	public void turnLeft(float degrees) {
		engine.turnLeft(degrees);
	}
	
	public void rotateLeft(float degrees) {
		engine.turnLeft(degrees);
	}
	
	
	public void printDisplay(String text) {
		LCD.drawString(text, 0, 0);
	}
	
	public void bendLeft(int ratio) {
		engine.bendLeft(ratio);
	}
	
	public void bendRight(int ratio) {
		engine.bendRight(ratio);
	}
	
	public void bendLeft(int ratio, int dist) {
		engine.bendLeft(ratio);
		engine.setMaxDist(dist);
	}
	
	public void bendRight(int ratio, int dist) {
		engine.bendRight(ratio);
		engine.setMaxDist(dist);
	}
	
	public void driveDistance(int distance, float speed, boolean flt) {
		engine.driveDistance(distance, speed, flt);
	}
		
	public boolean isMoving() {
		return engine.isMoving();
	}
	
	public void rotate(int degrees) {
		engine.rotateBlocking(degrees);
	}
	
	
	public void update() {
		for (RobotBehaviour b : behaviours) {
			b.update(this);
		}
		engine.update();
	}
	
}

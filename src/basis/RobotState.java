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
	private SensorArm sArm;
	
	
	private RobotState() {
		usSensor = new UltrasonicSensor(ULTRASONIC_PORT);
		lightSensor = new LightSensor(LIGHTSENSOR_PORT);
		lastUsSample = new SensorSample(usSensor.getDistance());
		lastLightSensorSample = new SensorSample(lightSensor.getLightValue());
		
		engine = new Engine();
		sArm = new SensorArm();
		
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
	
	public void init() {
		for (RobotBehaviour r : behaviours) {
			r.init(this);
		}
	}
	
	/**
	 * add a behaviour 
	 * @param behaviour
	 */
	public void addBehaviour(RobotBehaviour behaviour) {
		behaviours.add(behaviour);
	}
	
	public void removeBehaviour(RobotBehaviour behaviour) {
		behaviours.remove(behaviour);
	}
	
	/**
	 * remove all behaviours
	 * @param behaviour
	 */
	public void clearBehaviour(RobotBehaviour behaviour) {
		behaviours.clear();
	}
	
	/**
	 * get the UltraSonic Sensor output 
	 * output is distance in cm to the next obstacle.
	 * max. 30, if no object is seen, 255 will be returned
	 * is updated every 200ms
	 * @return distance in cm
	 */
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
	
	/**
	 * stops the engine motors
	 */
	public void halt() {
		engine.stop();
	}
	
	/**
	 * drives forward with given speed
	 * @param speed speed
	 */
	public void driveForward(int speed) {
		//engine.setSpeed((int) speed);
		engine.forward(speed);
	}
	
	/**
	 * drive forward with until the given distance is reached
	 * @param speed speed 
	 * @param dist distance to travel
	 */
	public void driveForward(int speed, int dist) {
		//engine.setSpeed((int) speed);
		engine.forward(speed);
		engine.setMaxDist(dist);
	}
	
	/**
	 * drive backwards
	 * @param speed speed
	 */
	public void driveBackward(int speed) {
		engine.backward(speed);
	}
	
	public void driveBackward(int speed, int dist) {
		engine.backward(speed);
		engine.setMaxDist(dist);
	}
	
	public void printDisplay(String text) {
		LCD.drawString(text, 0, 0);
	}
	
	public void driveCurveLeft(int ratio) {
		engine.bendLeft(ratio);
	}
	
	public void driveCurveRight(int ratio) {
		engine.bendRight(ratio);
	}
	
	public void driveCurveLeft(int ratio, int dist) {
		engine.bendLeft(ratio);
		engine.setMaxDist(dist);
	}
	
	public void driveCurveRight(int ratio, int dist) {
		engine.bendRight(ratio);
		engine.setMaxDist(dist);
	}
	
	/**
	 * drives a distance
	 * @param distance
	 * @param speed
	 * @param flt
	 */
	public void driveDistance(int distance, int speed, boolean flt) {
		engine.driveDistance(distance, speed, flt);
	}
	
	/**
	 * returns true if the robot is moving
	 * @return
	 */
	public boolean isMoving() {
		return engine.isMoving();
	}
	
	/**
	 * rotates the robot inplace
	 * @param degrees degrees to turn. if negative, turn ccw, else cw
	 */
	public void rotate(int degrees) {
		engine.rotateBlocking(degrees);
	}
	
	/**
	 * set the rotation of the sensor arm
	 * @param p
	 */
	public void setSensorArmPosition(SensorArm.SensorArmPosition p) {
		sArm.setPosition(p);
	}
	
	/**
	 * returns true if the sensor arm is currently moving
	 * @return
	 */
	public boolean isSensorArmMoving() {
		return sArm.isMoving();
	}
	
	/**
	 * main update loop, call often!
	 */
	public void update() {
		for (RobotBehaviour b : behaviours) {
			b.update(this);
		}
		engine.update();
		sArm.update();
	}

	
}

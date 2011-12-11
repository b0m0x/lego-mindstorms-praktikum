package basis;
import helper.Eieruhr;

import java.util.ArrayList;
import java.util.List;

import behaviour.RobotBehaviour;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public class RobotState {
	
	private static RobotState instance;
	
	private List<RobotBehaviour> behaviours = new ArrayList<RobotBehaviour>();
	private UltrasonicSensor usSensor;
	private LightSensor lightSensor;
	private TouchSensor bumpSensor;
	private SensorSample lastUsSample;
	private SensorSample lastLightSensorSample;
	
	private Engine engine;
	private SensorArm sArm;
	
	
	private RobotState() {
		usSensor = new UltrasonicSensor(Config.ULTRASONIC_PORT);
		lightSensor = new LightSensor(Config.LIGHTSENSOR_PORT);
		bumpSensor = new TouchSensor(Config.BUMPSENSOR_PORT);
		
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
	public void clearBehaviours() {
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
	 * Haaaaaaaaaaaalt stop!!!
	 * stops the engine motors
	 */
	public void halt() {
		engine.stop();
	}
	
	/**
	 * drives forward with given speed
	 * @param speed speed
	 */
	public void forward(int speed) {
		//engine.setSpeed((int) speed);
		engine.forward(speed/100f);
	}
	
	/**
	 * drive forward with until the given distance is reached
	 * @param speed speed 
	 * @param dist distance to travel
	 */
	public void forward(int speed, int dist) {
		//engine.setSpeed((int) speed);
		engine.forward(speed/100.f);
		engine.setMaxDist(dist);
	}
	
	public void forwardBlocking(int speed, int duration) {
		Eieruhr conrner_uhr = new Eieruhr(duration);
		forward(speed);
		while (!conrner_uhr.isFinished()) {}
	}
	
	/**
	 * drive backwards
	 * @param speed speed
	 */
	public void backward(int speed) {
		engine.backward(speed/100.f);
	}
	
	public void backward(int speed, int dist) {
		engine.backward(speed/100.f);
		engine.setMaxDist(dist);
	}
	
	public void printDisplay(String text) {
		LCD.drawString(text, 0, 0);
	}
	
	public void bendLeft(int ratio) {
		engine.bendLeft(ratio/100.f);
	}
	
	public void bendLeft(int ratio, int dist) {
		engine.bendLeft(ratio/100.f);
		engine.setMaxDist(dist);
	}
	
	public void bendRight(int ratio) {
		engine.bendRight(ratio/100.f);
	}
	
	public void bendRight(int ratio, int dist) {
		engine.bendRight(ratio/100.f);
		engine.setMaxDist(dist);
	}
	
	/**
	 * lent in angegebene richtung [-1; 1] wobei 0 = geradeaus, 1 = rechts, 2 = links
	 * @param dir
	 */
	public void bend(float dir) {
		engine.bend(dir);
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
	 * Rotate 90DEG
	 * @param times
	 */
	public void rotate90(int times) {
		rotate(93*times);
	}
	
	/**
	 * set the rotation of the sensor arm
	 * @param p
	 */
	public void setSensorArmPosition(SensorArm.POSITION p) {
		sArm.setPosition(p, true);
	}
	
	/**
	 * set the rotation of the sensor arm
	 * @param p [0..1]
	 */
	public void setSensorArmPosition(float p) {
		sArm.setPosition(p, true);
	}
	
	public float getArmPositionFloat() {
		return sArm.getPositionFloat();
	}
	
	
	/**
	 * returns true if the sensor arm is currently moving
	 * @return
	 */
	public boolean isSensorArmMoving() {
		return sArm.isMoving();
	}
	
	/**
	 * returns true if robot crashed into a wall or sth.
	 * @return
	 */
	public boolean crashedIntoWall() {
		return bumpSensor.isPressed();
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

	public void backwardBlocking(int speed, int time) {
		Eieruhr conrner_uhr = new Eieruhr(time);
		backward(speed);
		while (!conrner_uhr.isFinished()) {}		
	}
	
	public SensorArm getSensorArm() {
		return sArm;
	}
}

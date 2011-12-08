package basis;

public class SensorSample {
	private long time;
	private int value;
	
	public SensorSample(int value) {
		time = System.currentTimeMillis();
		this.value = value;
	}
	
	public long getTime() {
		return time;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}

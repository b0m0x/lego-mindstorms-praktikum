package helper;

public class Eieruhr {
	
	private final int duration;
	private long start_time;
	
	public Eieruhr(int duration) {
		this.duration = duration;
		reset();
	}
	
	public void reset() {
		start_time = System.currentTimeMillis();
	}
	
	public boolean isFinished() {
		long time = System.currentTimeMillis();
		return (time-start_time) > duration; 
	}
	
}

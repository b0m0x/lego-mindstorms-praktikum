package helper;

public class H {
	
	public static void haltstop(String s) {
		p("# " + s);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void p(Object... s) {
		String string = "";
		for(Object ps: s) {
			string += ps + " ";
		}
		System.out.println(string);
	}
	
	public static void wait(int duration) {
		Eieruhr timer = new Eieruhr(duration);
		while ( timer.isFinished() ) {};
	}
	
	public static void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

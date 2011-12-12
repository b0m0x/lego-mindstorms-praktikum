package basis;


/**
 * Simple class for logging TODO add loglevel
    * 
    */
   public class Logger {
   
   	public static void fatal(String s, Exception ex) {
  		System.out.println(s);
  		System.out.println("===============");
  		System.out.println(ex.getMessage());
  		System.out.println("===============");
  		try {
  			Thread.sleep(10000);
  		} catch (InterruptedException e) {
  		}
  		System.exit(-1);
  	}
  
  	public static void error(String s, Exception ex) {
  		error(s + ex);
  	}
  
  	public static void error(String s) {
  		if (LogLevel.ERROR.isActiveLogLevel()) {
  			System.out.println(s);
  		}
  	}
  
  	public static void warn(String s) {
  		if (LogLevel.WARN.isActiveLogLevel()) {
  			System.out.println(s);
  		}
  	}
  
  	public static void info(String s) {
  		if (LogLevel.INFO.isActiveLogLevel()) {
  			System.out.println(s);
  		}
  	}
  
  	public static void debug(String s) {
  		if (LogLevel.DEBUG.isActiveLogLevel()) {
  			System.out.println(s);
  		}
  	}
  
  	public static void trace(String s) {
  		if (LogLevel.TRACE.isActiveLogLevel()) {
  			System.out.println(s);
  		}
  	}
}
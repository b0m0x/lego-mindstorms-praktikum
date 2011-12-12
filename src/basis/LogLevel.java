package basis;
  
 
   public enum LogLevel {
   	TRACE, DEBUG, INFO, WARN, ERROR;

  	public static LogLevel logLevel = LogLevel.TRACE;

   	public boolean isActiveLogLevel() {
   		return this.ordinal() >= logLevel.ordinal();
  	}
  }
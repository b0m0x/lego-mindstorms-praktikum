package basis;

/**
 * Skeleton for interruptible classes
 */
public abstract class StoppableThread extends Thread {

	private boolean isStopped;

	
	public StoppableThread() {
		this.isStopped = false;
	}
	
	/**
	 * Method that will be cycled
	 */
	protected abstract void doAction();
	
	/**
	 * Method that will be called when the thread is stopped
	 */
	protected void doFinally() {
	}

	@Override
	public void run() {
		while (!isStopped) {
			doAction();
		}
		doFinally();
	}
	


	/**
	 * Wrapper for Thread.sleep()
	 * @param timeOut the length of time to sleep in milliseconds.
	 */
	public void sleep(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException ex) {
		}
	}

	/**
	 * stop the current thread
	 */
	public void stopAction() {
		setStopped(true);
	}

	/**
	 * @param isStopped
	 *            the isStopped to set
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	/**
	 * @return the isStopped
	 */
	public boolean isStopped() {
		return isStopped;
	}

}

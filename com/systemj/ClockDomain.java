package com.systemj;

/**
 * SystemJ program class
 * </p>
 *
 * @Modified_by Heejong Park
 */
public abstract class ClockDomain extends Container implements Runnable {

	private String name;
	
	private boolean terminated;

	protected boolean threaded = false;
	
	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	
	public ClockDomain() {
		init();
	}
	
	public ClockDomain(String name) {
		this.name = name;
		init();
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public boolean isThreaded() {
		return threaded;
	}

	public void setThread() {
		threaded = true;
	}

	public abstract void init();

	public abstract void runClockDomain();

}

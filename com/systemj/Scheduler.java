package com.systemj;

/**
 * Scheduler interface.
 * Any Schduler implementation should have the following methods
 * @author hpar081
 *
 */
public abstract class Scheduler extends Container {
	
	public SystemJProgram getParent(){
		return (SystemJProgram)super.getParent();
	}
	
	/**
	 * Run this scheduler. This method must be non-blocking
	 */
	public abstract void run();
	
	
	/**
	 * Executes clock-domain tick
	 * 
	 * @param cd - clockdomain to exec
	 */
	public final void tick(ClockDomain cd){
		cd.run();
		this.getParent().getInterfaceManager().run();
	}
	
	/**
	 * Argument to be provided to this scheduler
	 * @param args
	 */
	public abstract void addArguments(String args);
}

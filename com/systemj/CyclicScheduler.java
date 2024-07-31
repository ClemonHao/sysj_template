package com.systemj;

import java.util.Vector;

/**
 * This is a simple cyclic scheduler
 * 
 * Must be compatible with CLDC 1.1
 * 
 * @author hpar081
 *
 */
public class CyclicScheduler extends Scheduler{

	int delay = 1;
	//@Override
	public void addArguments(String args) {
		delay = Integer.valueOf(args).intValue();
	}

	//@Override
	public void run() {
		for(int i=0;i<this.getChildSize();i++){
			tick((ClockDomain)this.getChild(i));
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

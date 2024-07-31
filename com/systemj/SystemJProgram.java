package com.systemj;

import java.util.Vector;

/**
 * SystemJ program Must be compatible with CLDC 1.1
 * 
 * @author hpar081
 *
 */
public class SystemJProgram extends Container implements Runnable {

	private String name;
	private InterfaceManager im;
	private Vector schedulers = new Vector();

	public void setSubSystemName(String n) {
		name = n;
	}

	public String getSubSystemName() {
		return name;
	}

	public InterfaceManager getInterfaceManager() {
		for (int i = 0; i < this.getChildSize(); i++) {
			if (this.getChild(i) instanceof InterfaceManager)
				return (InterfaceManager) this.getChild(i);
		}
		return null;
	}

	public void init() {
		im = this.getInterfaceManager();
		im.init();
		schedulers.removeAllElements();
		for (int i = 0; i < this.getChildSize(); i++) {
			if (this.getChild(i) instanceof Scheduler)
				schedulers.addElement(this.getChild(i));
		}
	}

	public void startProgram() {
		try {
			while (!interrupted) {
				for (int i = 0; i < schedulers.size(); i++)
					((Scheduler) schedulers.elementAt(i)).run();
				im.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		shutdown = true;
	}

	public boolean isShutDown() {
		return shutdown;
	}

	public void run() {
		this.startProgram();
	}
}

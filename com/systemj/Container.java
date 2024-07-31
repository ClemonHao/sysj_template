package com.systemj;

import java.util.Vector;

public class Container {

	Vector children = new Vector();

	Container parent;
	
	protected boolean interrupted;
	
	protected boolean shutdown;

	public Container getParent() {
		return parent;
	}
	
	public void setParent(Container parent){
		this.parent = parent;
	}

	public void setChild(int i, Container child) {
		children.setElementAt(child, i);
	}

	public void addChild(Container child) {
		children.addElement(child);
	}

	public void removeChild(int i) {
		children.removeElementAt(i);
	}

	public int getChildSize() {
		return children.size();
	}

	public Container getChild(int i) {
		return (Container) children.elementAt(i);
	}
	
	public Vector getChildren() {
		return children;
	}
	
	public static void connect(Container parent, Container child){
		parent.addChild(child);
		child.setParent(parent);
	}
	
	public SystemJProgram getSubSystem(){
		Container cont = this;
		while(!(cont instanceof SystemJProgram))
			cont = cont.getParent();
		return ((SystemJProgram) cont);
	}

	/**
	 * This method blocks the caller until all the child containers
	 * terminate
	 */
	public void shutDownNow() {
		interrupted = true;
		cleanUp();
		while (!isShutDown())
			;
		
		for (int i = 0; i < getChildSize(); i++) {
			getChild(i).shutDownNow();
		}
	}

	/**
	 * If the container cannot immediately shutdown (i.e. need to
	 * terminate threads), it should override this method and return
	 * {@code true} to indicate the caller its completion
	 * 
	 * @return boolean value indicating that the container has been
	 *         shutdown
	 */
	public boolean isShutDown() {
		return true;
	}
	
	public void cleanUp() {
	}
	
	// ------------- To be used later ... -----------------
	public Vector getChannels(){
		SystemJProgram ss = getSubSystem();
		Vector v = new Vector();
		dFSChannels(v);
		return v;
	}
	
	private void dFSChannels(Vector v){
		if(this instanceof GenericChannel)
			v.addElement(this);
		
		for(int i=0; i < getChildSize() ; i++){
			getChild(i).dFSChannels(v);
		}
	}
	
}



